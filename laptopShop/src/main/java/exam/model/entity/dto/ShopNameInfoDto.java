package exam.model.entity.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ShopNameInfoDto {

    @NotNull
    @Size(min = 4)
    @Expose
    @SerializedName("name")
    private String shopName;

    public ShopNameInfoDto() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
