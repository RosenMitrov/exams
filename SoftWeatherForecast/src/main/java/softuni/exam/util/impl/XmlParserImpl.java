package softuni.exam.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {

    private JAXBContext jaxbContext;

    @Autowired
    public XmlParserImpl(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T parseXML(String filePath, Class<T> objClazz) throws JAXBException {

        this.jaxbContext = JAXBContext.newInstance(objClazz);

        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();

        File file = new File(filePath);

        return (T) unmarshaller.unmarshal(file);
    }

    @Override
    public <T> void exportXML(String filePath, Class<T> objClazz, T object) throws JAXBException {

        this.jaxbContext = JAXBContext.newInstance(objClazz);

        Marshaller marshaller = this.jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File(filePath);

        marshaller.marshal(object, file);

        marshaller.marshal(object, System.out);
    }
}
