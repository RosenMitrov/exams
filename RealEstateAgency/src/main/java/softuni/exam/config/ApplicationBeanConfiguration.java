package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance();
    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        //.serializeNulls()
    }


    @Bean
    public Validator validator() {
        return Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
