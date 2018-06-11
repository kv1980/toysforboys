package be.vdab.toysforboys.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import be.vdab.toysforboys.entities.Order;

@Repository
class JpaOrderRepository implements OrderRepository {
	EntityManager manager;
	
	public JpaOrderRepository(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public Optional<Order> read(long id) {
		return Optional.ofNullable(manager.find(Order.class, id));
	}
}