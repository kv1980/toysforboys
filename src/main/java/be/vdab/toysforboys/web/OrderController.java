package be.vdab.toysforboys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("order")
class OrderController {
	private static final String INDEX_VIEW = "redirect:/";
	private static final String VIEW = "order";
	private final OrderService service;
	
	public OrderController(OrderService service) {
		this.service = service;
	}
	
	@GetMapping("{id}")
	ModelAndView order(@PathVariable long id) {
		if (service.read(id).isPresent()) {
			ModelAndView modelAndView = new ModelAndView(VIEW);
			Order order = service.read(id).get();
			modelAndView.addObject(order);
			return modelAndView;
		} else {
			return new ModelAndView(INDEX_VIEW);
		}
	}
}