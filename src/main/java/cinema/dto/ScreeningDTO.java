package cinema.dto;

import cinema.model.Screening;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScreeningDTO {

    private Screening screening;

    @JsonCreator
    public ScreeningDTO(@JsonProperty("Screening") Screening screening) {
        this.screening = screening;
    }

    public Screening getScreening() {
        return screening;
    }


    public void setScreening(Screening screening) {
        this.screening = screening;

    }
}
