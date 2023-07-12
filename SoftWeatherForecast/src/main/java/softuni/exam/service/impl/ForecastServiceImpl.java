package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmlSeed.ForecastRootXmlDto;
import softuni.exam.models.dto.xmlSeed.ForecastSeedXmlDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private static final String FORECAST_READING_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";
    private static final String INVALID_FORECAST_PRINT_MESSAGE = "Invalid forecast";
    private static final String SUCCESSFULLY_IMPORTED_FORECAST_FORMAT = "Successfully import forecast %s - %.2f";
    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;


    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;

        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {

        final Path filePath = Path.of(FORECAST_READING_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {

        final StringBuilder outputMessage = new StringBuilder();

        final ForecastRootXmlDto forecastRootXmlDto = this.xmlParser
                .parseXML(FORECAST_READING_FILE_PATH, ForecastRootXmlDto.class);

        final List<ForecastSeedXmlDto> allForecasts = forecastRootXmlDto.getForecasts();

        final Set<DayWeek> validDaysOfWeek = Arrays.stream(DayWeek.values()).collect(Collectors.toSet());

        for (ForecastSeedXmlDto forecastDto : allForecasts) {

            final boolean isValid = this.validationUtil.isValid(forecastDto);

            final boolean isValidDayOfWeek = validDaysOfWeek.contains(forecastDto.getDayOfWeek());

            if (isValid && isValidDayOfWeek) {

                final Optional<City> cityById = this.cityRepository.findById(forecastDto.getCity());

                if (cityById.isPresent()) {

                    final City city = cityById.get();

                    final Optional<Forecast> firstByCityAndDayOfWeek = this.forecastRepository
                            .findFirstByCityAndDayOfWeek(city, forecastDto.getDayOfWeek());

                    if (firstByCityAndDayOfWeek.isEmpty()) {

                        final Forecast forecastToSave = this.modelMapper.map(forecastDto, Forecast.class);
                        forecastToSave.setCity(city);

                        this.forecastRepository.saveAndFlush(forecastToSave);

                        final String successfulMessage = String.format(SUCCESSFULLY_IMPORTED_FORECAST_FORMAT,
                                forecastDto.getDayOfWeek(),
                                forecastDto.getMaxTemperature());

                        outputMessage.append(successfulMessage)
                                .append(System.lineSeparator());
                    } else {
                        outputMessage.append(INVALID_FORECAST_PRINT_MESSAGE)
                                .append(System.lineSeparator());
                    }
                }
            } else {
                outputMessage.append(INVALID_FORECAST_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage.toString();
    }

    @Override
    public String exportForecasts() {

        final Optional<List<Forecast>> forecasts = this.forecastRepository
                .findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayWeek.SUNDAY, 150000);

        if (forecasts.isPresent()) {
            final List<Forecast> allForecasts = forecasts.get();

            return allForecasts
                    .stream()
                    .map(Forecast::toString)
                    .collect(Collectors.joining(System.lineSeparator()));

        }

        return "No such info ;)";
    }
}
