package bookify.ms.book.core.dtos.request;

public record RegisterRequestDTO(
    String name,
    String email,
    String password
) {
}
