package be.vdab.toysforboys.repositories;

import java.util.Optional;

import be.vdab.toysforboys.entities.Order;

public interface OrderRepository {
	Optional<Order> read(long id);
}
