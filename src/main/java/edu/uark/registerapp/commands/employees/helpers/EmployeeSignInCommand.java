package edu.uark.registerapp.commands.employees.helpers;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee> {
	@Override
	public Employee execute() {
		this.validateProperties();
        

        return new Employee(this.SignInEmployee());
        
    }
    
	@Transactional
	private EmployeeEntity SignInEmployee() {
		final Optional<EmployeeEntity> employeeEntity =
			this.employeeRepository.findByEmployeeId(
				Integer.parseInt(this.employeeSignIn.getEmployeeId()));

		if (!employeeEntity.isPresent()
			|| !Arrays.equals(
				employeeEntity.get().getPassword(),
				EmployeeHelper.hashPassword(this.employeeSignIn.getPassword()))
		) {

			throw new UnauthorizedException();
		}

		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository
				.findByEmployeeId(employeeEntity.get().getId());

		if (!activeUserEntity.isPresent()) {
			this.activeUserRepository.save(
					(new ActiveUserEntity())
						.setSessionKey(this.sessionKey)
						.setEmployeeId(employeeEntity.get().getId())
						.setClassification(
							employeeEntity.get().getClassification())
						.setName(
							employeeEntity.get().getFirstName()
								.concat(" ")
								.concat(employeeEntity.get().getLastName())));
		} else {
			this.activeUserRepository.save(
				activeUserEntity.get().setSessionKey(this.sessionKey));
		}

        return employeeEntity.get();
        
    }


    //Helper method
    private void validateProperties() {
		if (StringUtils.isBlank(this.employeeSignIn.getEmployeeId())) {
			throw new UnprocessableEntityException("employee ID");
		}
		try {
			Integer.parseInt(this.employeeSignIn.getEmployeeId());
		} catch (final NumberFormatException e) {
			throw new UnprocessableEntityException("employee ID");
		}
		if (StringUtils.isBlank(this.employeeSignIn.getPassword())) {
			throw new UnprocessableEntityException("password");
		}
    }
    
    
    //Properties
    private EmployeeSignIn employeeSignIn;
    private String sessionKey;
    
    public EmployeeSignIn getEmployeeSignIn() { return this.employeeSignIn; }

	public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
		this.employeeSignIn = employeeSignIn;
		return this;
    }

    public String getSessionKey() { return this.sessionKey; }

	public EmployeeSignInCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
    }


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
	private ActiveUserRepository activeUserRepository;

}
