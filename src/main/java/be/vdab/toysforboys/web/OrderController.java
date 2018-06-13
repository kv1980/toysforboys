package be.vdab.toysforboys.web;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("order")
class OrderController {
	private static final String VIEW = "order";
	private final OrderService service;
	
	public OrderController(OrderService service) {
		this.service = service;
	}

	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		service.read(id).ifPresent(order -> {
			modelAndView.addObject(order);
			BigDecimal totalValue = order.getTotalValue();
			modelAndView.addObject("totalValue",totalValue);
		});
		return modelAndView;
	}
}
