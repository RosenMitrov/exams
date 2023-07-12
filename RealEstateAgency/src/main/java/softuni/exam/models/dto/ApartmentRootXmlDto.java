package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentRootXmlDto {

    @XmlElement(name = "apartment")
    private List<ApartmentSeedXmlDto> apartments;

    public ApartmentRootXmlDto() {
    }

    public List<ApartmentSeedXmlDto> getApartments() {
        return apartments;
    }

    public void setApartments(List<ApartmentSeedXmlDto> apartments) {
        this.apartments = apartments;
    }
}
