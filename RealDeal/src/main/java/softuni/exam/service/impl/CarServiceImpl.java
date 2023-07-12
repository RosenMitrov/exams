package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.dtos.jsons.CarSeedJsonDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.exam.constants.FilePaths.CAR_SEED_FILE_PATH;
import static softuni.exam.constants.StaticMessage.*;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          Gson gson,
                          ValidationUtil validationUtil,
                          ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {

        final Path filePath = Path.of(CAR_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importCars() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();
        final String carContext = this.readCarsFileContent();
        final CarSeedJsonDto[] allCars = this.gson.fromJson(carContext, CarSeedJsonDto[].class);

        for (CarSeedJsonDto carDto : allCars) {

            final boolean isCarValid = this.validationUtil.isValid(carDto);

            if (isCarValid) {

                final Car carToSave = this.modelMapper.map(carDto, Car.class);
                this.carRepository.saveAndFlush(carToSave);

                final String successfullySavedMessage = String.format(CAR_SUCCESSFUL_PRINT_MESSAGE,
                        carDto.getMake(),
                        carDto.getModel());

                outputMessage
                        .append(successfullySavedMessage)
                        .append(System.lineSeparator());

            } else {
                outputMessage
                        .append(CAR_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {

        final List<Car> allCarsSorted = this.carRepository.findAllByOrderPicturesCountDescMakeAsc().orElseThrow(NoSuchElementException::new);

        return allCarsSorted
                .stream()
                .map(car -> String.format(OUTPUT_FORMAT,
                        car.getMake(), car.getModel(),
                        car.getKilometers(),
                        car.getRegisteredOn(),
                        car.getPictures().size()
                ))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
