package softuni.exam.models.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayWeek dayOfWeek;
    @Column(name = "max_temperature", nullable = false)
    private Double maxTemperature;
    @Column(name = "min_temperature", nullable = false)
    private Double minTemperature;
    @Column(name = "sunrise", nullable = false)
    private Time sunrise;
    @Column(name = "sunset", nullable = false)
    private Time sunset;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Forecast() {
    }

    public DayWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Time getSunrise() {
        return sunrise;
    }

    public void setSunrise(Time sunrise) {
        this.sunrise = sunrise;
    }

    public Time getSunset() {
        return sunset;
    }

    public void setSunset(Time sunset) {
        this.sunset = sunset;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format("City: %s:" + System.lineSeparator() +
                        "-min temperature: %.2f" + System.lineSeparator() +
                        "--max temperature: %.2f" + System.lineSeparator() +
                        "---sunrise: %s" + System.lineSeparator() +
                        "----sunset: %s",
                this.city.getCityName(),
                this.minTemperature,
                this.maxTemperature,
                this.sunrise,
                this.sunset);

    }
}
