package softuni.exam.models.entity.dtos.xmls;

import softuni.exam.config.adapters.LocalDateTimeXmlAdapter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedXmlDto {

    @Size(min = 5)
    @XmlElement(name = "description")
    private String description;

    @Positive
    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    @XmlElement(name = "added-on")
    private LocalDateTime addedOn;

    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;

    @XmlElement(name = "car")
    private CarInfoXmlDto car;

    @XmlElement(name = "seller")
    private SellerInfoXmlDto seller;

    public OfferSeedXmlDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public CarInfoXmlDto getCar() {
        return car;
    }

    public void setCar(CarInfoXmlDto car) {
        this.car = car;
    }

    public SellerInfoXmlDto getSeller() {
        return seller;
    }

    public void setSeller(SellerInfoXmlDto seller) {
        this.seller = seller;
    }
}
