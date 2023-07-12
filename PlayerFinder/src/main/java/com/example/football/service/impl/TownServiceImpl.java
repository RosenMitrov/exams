package com.example.football.service.impl;

import com.example.football.models.dto.TownSeedJsonDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.example.football.constants.FIlePaths.*;
import static com.example.football.constants.PrintMessages.*;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {

        Path filePath = Path.of(TOWN_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importTowns() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();

        final String context = this.readTownsFileContent();
        final TownSeedJsonDto[] allTownsDto = this.gson.fromJson(context, TownSeedJsonDto[].class);

        for (TownSeedJsonDto townDto : allTownsDto) {

            final boolean isValid = this.validationUtil.isValid(townDto);
            final Optional<Town> townByName = this.townRepository.findFirstByName(townDto.getName());
            final boolean townNotExistsInDb = townByName.isEmpty();

            if (isValid && townNotExistsInDb) {

                final Town townToSave = this.modelMapper.map(townDto, Town.class);
                this.townRepository.saveAndFlush(townToSave);

                final String successfulSaveMessage = String.format(SUCCESSFULLY_ADDED_TOWN_IN_DB_PRINT_FORMAT,
                        townDto.getName(),
                        townDto.getPopulation());
                outputMessage.append(successfulSaveMessage)
                        .append(System.lineSeparator());

            } else {
                outputMessage.append(INVALID_TOWN_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}





