package payroll.service;

import org.springframework.stereotype.Service;
import payroll.controller.response.EmployeeResponse;
import payroll.entity.Employee;
import payroll.exception.EmployeeNotFoundException;
import payroll.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeResponse> findAll() {
        return repository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public EmployeeResponse findById(Long id) {
        return toResponse(findEntityById(id));
    }

    public EmployeeResponse save(String firstName, String lastName, String role) {
        Employee employee = repository.save(new Employee(firstName, lastName, role));
        return toResponse(employee);
    }

    public EmployeeResponse update(Long id, String firstName, String lastName, String role) {
        Employee employee = repository.findById(id)
            .map(e -> {
                e.setFirstName(firstName);
                e.setLastName(lastName);
                e.setRole(role);
                return repository.save(e);
            })
            .orElseGet(() -> repository.save(new Employee(firstName, lastName, role)));
        return toResponse(employee);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private Employee findEntityById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
            employee.getId(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getRole()
        );
    }
}
