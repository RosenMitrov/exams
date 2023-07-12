package softuni.exam.models.entity.dtos.xmls;

import softuni.exam.models.entity.RatingType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedXmlDto {

    @Size(min = 2, max = 20)
    @XmlElement(name = "first-name")
    private String firstName;
    @Size(min = 2, max = 20)
    @XmlElement(name = "last-name")
    private String lastName;
    @Email
    @XmlElement(name = "email")
    private String email;
    @NotNull
    @XmlElement(name = "rating")
    private RatingType rating;
    @NotNull
    @XmlElement(name = "town")
    private String town;

    public SellerSeedXmlDto() {
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

    public RatingType getRating() {
        return rating;
    }

    public void setRating(RatingType rating) {
        this.rating = rating;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
