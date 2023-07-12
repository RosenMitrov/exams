package exam.service.impl;

import exam.model.entity.Town;
import exam.model.entity.dto.TownRootXmlDto;
import exam.model.entity.dto.TownSeedXmlDto;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static exam.constants.FilePaths.TOWN_SEED_FILE_PATH;
import static exam.constants.Messages.TOWN_INVALID_PRINT_MESSAGE;
import static exam.constants.Messages.TOWN_SUCCESSFULLY_SAVED_PRINT_FORMAT;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {

        final Path filePath = Path.of(TOWN_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {

        final StringBuilder outputMessage = new StringBuilder();
        final TownRootXmlDto townRootXmlDto = this.xmlParser.parseXML(TOWN_SEED_FILE_PATH, TownRootXmlDto.class);

        final List<TownSeedXmlDto> allTownsDto = townRootXmlDto.getTowns();

        for (TownSeedXmlDto townDto : allTownsDto) {

            final boolean isValid = this.validationUtil.isValid(townDto);

            final Optional<Town> townByName = this.townRepository.findFirstByName(townDto.getName());
            final boolean townDoesNotExist = townByName.isEmpty();

            if (isValid && townDoesNotExist) {

                final Town townToSave = this.modelMapper.map(townDto, Town.class);
                this.townRepository.saveAndFlush(townToSave);

                final String successfullySavedMessage = String.format(TOWN_SUCCESSFULLY_SAVED_PRINT_FORMAT,
                        townDto.getName());

                outputMessage.append(successfullySavedMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage.append(TOWN_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}












