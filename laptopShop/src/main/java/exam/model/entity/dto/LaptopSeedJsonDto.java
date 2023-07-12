package exam.model.entity.dto;

import com.google.gson.annotations.Expose;
import exam.model.entity.enums.WarrantyType;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class LaptopSeedJsonDto {

    @NotNull
    @Size(min = 8)
    @Expose
    private String macAddress;
    @NotNull
    @Positive
    @Expose
    private Double cpuSpeed;
    @NotNull
    @Min(8)
    @Max(128)
    @Expose
    private Integer ram;
    @NotNull
    @Min(128)
    @Max(1024)
    @Expose
    private Integer storage;
    @NotNull
    @Size(min = 10)
    @Expose
    private String description;
    @NotNull
    @Positive
    @Expose
    private BigDecimal price;
    @NotNull
    @Expose
    private WarrantyType warrantyType;
    @NotNull
    @Expose
    private ShopNameInfoDto shop;

    public LaptopSeedJsonDto() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
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

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopNameInfoDto getShop() {
        return shop;
    }

    public void setShop(ShopNameInfoDto shop) {
        this.shop = shop;
    }
}
