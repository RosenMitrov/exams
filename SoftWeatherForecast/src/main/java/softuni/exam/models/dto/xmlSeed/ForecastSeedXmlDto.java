package softuni.exam.models.dto.xmlSeed;

import softuni.exam.config.TimeAdapter;
import softuni.exam.models.entity.DayWeek;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Time;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedXmlDto {

    @NotNull
    @XmlElement(name = "day_of_week")
    private DayWeek dayOfWeek;
    @NotNull
    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;
    @NotNull
    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    @XmlElement(name = "min_temperature")
    private Double minTemperature;
    @NotNull
    @XmlJavaTypeAdapter(TimeAdapter.class)
    @XmlElement(name = "sunrise")
    private Time sunrise;
    @NotNull
    @XmlJavaTypeAdapter(TimeAdapter.class)
    @XmlElement(name = "sunset")
    private Time sunset;
    @XmlElement(name = "city")
    private Long city;

    public ForecastSeedXmlDto() {
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

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}
