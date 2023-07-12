package exam.model.entity.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TownNameInfoJsonDto {


    @NotNull
    @Size(min = 2)
    @Expose
    @SerializedName(value = "name")
    private String townName;

    public TownNameInfoJsonDto() {
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
