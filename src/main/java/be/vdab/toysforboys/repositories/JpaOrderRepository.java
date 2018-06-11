package be.vdab.toysforboys.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import be.vdab.toysforboys.entities.Order;

@Repository
class JpaOrderRepository implements OrderRepository {

	@Override
	public Optional<Order> read(long id) {
		throw new UnsupportedOperationException();
	}
}