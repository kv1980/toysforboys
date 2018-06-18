package be.vdab.toysforboys.services;

import java.util.List;
import java.util.Optional;

import be.vdab.toysforboys.entities.Order;

public interface OrderService {
	Optional<Order> read(long id);
	List<Order> findUnshippedOrders();
	void shipOrderById(long id);
}
