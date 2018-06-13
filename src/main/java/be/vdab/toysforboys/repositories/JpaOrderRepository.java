package be.vdab.toysforboys.repositories;

import java.util.List;
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

	@Override
	public List<Order> findUnshippedOrders() {
		return manager.createNamedQuery("Order.findUnshippedOrders",Order.class)
					  .setHint("javax.persistence.loadgraph",manager.createEntityGraph("Order.metCustomer"))
				      .getResultList();
	}
}