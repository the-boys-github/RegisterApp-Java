package edu.uark.registerapp.commands.employees.helpers;

import java.util.Arrays;
import java.util.Optional;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeSignInCommand implements VoidCommandInterface {
	@Override
	public void execute() {
		this.validateProperties();
        

        Optional<EmployeeEntity> employeeEntity =
        this.employeeRepository.findByEmployeeId(Integer.valueOf(employeeSignIn.getEmployeeId()));
        byte[] pass = this.employeeSignIn.getPassword().getBytes();
        if (employeeEntity.isPresent()) {
            byte[] dataPass = employeeEntity.get().getPassword();
            if(!Arrays.equals(dataPass, pass))
                throw new UnauthorizedException();
		} else {
			throw new NotFoundException("Employee");
        }

        this.createActiveUserEntity(employeeEntity);
        
    }
    
    @Transactional
	private ActiveUserEntity createActiveUserEntity(Optional<EmployeeEntity> employeeEntity) {
	    System.out.println(employeeEntity.get());
        Optional<ActiveUserEntity> queriedActiveUserEntity =
        this.activeUserRepository
            .findByEmployeeId(employeeEntity.get().getId());

		if (queriedActiveUserEntity.isPresent()) {
            queriedActiveUserEntity.get().setSessionKey(this.getSessionKey());
            return this.activeUserRepository.save(queriedActiveUserEntity.get());
        } else {
            return this.activeUserRepository.save(new ActiveUserEntity()
                                                    .setClassification(employeeEntity.get().getClassification())
                                                    .setName(employeeEntity.get().getFirstName() + " " + employeeEntity.get().getLastName())
                                                    .setEmployeeId((employeeEntity.get().getId()))
                                                    .setSessionKey(this.getSessionKey())
                                                    );
        }
	}


    //Helper method
    private void validateProperties() {
		if (StringUtils.isBlank(this.employeeSignIn.getEmployeeId()) || !StringUtils.isNumeric(this.employeeSignIn.getEmployeeId())) {
			throw new UnprocessableEntityException("employeeID");
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
