package com.example.football.service.impl;

import com.example.football.models.dto.StatRootXmlDto;
import com.example.football.models.dto.StatSeedXmlDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.example.football.constants.FIlePaths.*;
import static com.example.football.constants.PrintMessages.*;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {

        final Path filePath = Path.of(STAT_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importStats() throws JAXBException {

        final StringBuilder outputMessage = new StringBuilder();

        final StatRootXmlDto statRootXmlDto = this.xmlParser
                .parseXML(STAT_SEED_FILE_PATH, StatRootXmlDto.class);

        final List<StatSeedXmlDto> allStats = statRootXmlDto.getStats();

        for (StatSeedXmlDto statDto : allStats) {

            final boolean isValid = this.validationUtil.isValid(statDto);

            final Optional<Stat> statByAllParams = this.statRepository
                    .findFirstByShootingAndPassingAndEndurance(statDto.getShooting(), statDto.getPassing(), statDto.getEndurance());

            final boolean statDoesNotExist = statByAllParams.isEmpty();

            if (isValid && statDoesNotExist) {

                final Stat statToSave = this.modelMapper.map(statDto, Stat.class);

                this.statRepository.saveAndFlush(statToSave);

                final String successfulSaveMessage = String.format(SUCCESSFULLY_ADDED_STAT_IN_DB_PRINT_FORMAT,
                        statDto.getPassing(),
                        statDto.getShooting(),
                        statDto.getEndurance()
                );

                outputMessage.append(successfulSaveMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage.append(INVALID_STAT_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }

        return outputMessage
                .toString()
                .trim();
    }
}









