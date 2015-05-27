package cinema.jpa.model.dao;

import cinema.jpa.model.Enquiry;

import java.util.List;

/**
 */
public interface IEnquiryDAO {
    public List<Enquiry> findAll();
    public Enquiry save(Enquiry movie);
    public void delete(Enquiry movie);
}
