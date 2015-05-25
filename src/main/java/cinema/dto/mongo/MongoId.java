package cinema.dto.mongo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniel on 25.05.2015.
 */
public class MongoId {

    private String $oid;

    @JsonCreator
    public MongoId(@JsonProperty("$oid") String $oid) {
        this.$oid = $oid;
    }

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }
}
