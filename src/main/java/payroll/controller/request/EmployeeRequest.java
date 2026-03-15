package payroll.controller.request;

public record EmployeeRequest(
    String firstName,
    String lastName,
    String role
) {
}
