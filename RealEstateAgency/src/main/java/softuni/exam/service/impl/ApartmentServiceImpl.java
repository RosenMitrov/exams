package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentRootXmlDto;
import softuni.exam.models.dto.ApartmentSeedXmlDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final String APARTMENT_INPUT_FILE_PATH = "src/main/resources/files/xml/apartments.xml";
    private static final String SUCCESSFUL_ADD_PRINT_FORMAT = "Successfully imported apartment %s - %.2f";
    private static final String INVALID_APARTMENT_PRINT_MESSAGE = "Invalid apartment";
    private final ApartmentRepository apartmentRepository;
    private final ValidationUtil validationUtil;

    private final TownRepository townRepository;

    private final ModelMapper modelMapper;

    private final XmlParser xmlParser;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ValidationUtil validationUtil, TownRepository townRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        Path filePath = Path.of(APARTMENT_INPUT_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {

        StringBuilder outputMessage = new StringBuilder();

        ApartmentRootXmlDto apartmentRootXmlDto = this.xmlParser
                .parseXML(APARTMENT_INPUT_FILE_PATH, ApartmentRootXmlDto.class);

        List<ApartmentSeedXmlDto> allApartments = apartmentRootXmlDto.getApartments();

        for (ApartmentSeedXmlDto apartmentDto : allApartments) {

            boolean isValid = this.validationUtil.isValid(apartmentDto);

            Optional<Apartment> firstByTownNameAndArea = this.apartmentRepository.findFirstByTown_TownNameAndArea(apartmentDto.getTownName(), apartmentDto.getArea());

            boolean doesNotExist = firstByTownNameAndArea.isEmpty();

            if (isValid && doesNotExist) {

                Optional<Town> firstByTownName = this.townRepository.findFirstByTownName(apartmentDto.getTownName());

                if (firstByTownName.isPresent()) {
                    Apartment apartmentToSave = this.modelMapper.map(apartmentDto, Apartment.class);

                    Town town = firstByTownName.get();
                    apartmentToSave.setTown(town);

                    this.apartmentRepository.saveAndFlush(apartmentToSave);

                    String successfulSaveMessage = String.format(SUCCESSFUL_ADD_PRINT_FORMAT,
                            apartmentDto.getApartmentType(),
                            apartmentDto.getArea());

                    outputMessage.append(successfulSaveMessage)
                            .append(System.lineSeparator());
                }


            } else {
                outputMessage.append(INVALID_APARTMENT_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }


        return outputMessage.toString();
    }
}
