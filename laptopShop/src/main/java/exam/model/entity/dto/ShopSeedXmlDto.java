package exam.model.entity.dto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedXmlDto {

    @NotNull
    @Size(min = 4)
    @XmlElement(name = "address")
    private String address;

    @NotNull
    @Min(1)
    @Max(50)
    @XmlElement(name = "employee-count")
    private Integer employeeCount;

    @NotNull
    @DecimalMin("20000")
    @XmlElement(name = "income")
    private BigDecimal income;

    @NotNull
    @Size(min = 4)
    @XmlElement(name = "name")
    private String name;

    @NotNull
    @Min(150)
    @XmlElement(name = "shop-area")
    private Integer shopArea;

    @NotNull
    @XmlElement(name = "town")
    private TownNameInfoXmlDto town;

    public ShopSeedXmlDto() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public void setShopArea(Integer shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameInfoXmlDto getTown() {
        return town;
    }

    public void setTown(TownNameInfoXmlDto town) {
        this.town = town;
    }
}
