package payroll.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import payroll.entity.CustomerOrder;
import payroll.entity.Employee;
import payroll.enums.OrderStatus;
import payroll.repository.EmployeeRepository;
import payroll.repository.OrderRepository;

/**
 * アプリケーション起動時にH2データベースへサンプルデータを投入する設定クラス。
 * 開発・動作確認用。stg, prod プロファイルでは無効化される。
 */
@Configuration
@Profile("!stg & !prod")
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return _ -> {
            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));
            employeeRepository.findAll().forEach(employee -> log.info("Preloaded employee: {}", employee));

            orderRepository.save(new CustomerOrder("MacBook Pro", OrderStatus.COMPLETED));
            orderRepository.save(new CustomerOrder("iPhone", OrderStatus.IN_PROGRESS));
            orderRepository.findAll().forEach(order -> log.info("Preloaded order: {}", order));
        };
    }
}
