package be.vdab.toysforboys.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.repositories.OrderRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultOrderService implements OrderService {
	private final OrderRepository repository;

	
	public DefaultOrderService(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Order> read(long id) {
		return repository.read(id);
	}

	@Override
	public List<Order> findUnshippedOrders() {
		return repository.findUnshippedOrders();
	}
	
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean shipOrderById(long id) {
		try {
			Order order = repository.read(id).get();
			if (order.isShippable()) {
				order.ship();
				return true;
			}
			return false;
		} catch (OptimisticLockException ex) {
			return false;
		}
	}
}