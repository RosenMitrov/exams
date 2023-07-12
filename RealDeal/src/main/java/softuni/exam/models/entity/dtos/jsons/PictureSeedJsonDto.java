package softuni.exam.models.entity.dtos.jsons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import softuni.exam.config.adapters.LocalDateTimeJsonAdapter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PictureSeedJsonDto {

    @Size(min = 2, max = 20)
    @Expose
    private String name;
    @Expose
    @JsonAdapter(LocalDateTimeJsonAdapter.class)
    private LocalDateTime dateAndTime;
    @Expose
    @SerializedName("car")
    private Long cardId;

    public PictureSeedJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
