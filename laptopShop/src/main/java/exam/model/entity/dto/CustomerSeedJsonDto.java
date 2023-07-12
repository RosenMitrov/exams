package exam.model.entity.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CustomerSeedJsonDto {

    @NotNull
    @Size(min = 2)
    @Expose
    private String firstName;
    @NotNull
    @Size(min = 2)
    @Expose
    private String lastName;
    @NotNull
    @Email
    @Expose
    private String email;
    @NotNull
    @Expose
    private LocalDate registeredOn;
    @NotNull
    @Expose
    private TownNameInfoJsonDto town;

    public CustomerSeedJsonDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public TownNameInfoJsonDto getTown() {
        return town;
    }

    public void setTown(TownNameInfoJsonDto town) {
        this.town = town;
    }
}
