package cinema.dto.mongo;

import cinema.model.Screening;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniel on 24.05.2015.
 */
public class ScreeningMongoDTO {

    private Screening screening;
    private MongoId _id;

    @JsonCreator
    public ScreeningMongoDTO(@JsonProperty("_id") MongoId _id,
                             @JsonProperty("Screening") Screening screening) {
        this.screening = screening;
        this._id=_id;
    }

    public Screening getScreening() {
        return screening;
    }

    public MongoId get_id() {
        return _id;
    }

    public void set_id(MongoId _id) {
        this._id = _id;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;

    }
}
