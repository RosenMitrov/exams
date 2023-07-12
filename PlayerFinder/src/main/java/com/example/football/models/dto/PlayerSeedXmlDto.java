package com.example.football.models.dto;

import com.example.football.config.LocalDateAdapter;
import com.example.football.models.entity.PositionType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedXmlDto {

    @NotNull
    @Size(min = 2)
    @XmlElement(name = "first-name")
    private String firstName;

    @NotNull
    @Size(min = 2)
    @XmlElement(name = "last-name")
    private String lastName;

    @NotNull
    @Email
    @XmlElement(name = "email")
    private String email;

    @NotNull
    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;//"dd/MM/yyyy"
    @XmlElement(name = "position")
    @NotNull
    private PositionType position;

    @XmlElement(name = "town")
    private TownInfoXmlDto townName;

    @XmlElement(name = "team")
    private TeamInfoXmlDto teamName;

    @XmlElement(name = "stat")
    private StatInfoXmlDto stat;

    public PlayerSeedXmlDto() {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PositionType getPosition() {
        return position;
    }

    public void setPosition(PositionType position) {
        this.position = position;
    }

    public TownInfoXmlDto getTownName() {
        return townName;
    }

    public void setTownName(TownInfoXmlDto townName) {
        this.townName = townName;
    }

    public TeamInfoXmlDto getTeamName() {
        return teamName;
    }

    public void setTeamName(TeamInfoXmlDto teamName) {
        this.teamName = teamName;
    }

    public StatInfoXmlDto getStat() {
        return stat;
    }

    public void setStat(StatInfoXmlDto stat) {
        this.stat = stat;
    }
}
