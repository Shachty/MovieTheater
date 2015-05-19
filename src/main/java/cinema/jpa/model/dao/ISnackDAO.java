package cinema.jpa.model.dao;

import cinema.jpa.model.Snack;

import java.util.List;

/**
 */
public interface ISnackDAO {
    public List<Snack> findAll();
    public Snack save(Snack movie);
    public void delete(Snack movie);
}
