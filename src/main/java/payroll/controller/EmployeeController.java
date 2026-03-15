package payroll.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.controller.request.EmployeeRequest;
import payroll.controller.response.EmployeeResponse;
import payroll.service.EmployeeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeResponse> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponse one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> newEmployee(@RequestBody EmployeeRequest request) {
        EmployeeResponse created = service.save(request.firstName(), request.lastName(), request.role());
        return ResponseEntity.created(URI.create("/employees/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public EmployeeResponse replaceEmployee(@RequestBody EmployeeRequest request, @PathVariable Long id) {
        String name = request.firstName() + " " + request.lastName();
        return service.update(id, name, request.role());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
