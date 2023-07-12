package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonSeed.CountryJsonSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRY_READING_FILE_PATH = "src/main/resources/files/json/countries.json";
    private static final String INVALID_COUNTRY_PRINT_MESSAGE = "Invalid country";
    private static final String SUCCESSFULLY_IMPORTED_COUNTRY_FORMAT = "Successfully imported country %s - %s";
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {

        Path filePath = Path.of(COUNTRY_READING_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importCountries() throws IOException {

        final StringBuilder allMessages = new StringBuilder();

        final String context = this.readCountriesFromFile();
        final CountryJsonSeedDto[] allCountries = this.gson.fromJson(context, CountryJsonSeedDto[].class);

        for (CountryJsonSeedDto seedDto : allCountries) {

            final boolean isValid = this.validationUtil.isValid(seedDto);
            final Optional<Country> byCountryName = this.countryRepository.findByCountryName(seedDto.getCountryName());

            final boolean isNotExists = byCountryName.isEmpty();

            if (isValid && isNotExists) {

                final Country countryToSave = this.modelMapper.map(seedDto, Country.class);
                this.countryRepository.saveAndFlush(countryToSave);

                final String successfulMessage = String.format(SUCCESSFULLY_IMPORTED_COUNTRY_FORMAT,
                        seedDto.getCountryName(),
                        seedDto.getCurrency());

                allMessages.append(successfulMessage)
                        .append(System.lineSeparator());
            } else {
                allMessages.append(INVALID_COUNTRY_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return allMessages.toString();
    }
}










