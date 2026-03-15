package payroll.controller.response;

public record EmployeeResponse(
    Long id,
    String firstName,
    String lastName,
    String role
) {
}
