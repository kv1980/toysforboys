package be.vdab.toysforboys.web;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;

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
	private final Errors errors;
	
	public IndexController(OrderService service, Errors errors) {
		this.service = service;
		this.errors = errors;
	}

	@GetMapping
	ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		modelAndView.addObject("errors", errors.getErrors());
		modelAndView.addObject("unshippedOrders", service.findUnshippedOrders());
		return modelAndView;
	}
	
	@PostMapping (params = "orderToShipId")
	String afterSetAsShipped(long[] orderToShipId) {
		errors.removeAllPreviousErrors();
		for (long id : orderToShipId) {
			Optional<Order> order = service.read(id);
			if(order.isPresent()) {
				try {
					service.shipOrderById(id);
					if (service.read(id).get().getStatus() != Status.SHIPPED) {
						errors.addError("there was not enough stock, please click order ID for more detailled information",id);
					}	
				} catch (OptimisticLockException ex) {
					errors.addError("the transaction failed, please try again",id);
				}
			} else {
				errors.addError("the order(s) were not found.",id);
			}
		}
		return REDIRECT_VIEW;
	}
	
	@PostMapping ()
	String afterSetAsShippedWithNoOrderIds() {
		return REDIRECT_VIEW;
	}
}