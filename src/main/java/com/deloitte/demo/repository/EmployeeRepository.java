package com.deloitte.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.deloitte.demo.model.Employee;

public class EmployeeRepository {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EmployeePU");

    public Employee addEmployee(Employee employee) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
        return employee;
    }

    public List<Employee> getAllEmployees() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        entityManager.close();
        return employees;
    }
    
  
    public Employee getEmployeeById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.close();
        return employee;
    }
    
    public void deleteEmployee(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, id);
        
        if (employee != null) {
            entityManager.remove(employee);  // Remove the employee if it exists
        }
        
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = entityManager.find(Employee.class, id);

        if (employee != null) {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setSalary(updatedEmployee.getSalary());
        } else {
            entityManager.getTransaction().rollback();
            entityManager.close();
            return null; 
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        return employee;
    }

    // Implement other methods like updateEmployee, getEmployeeById, deleteEmployee...
}