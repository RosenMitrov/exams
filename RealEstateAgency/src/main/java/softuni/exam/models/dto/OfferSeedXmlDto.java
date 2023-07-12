package softuni.exam.models.dto;

import softuni.exam.config.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedXmlDto {

    @NotNull
    @Positive
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "agent")
    private AgentInfoXmlDto agent;
    @XmlElement(name = "apartment")
    private ApartmentInfoXmlDto apartment;
    @NotNull
    @XmlElement(name = "publishedOn")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate publishedOn;

    public OfferSeedXmlDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AgentInfoXmlDto getAgent() {
        return agent;
    }

    public void setAgent(AgentInfoXmlDto agent) {
        this.agent = agent;
    }

    public ApartmentInfoXmlDto getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentInfoXmlDto apartment) {
        this.apartment = apartment;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }
}
