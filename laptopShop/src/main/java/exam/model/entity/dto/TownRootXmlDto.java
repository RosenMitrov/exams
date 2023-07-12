package exam.model.entity.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownRootXmlDto {

    @XmlElement(name = "town")
    private List<TownSeedXmlDto> towns;

    public TownRootXmlDto() {
    }

    public List<TownSeedXmlDto> getTowns() {
        return towns;
    }

    public void setTowns(List<TownSeedXmlDto> towns) {
        this.towns = towns;
    }
}
