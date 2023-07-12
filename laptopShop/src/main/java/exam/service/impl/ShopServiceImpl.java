package exam.service.impl;

import exam.model.entity.Shop;
import exam.model.entity.Town;
import exam.model.entity.dto.ShopRootXmlDto;
import exam.model.entity.dto.ShopSeedXmlDto;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static exam.constants.FilePaths.*;
import static exam.constants.Messages.*;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {

        final Path filePath = Path.of(SHOP_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importShops() throws JAXBException {

        final StringBuilder outputMessage = new StringBuilder();
        final ShopRootXmlDto shopRootXmlDto = this.xmlParser.parseXML(SHOP_SEED_FILE_PATH, ShopRootXmlDto.class);

        final List<ShopSeedXmlDto> allShops = shopRootXmlDto.getShops();

        for (ShopSeedXmlDto shopDto : allShops) {

            final boolean isValid = this.validationUtil.isValid(shopDto);

            final Optional<Shop> shopByName = this.shopRepository.findFirstByName(shopDto.getName());
            final boolean shopDoesNotExist = shopByName.isEmpty();

            if (isValid && shopDoesNotExist) {

                final Shop shopToSave = this.modelMapper.map(shopDto, Shop.class);
                final Town townToSet = this.townRepository.findFirstByName(shopDto.getTown().getTownName()).orElseThrow(NoSuchElementException::new);

                shopToSave.setTown(townToSet);
                this.shopRepository.saveAndFlush(shopToSave);

                final String successfullySavedMessage = String.format(SHOP_SUCCESSFULLY_SAVED_PRINT_FORMAT,
                        shopDto.getName(),
                        shopDto.getIncome());
                outputMessage
                        .append(successfullySavedMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage
                        .append(SHOP_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}
