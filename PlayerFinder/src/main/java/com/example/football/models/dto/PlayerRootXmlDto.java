package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerRootXmlDto {

    @XmlElement(name = "player")
    private List<PlayerSeedXmlDto> players;

    public PlayerRootXmlDto() {
    }

    public List<PlayerSeedXmlDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerSeedXmlDto> players) {
        this.players = players;
    }
}
