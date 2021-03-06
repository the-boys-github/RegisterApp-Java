package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.models.entities.ActiveUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/productDetail")
public class ProductDetailRouteController extends BaseRouteController{
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(final HttpServletRequest request) {
		ModelAndView modelandView = new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName());
		modelandView.addObject(
				ViewModelNames.PRODUCT.getValue(),
				(new Product()).setLookupCode(StringUtils.EMPTY).setCount(0));
		Optional<ActiveUserEntity> currentUser = this.getCurrentUser(request);
		if(!currentUser.isPresent() || !this.isElevatedUser(currentUser.get())){
			modelandView.addObject("isElevatedUser", false);
		}
		else{
			modelandView.addObject("isElevatedUser", true);
		}

		return modelandView;
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView startWithProduct(@PathVariable final UUID productId, final HttpServletRequest request) {
		final ModelAndView modelAndView =
			new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName());

		Optional<ActiveUserEntity> currentUser = this.getCurrentUser(request);
		if(!currentUser.isPresent() || !this.isElevatedUser(currentUser.get())){
			modelAndView.addObject("isElevatedUser", false);
		}
		else{
			modelAndView.addObject("isElevatedUser", true);
		}
		try {
			Product productFound = this.productQuery.setProductId(productId).execute();
			modelAndView.addObject(
				ViewModelNames.PRODUCT.getValue(),
				this.productQuery.setProductId(productId).execute());
		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.PRODUCT.getValue(),
				(new Product())
					.setCount(0)
					.setLookupCode(StringUtils.EMPTY));
		}

		return modelAndView;
	}

	// Properties
	@Autowired
	private ProductQuery productQuery;
}
