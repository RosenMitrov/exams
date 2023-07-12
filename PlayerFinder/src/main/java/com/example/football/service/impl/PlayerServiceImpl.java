package com.example.football.service.impl;

import com.example.football.models.dto.PlayerRootXmlDto;
import com.example.football.models.dto.PlayerSeedXmlDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.football.constants.FIlePaths.*;
import static com.example.football.constants.PrintMessages.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             TownRepository townRepository,
                             TeamRepository teamRepository,
                             StatRepository statRepository,
                             ModelMapper modelMapper,
                             ValidationUtil validationUtil,
                             XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {

        final Path filePath = Path.of(PLAYER_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importPlayers() throws JAXBException {

        final StringBuilder outputMessage = new StringBuilder();

        final PlayerRootXmlDto playerRootXmlDto = this.xmlParser.parseXML(PLAYER_SEED_FILE_PATH, PlayerRootXmlDto.class);

        final List<PlayerSeedXmlDto> allPlayersDto = playerRootXmlDto.getPlayers();

        for (PlayerSeedXmlDto playerDto : allPlayersDto) {

            final boolean isValid = this.validationUtil.isValid(playerDto);

            final Optional<Player> playerByEmail = this.playerRepository.findFirstByEmail(playerDto.getEmail());

            final boolean playerDoesNotExist = playerByEmail.isEmpty();

            if (isValid && playerDoesNotExist) {

                final Player playerToSave = this.modelMapper.map(playerDto, Player.class);

                final String townName = playerDto.getTownName().getTownName();
                final Town townToSet = this.townRepository.findFirstByName(townName).orElseThrow(NoSuchElementException::new);

                final String teamName = playerDto.getTeamName().getTeamName();
                final Team teamToSet = this.teamRepository.findFirstByName(teamName).orElseThrow(NoSuchElementException::new);

                Long id = playerDto.getStat().getId();
                final Stat statToSet = this.statRepository.findById(id).orElseThrow(NoSuchElementException::new);

                playerToSave.setTown(townToSet);
                playerToSave.setTeam(teamToSet);
                playerToSave.setStat(statToSet);

                this.playerRepository.saveAndFlush(playerToSave);

                final String successfulSaveMessage = String.format(SUCCESSFULLY_ADDED_PLAYER_IN_DB_PRINT_FORMAT,
                        playerDto.getFirstName(),
                        playerDto.getLastName(),
                        playerDto.getPosition()
                );

                outputMessage.append(successfulSaveMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage.append(INVALID_PLAYER_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage.toString().trim();
    }

    @Override
    public String exportBestPlayers() {

        final LocalDate birthDateAfter = LocalDate.parse(BIRTHDATE_AFTER_PATTERN);

        final LocalDate birthDateBefore = LocalDate.parse(BIRTHDATE_BEFORE_PATTERN);

        final List<Player> players = this.playerRepository
                .findAllByBirthDateAfterAndBirthDateBeforeOrderByStat_ShootingDescStat_PassingDescStat_EnduranceDescLastNameAsc(birthDateAfter, birthDateBefore)
                .orElseThrow(NoSuchElementException::new);


        return players.stream()
                .map(player -> String.format(VIEW_FORMAT,
                        player.getFirstName(), player.getLastName(),
                        player.getPosition(),
                        player.getTeam().getName(),
                        player.getTeam().getStadiumName()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
