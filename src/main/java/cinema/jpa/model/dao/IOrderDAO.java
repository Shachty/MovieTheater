package cinema.jpa.model.dao;

import cinema.jpa.model.Order;

import java.util.List;

/**
 */
public interface IOrderDAO {
    public List<Order> findAll();
    public Order save(Order movie);
    public void delete(Order movie);
}
