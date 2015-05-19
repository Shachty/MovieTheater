package cinema.jpa.model.dao;

import cinema.jpa.model.Screening;

import java.util.List;

/**
 */
public interface IScreeningDAO {
    public List<Screening> findAll();
    public Screening save(Screening movie);
    public void delete(Screening movie);
}
