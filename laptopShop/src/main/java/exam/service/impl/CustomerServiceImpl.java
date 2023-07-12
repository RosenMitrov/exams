package exam.service.impl;

import com.google.gson.Gson;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.model.entity.dto.CustomerSeedJsonDto;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;

import static exam.constants.FilePaths.*;
import static exam.constants.Messages.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {

        final Path filePath = Path.of(CUSTOMER_SEED_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importCustomers() throws IOException {

        final StringBuilder outputMessage = new StringBuilder();
        final String customerJsonContext = this.readCustomersFileContent();

        final CustomerSeedJsonDto[] allCustomersDto = this.gson.fromJson(customerJsonContext, CustomerSeedJsonDto[].class);

        for (CustomerSeedJsonDto customerDto : allCustomersDto) {

            final boolean isValid = this.validationUtil.isValid(customerDto);

            final Optional<Customer> customerByEmail = this.customerRepository.findFirstByEmail(customerDto.getEmail());
            final boolean customerDoesNotExist = customerByEmail.isEmpty();

            if (isValid && customerDoesNotExist) {

                final Customer customerToSave = this.modelMapper.map(customerDto, Customer.class);

                final Town townToSet = this.townRepository.findFirstByName(customerDto.getTown().getTownName()).orElseThrow(NoSuchElementException::new);

                customerToSave.setTown(townToSet);

                this.customerRepository.saveAndFlush(customerToSave);

                final String successfullySavedMessage = String.format(CUSTOMER_SUCCESSFULLY_SAVED_PRINT_FORMAT,
                        customerDto.getFirstName(),
                        customerDto.getLastName(),
                        customerDto.getEmail());

                outputMessage.append(successfullySavedMessage)
                        .append(System.lineSeparator());
            } else {
                outputMessage.append(CUSTOMER_INVALID_PRINT_MESSAGE)
                        .append(System.lineSeparator());
            }
        }
        return outputMessage
                .toString()
                .trim();
    }
}
