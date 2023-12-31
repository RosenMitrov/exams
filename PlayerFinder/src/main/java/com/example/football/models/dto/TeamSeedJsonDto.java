package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeamSeedJsonDto {

    @NotNull
    @Size(min = 3)
    @Expose
    private String name;

    @NotNull
    @Size(min = 3)
    @Expose
    private String stadiumName;

    @NotNull
    @Min(1000)
    @Expose
    private Integer fanBase;

    @NotNull
    @Size(min = 10)
    @Expose
    private String history;

    @Expose
    private String townName;

    public TeamSeedJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Integer getFanBase() {
        return fanBase;
    }

    public void setFanBase(Integer fanBase) {
        this.fanBase = fanBase;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
