package softuni.exam.models.dto.xmlSeed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastRootXmlDto {

    @XmlElement(name = "forecast")
    private List<ForecastSeedXmlDto> forecasts;

    public ForecastRootXmlDto() {
    }

    public List<ForecastSeedXmlDto> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastSeedXmlDto> forecasts) {
        this.forecasts = forecasts;
    }
}
