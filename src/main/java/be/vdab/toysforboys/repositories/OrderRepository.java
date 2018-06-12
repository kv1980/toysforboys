package be.vdab.toysforboys.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.toysforboys.entities.Order;

public interface OrderRepository {
	Optional<Order> read(long id);
	List<Order> findUnshippedOrders();
}
