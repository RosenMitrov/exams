package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferRootXmlDto;
import softuni.exam.models.dto.OfferSeedXmlDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {


    private static final String OFFER_INPUT_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    private static final String SUCCESSFUL_ADD_PRINT_FORMAT = "Successfully imported offer %.2f";
    private static final String INVALID_OFFER_PRINT_MESSAGE = "Invalid offer";
    private final OfferRepository offerRepository;
    private final ValidationUtil validationUtil;

    private final ApartmentRepository apartmentRepository;

    private final ModelMapper modelMapper;

    private final AgentRepository agentRepository;

    private final XmlParser xmlParser;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ValidationUtil validationUtil, ApartmentRepository apartmentRepository, ModelMapper modelMapper, AgentRepository agentRepository, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.validationUtil = validationUtil;
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.agentRepository = agentRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {

        Path filePath = Path.of(OFFER_INPUT_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        StringBuilder outputMessage = new StringBuilder();

        OfferRootXmlDto offerRootXmlDto = this.xmlParser.parseXML(OFFER_INPUT_FILE_PATH, OfferRootXmlDto.class);

        List<OfferSeedXmlDto> allOffers = offerRootXmlDto.getOffers();

        for (OfferSeedXmlDto offerDto : allOffers) {

            boolean isValid = this.validationUtil.isValid(offerDto);

            Optional<Agent> agentByFirstName = this.agentRepository.findFirstByFirstName(offerDto.getAgent().getName());

            boolean agentExits = agentByFirstName.isPresent();

            if (isValid && agentExits) {

                Offer offerToSave = this.modelMapper.map(offerDto, Offer.class);
                Apartment apartment = this.apartmentRepository.findById(offerDto.getApartment().getId()).orElseThrow(NoSuchElementException::new);

                Agent agent = agentByFirstName.get();

                offerToSave.setAgent(agent);
                offerToSave.setApartment(apartment);

                this.offerRepository.saveAndFlush(offerToSave);

                String successfulSaveMessage = String.format(SUCCESSFUL_ADD_PRINT_FORMAT,
                        offerDto.getPrice());
                outputMessage.append(successfulSaveMessage)
                        .append(System.lineSeparator());

            } else {
                outputMessage.append(INVALID_OFFER_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }

        return outputMessage.toString();
    }

    @Override
    public String exportOffers() {

        List<Offer> offers = this.offerRepository
                .findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType.three_rooms).orElseThrow(NoSuchElementException::new);
        String output = "Agent %s %s with offer №%d:" + System.lineSeparator() +
                "-Apartment area: %.2f" + System.lineSeparator() +
                "--Town: %s" + System.lineSeparator() +
                "---Price: %.2f$";

        return offers.stream()
                .map(offer ->
                        String.format(output, offer.getAgent().getFirstName(), offer.getAgent().getLastName(), offer.getId(),
                                offer.getApartment().getArea(),
                                offer.getApartment().getTown().getTownName(),
                                offer.getPrice()))
                .collect(Collectors.joining(System.lineSeparator()));


    }
}







