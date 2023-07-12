package softuni.exam.models.dto.jsonSeed;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CityJsonSeedDto {

    @Expose
    @Size(min = 2, max = 60)
    @NotNull
    private String cityName;
    @Expose
    @Size(min = 2)
    private String description;
    @Expose
    @Min(value = 500)
    @NotNull
    private Integer population;
    @Expose
    private Long country;

    public CityJsonSeedDto() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
