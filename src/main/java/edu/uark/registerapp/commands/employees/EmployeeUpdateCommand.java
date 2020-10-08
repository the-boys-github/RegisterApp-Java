package edu.uark.registerapp.commands.employees;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeUpdateCommand implements ResultCommandInterface<Employee> {
    UUID employeeId;
    Employee employee;

    @Autowired
    EmployeeRepository employeeRepository;


    public Employee execute(){
        this.validate();

        return this.updateEmployee();
    }


    private void validate() {
        boolean isLastNameNotBlank = employee.getLastName().length() > 0;
        boolean isFirstNameNotBlank = employee.getFirstName().length() > 0;

        if (!isFirstNameNotBlank && !isLastNameNotBlank) {
            throw new UnprocessableEntityException("Blank Parameter");
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
}
