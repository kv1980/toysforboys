package be.vdab.toysforboys.web;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class DefaultOrdersToShip implements OrdersToShip {
	private final Set<Long> orderIds = new LinkedHashSet<>();

	@Override
	public void addOrderId(long orderId) {
		orderIds.add(orderId);
	}

	@Override
	public Set<Long> getOrderIds() {
		return orderIds;
	}
}