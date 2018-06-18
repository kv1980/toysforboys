package be.vdab.toysforboys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("/")
class IndexController {
	private static final String VIEW = "index";
	private static final String REDIRECT_VIEW = "redirect:/";
	private final OrderService service;
	
	public IndexController(OrderService service) {
		this.service = service;
	}

	@GetMapping
	ModelAndView index() {
		return new ModelAndView(VIEW,"unshippedOrders",service.findUnshippedOrders());
	}
	
	@PostMapping (params = "orderToShipId")
	String afterSetAsShipped(long[] orderToShipId, RedirectAttributes redirectAttributes) {
		List<Error> errorList = new ArrayList<>();
		Error notFound = new Error("the order(s) were not found.");
		Error notEnoughStock = new Error("there was not enough stock, please click order id for more information.");

		Error failedTransaction = new Error("the transaction failed, please try again.");
		
		for (long id : orderToShipId) {
			Optional<Order> order = service.read(id);
			if(order.isPresent()) {
				try {
					order.get().ship();
					if (order.get().getStatus() != Status.SHIPPED) {
						
					}	
				} catch (OptimisticLockException ex) {

				}
			} else {
				
			}
		}
		
		errorList.add(notFound);
		errorList.add(notEnoughStock);
		errorList.add(failedTransaction);
		
		
		
		redirectAttributes.addAttribute("errors",errorList);
		return REDIRECT_VIEW;
	}
	
	@PostMapping ()
	String afterSetAsShippedWithNoOrderIds() {
		return REDIRECT_VIEW;
	}
}