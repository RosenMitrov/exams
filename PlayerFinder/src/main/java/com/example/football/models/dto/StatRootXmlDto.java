package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatRootXmlDto {

    @XmlElement(name = "stat")
    private List<StatSeedXmlDto> stats;

    public StatRootXmlDto() {
    }

    public List<StatSeedXmlDto> getStats() {
        return stats;
    }

    public void setStats(List<StatSeedXmlDto> stats) {
        this.stats = stats;
    }
}
