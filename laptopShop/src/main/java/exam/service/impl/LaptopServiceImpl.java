package exam.service.impl;

import com.google.gson.Gson;
import exam.model.entity.Laptop;
import exam.model.entity.Shop;
import exam.model.entity.dto.LaptopSeedJsonDto;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.constants.FilePaths.*;
import static exam.constants.Messages.*;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {

        final Path filePath = Path.of(LAPTOP_SEED_FILE_PATH);
        return Files.readString(filePath);
    }

    @Override
    public String importLaptops() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();
        final String laptopJsonContext = this.readLaptopsFileContent();

        final LaptopSeedJsonDto[] allLaptopsDto = this.gson.fromJson(laptopJsonContext, LaptopSeedJsonDto[].class);

        for (LaptopSeedJsonDto laptopDto : allLaptopsDto) {

            final boolean isValid = this.validationUtil.isValid(laptopDto);

            final Optional<Laptop> laptopByMacAddress = this.laptopRepository.findFirstByMacAddress(laptopDto.getMacAddress());
            final boolean laptopDoesNotExist = laptopByMacAddress.isEmpty();

            if (isValid && laptopDoesNotExist) {

                final Laptop laptopToSave = this.modelMapper.map(laptopDto, Laptop.class);
                final Shop shopToSet = this.shopRepository.findFirstByName(laptopDto.getShop().getShopName()).orElseThrow(NoSuchElementException::new);

                laptopToSave.setShop(shopToSet);

                this.laptopRepository.saveAndFlush(laptopToSave);

                final String successfullySavedMessage = String.format(LAPTOP_SUCCESSFULLY_SAVED_PRINT_FORMAT,
                        laptopDto.getMacAddress(),
                        laptopDto.getCpuSpeed(),
                        laptopDto.getRam(),
                        laptopDto.getStorage());

                outputMessage.append(successfullySavedMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage.append(LAPTOP_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }

    @Override
    public String exportBestLaptops() {

        final List<Laptop> allLaptops = this.laptopRepository
                .findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc().orElseThrow(NoSuchElementException::new);


        return allLaptops.stream()
                .map(laptop -> String.format(OUTPUT_FORMAT_MESSAGE,
                        laptop.getMacAddress(),
                        laptop.getCpuSpeed(),
                        laptop.getRam(),
                        laptop.getStorage(),
                        laptop.getPrice(),
                        laptop.getShop().getName(),
                        laptop.getShop().getTown().getName()))
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
