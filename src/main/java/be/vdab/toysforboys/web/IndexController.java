package be.vdab.toysforboys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("/")
class IndexController {
	private static final String VIEW = "index";
	private final OrderService service;
	
	public IndexController(OrderService service) {
		this.service = service;
	}

	@GetMapping
	ModelAndView index() {
		return new ModelAndView(VIEW,"unshippedOrders",service.findUnshippedOrders());
	}
	
	@GetMapping (params = "ordersToShip")
	ModelAndView afterSetAsShipped() {
		return new ModelAndView(VIEW,"unshippedOrders",service.findUnshippedOrders());
	}
}