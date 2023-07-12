package softuni.exam.models.dto.jsonSeed;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CountryJsonSeedDto {

    @Expose
    @Size(min = 2,max = 60)
    @NotNull
    private String countryName;
    @Expose
    @Size(min = 2,max = 20)
    @NotNull
    private String currency;

    public CountryJsonSeedDto() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
