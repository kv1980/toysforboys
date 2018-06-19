package be.vdab.toysforboys.web;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private static final String REDIRECT_START= "redirect:/1";
	private static final String REDIRECT_VIEW = "redirect:/";
	private final OrderService service;
	private final Errors errors;
	private static final int NUMBER_OF_ORDERS_IN_PAGE = 10;
	
	public IndexController(OrderService service, Errors errors) {
		this.service = service;
		this.errors = errors;
	}
	
	@GetMapping
	String index() {
		return REDIRECT_START;
	}

	@GetMapping ("{pagenr}")
	ModelAndView index(@PathVariable int pagenr) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		modelAndView.addObject("errors", errors.getErrors());
		List<Order> unshippedOrderList = service.findUnshippedOrders();
		int numberOfPages = unshippedOrderList.size()/NUMBER_OF_ORDERS_IN_PAGE+1;
		int indexFirst = (pagenr-1)*NUMBER_OF_ORDERS_IN_PAGE;
		int indexLast = (pagenr < numberOfPages) ? (pagenr*NUMBER_OF_ORDERS_IN_PAGE)+1 : unshippedOrderList.size()-1; 
		modelAndView.addObject("numberOfPages",numberOfPages);
		modelAndView.addObject("unshippedOrders", service.findUnshippedOrders().subList(indexFirst,indexLast));
		modelAndView.addObject("pagenr",pagenr);
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