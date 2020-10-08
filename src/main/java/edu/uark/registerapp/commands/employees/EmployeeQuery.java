package edu.uark.registerapp.commands.employees;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class EmployeeQuery implements ResultCommandInterface<Employee> {
    @Autowired
    EmployeeRepository employeeRepository;

    UUID id;

    public Employee execute(){
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);

        if(!employeeEntity.isPresent()){
            throw new NotFoundException("Employee");
        }
        else{
            return new Employee(employeeEntity.get());
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
