package cinema.dto.mongo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScreeningsMongoDTO {


    private List<ScreeningMongoDTO> screenings;

    @JsonCreator
    public ScreeningsMongoDTO(
            @JsonProperty List<ScreeningMongoDTO> screenings) {
        this.screenings = screenings;
    }

    public List<ScreeningMongoDTO> getScreenings() {
        return this.screenings;
    }

    public void setScreenings(List<ScreeningMongoDTO> screenings) {
        this.screenings = screenings;
    }
}
