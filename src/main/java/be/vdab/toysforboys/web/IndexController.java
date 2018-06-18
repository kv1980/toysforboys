package be.vdab.toysforboys.web;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("/")
class IndexController {
	private static final String VIEW = "index";
	private static final String REDIRECT_VIEW = "redirect:/";
	private final OrderService service;
	private final Set<DefaultError> errors;
	
	public IndexController(OrderService service) {
		this.service = service;
		this.errors = new HashSet<>();
	}

	@GetMapping
	ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		modelAndView.addObject("errors", errors);
		modelAndView.addObject("unshippedOrders", service.findUnshippedOrders());
		return modelAndView;
	}
	
	@PostMapping (params = "orderToShipId")
	String afterSetAsShipped(long[] orderToShipId) {
		for (long id : orderToShipId) {
			Optional<Order> order = service.read(id);
			if(order.isPresent()) {
				try {
					service.shipOrderById(id);
					if (service.read(id).get().getStatus() != Status.SHIPPED) {
						
						errors.getNotEnoughStock().add(id);
					}	
				} catch (OptimisticLockException ex) {
					errors.getFailedTransaction().add(id);
				}
			} else {
				errorList.getNotFound().add(id);
			}
		}
		return REDIRECT_VIEW;
	}
	
	@PostMapping ()
	String afterSetAsShippedWithNoOrderIds() {
		return REDIRECT_VIEW;
	}
}