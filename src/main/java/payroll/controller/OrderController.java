package payroll.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payroll.controller.request.OrderRequest;
import payroll.controller.response.OrderResponse;
import payroll.service.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderResponse> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> newOrder(@RequestBody OrderRequest request) {
        OrderResponse created = service.create(request.description());
        return ResponseEntity.created(URI.create("/orders/" + created.id())).body(created);
    }

    @DeleteMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @PutMapping("/{id}/complete")
    public OrderResponse complete(@PathVariable Long id) {
        return service.complete(id);
    }
}
