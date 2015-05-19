package cinema.jpa.model.dao;

import cinema.jpa.model.Movie;

import java.util.List;

/**
 */
public interface IMovieDAO {
    public List<Movie> findAll();
    public Movie save(Movie movie);
    public void delete(Movie movie);
}
