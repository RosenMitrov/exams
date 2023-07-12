package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedJsonDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {

        final Path filePath = Path.of(TEAM_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importTeams() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();

        final String teamContext = this.readTeamsFileContent();

        final TeamSeedJsonDto[] allTeamsDto = this.gson.fromJson(teamContext, TeamSeedJsonDto[].class);

        for (TeamSeedJsonDto teamDto : allTeamsDto) {

            final boolean isValid = this.validationUtil.isValid(teamDto);
            final Optional<Team> teamByName = this.teamRepository.findFirstByName(teamDto.getName());
            final boolean teamNotExistInDb = teamByName.isEmpty();

            if (isValid && teamNotExistInDb) {

                final Optional<Town> townByName = this.townRepository.findFirstByName(teamDto.getTownName());

                if (townByName.isPresent()) {

                    final Team teamToSave = this.modelMapper.map(teamDto, Team.class);
                    final Town townToSet = townByName.get();
                    teamToSave.setTown(townToSet);

                    this.teamRepository.saveAndFlush(teamToSave);

                    final String successfulSaveMessage = String.format(SUCCESSFULLY_ADDED_TEAM_IN_DB_PRINT_FORMAT,
                            teamDto.getName(),
                            teamDto.getFanBase()
                    );

                    outputMessage.append(successfulSaveMessage)
                            .append(System.lineSeparator());
                }

            } else {
                outputMessage.append(INVALID_TEAM_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}









