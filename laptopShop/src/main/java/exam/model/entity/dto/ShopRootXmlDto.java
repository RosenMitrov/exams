package exam.model.entity.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopRootXmlDto {

    @XmlElement(name = "shop")
    private List<ShopSeedXmlDto> shops;

    public ShopRootXmlDto() {
    }

    public List<ShopSeedXmlDto> getShops() {
        return shops;
    }

    public void setShops(List<ShopSeedXmlDto> shops) {
        this.shops = shops;
    }
}
