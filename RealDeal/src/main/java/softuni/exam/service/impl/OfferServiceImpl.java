package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.Seller;
import softuni.exam.models.entity.dtos.xmls.OfferRootXmlDto;
import softuni.exam.models.entity.dtos.xmls.OfferSeedXmlDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static softuni.exam.constants.FilePaths.OFFER_SEED_FILE_PATH;
import static softuni.exam.constants.StaticMessage.OFFER_INVALID_PRINT_MESSAGE;
import static softuni.exam.constants.StaticMessage.OFFER_SUCCESSFUL_PRINT_MESSAGE;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final SellerRepository sellerRepository;
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            SellerRepository sellerRepository,
                            CarRepository carRepository,
                            ValidationUtil validationUtil,
                            ModelMapper modelMapper,
                            XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.sellerRepository = sellerRepository;
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {

        final Path filePath = Path.of(OFFER_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        final StringBuilder outputMessage = new StringBuilder();
        final OfferRootXmlDto offerRootXmlDto = this.xmlParser.parseXML(OFFER_SEED_FILE_PATH, OfferRootXmlDto.class);
        final List<OfferSeedXmlDto> allOffers = offerRootXmlDto.getOffers();

        for (OfferSeedXmlDto offerDto : allOffers) {

            final boolean isOfferValid = this.validationUtil.isValid(offerDto);
            if (isOfferValid) {

                final Offer offerToSave = this.modelMapper.map(offerDto, Offer.class);

                final Seller sellerToBeSet = this.sellerRepository.findById(offerDto.getSeller().getId()).orElse(null);
                final Car carToBeSet = this.carRepository.findById(offerDto.getCar().getId()).orElse(null);
                Set<Picture> carToBeSetPictures;

                offerToSave.setSeller(sellerToBeSet);
                offerToSave.setCar(carToBeSet);

                if (carToBeSet != null) {
                    carToBeSetPictures = carToBeSet.getPictures();
                    offerToSave.setPictures(new HashSet<>(carToBeSetPictures));
                }

                this.offerRepository.saveAndFlush(offerToSave);

                final String successfullySavedMessage = String.format(OFFER_SUCCESSFUL_PRINT_MESSAGE,
                        offerDto.getAddedOn(),
                        offerDto.isHasGoldStatus());

                outputMessage
                        .append(successfullySavedMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage
                        .append(OFFER_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}
