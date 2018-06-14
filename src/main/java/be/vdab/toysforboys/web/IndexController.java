package be.vdab.toysforboys.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		StringBuilder boodschap = new StringBuilder();
		for (long id : orderToShipId) {
			if (!service.shipOrderById(id)) {
				boodschap.append(id);
				boodschap.append(",");
			}
		}
		redirectAttributes.addAttribute("boodschap",boodschap);
		return REDIRECT_VIEW;
	}
}