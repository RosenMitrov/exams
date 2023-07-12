package softuni.exam.models.entity.dtos.jsons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import softuni.exam.config.adapters.LocalDateJsonAdapter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CarSeedJsonDto {

    @Size(min = 2, max = 20)
    @Expose
    private String make;
    @Size(min = 2, max = 20)
    @Expose
    private String model;
    @Positive
    @Expose
    private Integer kilometers;
    @JsonAdapter(LocalDateJsonAdapter.class)
    @Expose
    private LocalDate registeredOn;

    public CarSeedJsonDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }
}
