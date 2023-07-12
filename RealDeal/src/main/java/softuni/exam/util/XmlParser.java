package softuni.exam.util;


import javax.xml.bind.JAXBException;

public interface XmlParser {

    <T> T parseXML(String filePath, Class<T> clazzObj) throws JAXBException;

    <T> void exportXML(String filePath, Class<T> clazzObj, T entity) throws JAXBException;
}
