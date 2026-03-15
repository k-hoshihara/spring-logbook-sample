package payroll.controller.response;

public record OrderResponse(
    Long id,
    String description,
    String status
) {
}
