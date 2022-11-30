package com.hrd.HumanResourcesDepartment.repository;

import com.hrd.HumanResourcesDepartment.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);
}
