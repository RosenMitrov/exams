package exam.model.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedXmlDto {

    @NotNull
    @Size(min = 2)
    @XmlElement(name = "name")
    private String name;
    @NotNull
    @Positive
    @XmlElement(name = "population")
    private Integer population;
    @NotNull
    @Size(min = 10)
    @XmlElement(name = "travel-guide")
    private String travelGuide;

    public TownSeedXmlDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
