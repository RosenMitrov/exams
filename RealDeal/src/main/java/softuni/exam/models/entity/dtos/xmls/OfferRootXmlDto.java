package softuni.exam.models.entity.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootXmlDto {

    @XmlElement(name = "offer")
    private List<OfferSeedXmlDto> offers;

    public OfferRootXmlDto() {
    }

    public List<OfferSeedXmlDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferSeedXmlDto> offers) {
        this.offers = offers;
    }
}
