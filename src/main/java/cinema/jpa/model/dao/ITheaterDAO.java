package cinema.jpa.model.dao;

import cinema.jpa.model.Theater;

import java.util.List;

/**
 */
public interface ITheaterDAO {
    public List<Theater> findAll();
    public Theater save(Theater movie);
    public void delete(Theater movie);
}
