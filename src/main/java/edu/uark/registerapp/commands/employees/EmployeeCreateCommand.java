package edu.uark.registerapp.commands.employees;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
    Employee employee;
    boolean isInitialEmployee;

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee execute(){
        this.validate();

        if(isInitialEmployee){
            employee.setClassification(3);
        }

        EmployeeEntity employeeCreated = employeeRepository.save(new EmployeeEntity(employee));

        return new Employee(employeeCreated);
    }

    private void validate(){
        boolean isLastNameNotBlank = employee.getLastName().length() > 0;
        boolean isFirstNameNotBlank = employee.getFirstName().length() > 0;
        boolean isPasswordNotBlank = employee.getPassword().length() > 0;

        if(!isFirstNameNotBlank && !isLastNameNotBlank && !isPasswordNotBlank){
            throw new UnprocessableEntityException("Blank Parameter");
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isInitialEmployee() {
        return isInitialEmployee;
    }

    public void setInitialEmployee(boolean initialEmployee) {
        isInitialEmployee = initialEmployee;
    }
}
