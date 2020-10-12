package edu.uark.registerapp.commands.employees;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeUpdateCommand implements ResultCommandInterface<Employee> {

    public Employee execute(){
        this.validate();

        final Optional<EmployeeEntity> employeeEntity =
        this.employeeRepository.findById(this.employeeId);

        if (!employeeEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Employee");
		}
        
        this.employee = employeeEntity.get().synchronize(this.employee);

		// Write, via an UPDATE, any changes to the database.
		this.employeeRepository.save(employeeEntity.get());

		return this.employee;
    }


    private void validate() {
        if (StringUtils.isBlank(this.employee.getFirstName())) {
            throw new UnprocessableEntityException("firstname");
        }
        if (StringUtils.isBlank(this.employee.getLastName())) {
            throw new UnprocessableEntityException("lastname");
        }
    }

    @Transactional
    public Employee updateEmployee(){
        Optional<EmployeeEntity> employeeStored = employeeRepository.findById(this.employeeId);
        if(!employeeStored.isPresent()){
            throw new NotFoundException("Employee");
        }

        EmployeeEntity employeeUpdated = employeeRepository.save(new EmployeeEntity(employee));

        return new Employee(employeeUpdated);

    }

    // Properties
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public EmployeeUpdateCommand setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	private Employee employee;
	public Employee getEmployee() {
		return this.employee;
	}
	public EmployeeUpdateCommand setEmployee(final Employee employee) {
		this.employee = employee;
		return this;
	}

    @Autowired
    EmployeeRepository employeeRepository;
}
