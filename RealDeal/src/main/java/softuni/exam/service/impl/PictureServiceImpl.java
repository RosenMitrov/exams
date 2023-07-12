package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.dtos.jsons.PictureSeedJsonDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.constants.FilePaths.PICTURE_SEED_FILE_PATH;
import static softuni.exam.constants.StaticMessage.PICTURE_INVALID_PRINT_MESSAGE;
import static softuni.exam.constants.StaticMessage.PICTURE_SUCCESSFUL_PRINT_MESSAGE;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              CarRepository carRepository,
                              Gson gson,
                              ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {

        final Path filePath = Path.of(PICTURE_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importPictures() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();
        final String picturesContext = this.readPicturesFromFile();
        final PictureSeedJsonDto[] allPictures = this.gson.fromJson(picturesContext, PictureSeedJsonDto[].class);

        for (PictureSeedJsonDto pictureDto : allPictures) {

            final boolean isPictureValid = this.validationUtil.isValid(pictureDto);

            final Optional<Picture> pictureByName = this.pictureRepository.findFirstByName(pictureDto.getName());
            boolean isPicturePresent = pictureByName.isPresent();

            if (isPictureValid && !isPicturePresent) {

                final Picture pictureToSave = this.modelMapper.map(pictureDto, Picture.class);
                final Car carToBeSet = this.carRepository.findById(pictureDto.getCardId()).orElse(null);

                pictureToSave.setCar(carToBeSet);
                this.pictureRepository.saveAndFlush(pictureToSave);

                final String successfullySavedMessage = String.format(PICTURE_SUCCESSFUL_PRINT_MESSAGE,
                        pictureDto.getName());

                outputMessage
                        .append(successfullySavedMessage)
                        .append(System.lineSeparator());

            } else {
                outputMessage
                        .append(PICTURE_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}
