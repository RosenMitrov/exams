package softuni.exam.models.entity.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerRootXmlDto {


    @XmlElement(name = "seller")
    private List<SellerSeedXmlDto> sellers;

    public SellerRootXmlDto() {
    }

    public List<SellerSeedXmlDto> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerSeedXmlDto> sellers) {
        this.sellers = sellers;
    }
}
