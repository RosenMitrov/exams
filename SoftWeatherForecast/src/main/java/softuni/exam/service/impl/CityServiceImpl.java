package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonSeed.CityJsonSeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private static final String CITY_READING_FILE_PATH = "src/main/resources/files/json/cities.json";
    private static final String INVALID_CITY_PRINT_MESSAGE = "Invalid city";
    private static final String SUCCESSFULLY_IMPORTED_CITY_FORMAT = "Successfully imported city %s - %s";
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {

        final Path filePath = Path.of(CITY_READING_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importCities() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();

        final String context = this.readCitiesFileContent();

        final CityJsonSeedDto[] allCities = this.gson.fromJson(context, CityJsonSeedDto[].class);

        for (CityJsonSeedDto citySeedDto : allCities) {

            final boolean isValid = this.validationUtil.isValid(citySeedDto);

            final Optional<City> firstByCityName = this.cityRepository.findFirstByCityName(citySeedDto.getCityName());

            final boolean isNotExists = firstByCityName.isEmpty();

            if (isValid && isNotExists) {

                final City cityToSave = this.modelMapper.map(citySeedDto, City.class);

                final Optional<Country> countryById = this.countryRepository.findById(citySeedDto.getCountry());

                if (countryById.isPresent()) {
                    cityToSave.setCountry(countryById.get());

                    this.cityRepository.saveAndFlush(cityToSave);

                    final String successfulMessage = String.format(SUCCESSFULLY_IMPORTED_CITY_FORMAT,
                            citySeedDto.getCityName(),
                            citySeedDto.getPopulation());

                    outputMessage.append(successfulMessage)
                            .append(System.lineSeparator());

                }
            } else {
                outputMessage.append(INVALID_CITY_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage.toString();
    }
}











