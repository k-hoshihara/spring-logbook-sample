package payroll.service;

import org.springframework.stereotype.Service;
import payroll.controller.response.OrderResponse;
import payroll.entity.CustomerOrder;
import payroll.enums.OrderStatus;
import payroll.exception.OrderNotFoundException;
import payroll.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public OrderResponse findById(Long id) {
        return toResponse(findEntityById(id));
    }

    public OrderResponse create(String description) {
        CustomerOrder order = repository.save(new CustomerOrder(description, OrderStatus.IN_PROGRESS));
        return toResponse(order);
    }

    public OrderResponse cancel(Long id) {
        CustomerOrder order = findEntityById(id);
        if (order.getStatus() != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("You can't cancel an order that is in the " + order.getStatus() + " status");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return toResponse(repository.save(order));
    }

    public OrderResponse complete(Long id) {
        CustomerOrder order = findEntityById(id);
        if (order.getStatus() != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("You can't complete an order that is in the " + order.getStatus() + " status");
        }
        order.setStatus(OrderStatus.COMPLETED);
        return toResponse(repository.save(order));
    }

    private CustomerOrder findEntityById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private OrderResponse toResponse(CustomerOrder order) {
        return new OrderResponse(
            order.getId(),
            order.getDescription(),
            order.getStatus().name()
        );
    }
}
