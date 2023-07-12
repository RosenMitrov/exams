package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedJsonDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    private static final String AGENT_INPUT_FILE_PATH = "src/main/resources/files/json/agents.json";
    private static final String SUCCESSFUL_ADD_PRINT_FORMAT = "Successfully imported agent - %s %s";
    private static final String INVALID_AGENT_PRINT_MESSAGE = "Invalid agent";
    private final AgentRepository agentRepository;

    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {

        final Path filePath = Path.of(AGENT_INPUT_FILE_PATH);

        return Files.readString(filePath);
    }

    @Override
    public String importAgents() throws IOException {

        StringBuilder outputMessage = new StringBuilder();

        String context = this.readAgentsFromFile();

        AgentSeedJsonDto[] allAgents = this.gson.fromJson(context, AgentSeedJsonDto[].class);

        for (AgentSeedJsonDto agentDto : allAgents) {

            boolean isValid = this.validationUtil.isValid(agentDto);

            Optional<Agent> byFirstName = this.agentRepository.findFirstByFirstName(agentDto.getFirstName());

            boolean isEmpty = byFirstName.isEmpty();


            if (isValid && isEmpty) {

                Agent agentToSave = this.modelMapper.map(agentDto, Agent.class);

                Optional<Town> byTownName = this.townRepository.findFirstByTownName(agentDto.getTown());

                if (byTownName.isPresent()) {

                    Town town = byTownName.get();
                    agentToSave.setTown(town);
                    this.agentRepository.saveAndFlush(agentToSave);

                    String successfulSaveMessage = String.format(SUCCESSFUL_ADD_PRINT_FORMAT,
                            agentDto.getFirstName(),
                            agentDto.getLastName());

                    outputMessage.append(successfulSaveMessage)
                            .append(System.lineSeparator());
                }

            } else {

                outputMessage.append(INVALID_AGENT_PRINT_MESSAGE)
                        .append(System.lineSeparator());

            }
        }
        return outputMessage.toString();
    }
}












