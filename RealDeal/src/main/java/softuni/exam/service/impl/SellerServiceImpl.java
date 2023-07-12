package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Seller;
import softuni.exam.models.entity.dtos.xmls.SellerRootXmlDto;
import softuni.exam.models.entity.dtos.xmls.SellerSeedXmlDto;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.FilePaths.SELLER_SEED_FILE_PATH;
import static softuni.exam.constants.StaticMessage.SELLER_INVALID_PRINT_MESSAGE;
import static softuni.exam.constants.StaticMessage.SELLER_SUCCESSFUL_PRINT_MESSAGE;

@Service
public class SellerServiceImpl implements SellerService {
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;

    private final XmlParser xmlParser;

    @Autowired
    public SellerServiceImpl(ValidationUtil validationUtil,
                             ModelMapper modelMapper,
                             SellerRepository sellerRepository,
                             XmlParser xmlParser) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.sellerRepository = sellerRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {

        final Path filePath = Path.of(SELLER_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importSellers() throws IOException, JAXBException {

        final StringBuilder outputMessage = new StringBuilder();
        final SellerRootXmlDto sellerRootXmlDto = this.xmlParser.parseXML(SELLER_SEED_FILE_PATH, SellerRootXmlDto.class);
        final List<SellerSeedXmlDto> allSellers = sellerRootXmlDto.getSellers();

        for (SellerSeedXmlDto sellerDto : allSellers) {

            final boolean isSellerValid = this.validationUtil.isValid(sellerDto);
            final Optional<Seller> sellerByEmail = this.sellerRepository.findFirstByEmail(sellerDto.getEmail());
            final boolean isSellerPresent = sellerByEmail.isPresent();

            if (isSellerValid && !isSellerPresent) {

                final Seller sellerToSave = this.modelMapper.map(sellerDto, Seller.class);
                this.sellerRepository.saveAndFlush(sellerToSave);

                final String successfullySavedMessage = String.format(SELLER_SUCCESSFUL_PRINT_MESSAGE,
                        sellerDto.getLastName(),
                        sellerDto.getEmail());

                outputMessage
                        .append(successfullySavedMessage)
                        .append(System.lineSeparator());

            } else {
                outputMessage
                        .append(SELLER_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}
