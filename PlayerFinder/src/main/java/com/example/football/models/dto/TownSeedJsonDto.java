package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownSeedJsonDto {

    @NotNull
    @Size(min = 2)
    @Expose
    private String name;

    @NotNull
    @Positive
    @Expose
    private Integer population;

    @NotNull
    @Size(min = 10)
    @Expose
    private String travelGuide;

    public TownSeedJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
