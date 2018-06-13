package be.vdab.toysforboys.web;

import java.util.Set;

public interface OrdersToShip {
	void addOrderId(long orderId);
	Set<Long> getOrderIds();
}
