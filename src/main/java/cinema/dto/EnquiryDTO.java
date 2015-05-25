package cinema.dto;

import cinema.model.Enquiry;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EnquiryDTO {
    public Enquiry enquiry;

    @JsonCreator
    public EnquiryDTO(@JsonProperty("Enquiry") Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }
}
