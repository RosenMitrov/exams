package exam.config;

import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance();
    }

    @Bean
    public Validator validator(){
        return Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson gson(){
     return    new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
             .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                 //FROM JSON TO OBJECT
                 //FROM LocalDate to LocalDate
                 @Override
                 public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

                     DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                     return LocalDate.parse(jsonElement.getAsString(), dateTimeFormatter);
                 }
             })
                .create();
        //.serializeNulls()
    }
}
