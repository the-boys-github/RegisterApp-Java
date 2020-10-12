package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import edu.uark.registerapp.commands.employees.EmployeeQuery;
import edu.uark.registerapp.commands.employees.helpers.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@Autowired
	ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

	@Autowired
	EmployeeQuery employeeQuery;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		// TODO: Logic to determine if the user associated with the current session
		//  is able to create an employee
		ModelMap employeeViewModel = new ModelMap();
		try{
			activeEmployeeExistsQuery.execute();

		} catch (Exception e){
			return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName(), new ModelMap().addAttribute(ViewModelNames.ERROR_MESSAGE.getValue(), e.getMessage()));
		}

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		if(!activeUserEntity.isPresent()){
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()));
		}
		else if(this.isElevatedUser(activeUserEntity.get())){
			return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName(), employeeViewModel.addAttribute("employee", new ModelMap().addAttribute("isEmpty", true).addAttribute("isInitialEmployee", false)));
		}
		else{
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()), new ModelMap().addAttribute(ViewModelNames.ERROR_MESSAGE.getValue(), QueryParameterMessages.NO_PERMISSIONS_FOR_ACTION.getMessage()));
		}
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
		}

		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
		Employee employeeFound = employeeQuery.setId(employeeId).execute();

		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName(), new ModelMap("employee", employeeFound));

	}

	// Helper methods
	private boolean activeUserExists() {
		// TODO: Helper method to determine if any active users Exist
		return true;
	}
}
