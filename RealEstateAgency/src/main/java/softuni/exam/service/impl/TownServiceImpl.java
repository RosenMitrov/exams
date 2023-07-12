package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedJsonDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWN_INPUT_FILE_PATH = "src/main/resources/files/json/towns.json";
    private static final String SUCCESSFUL_ADD_PRINT_FORMAT = "Successfully imported town %s - %d";
    private static final String INVALID_TOWN_PRINT_MESSAGE = "Invalid town";
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {

        final Path filePath = Path.of(TOWN_INPUT_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importTowns() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();

        final String context = this.readTownsFileContent();

        final TownSeedJsonDto[] allTowns = this.gson.fromJson(context, TownSeedJsonDto[].class);

        for (TownSeedJsonDto townDto : allTowns) {

            final boolean isValid = this.validationUtil.isValid(townDto);

            Optional<Town> optionalTownByName = this.townRepository.findFirstByTownName(townDto.getTownName());

            boolean doesNotExist = optionalTownByName.isEmpty();

            if (isValid && doesNotExist) {
                final Town townToSave = this.modelMapper.map(townDto, Town.class);
                this.townRepository.saveAndFlush(townToSave);

                final String successfulSaveMessage = String.format(SUCCESSFUL_ADD_PRINT_FORMAT,
                        townDto.getTownName(),
                        townDto.getPopulation());

                outputMessage.append(successfulSaveMessage)
                        .append(System.lineSeparator());
            } else {

                outputMessage.append(INVALID_TOWN_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage.toString().trim();
    }
}
