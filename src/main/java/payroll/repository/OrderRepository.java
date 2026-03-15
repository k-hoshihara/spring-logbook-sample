package payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payroll.entity.CustomerOrder;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
