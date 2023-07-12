package softuni.exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <T> T parseXML(String filePath, Class<T> objectClazz) throws JAXBException;

    <T> void exportXML(String filePath, Class<T> objClazz, T object) throws JAXBException;
}
