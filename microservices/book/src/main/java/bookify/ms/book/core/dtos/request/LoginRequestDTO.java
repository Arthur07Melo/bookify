package bookify.ms.book.core.dtos.request;

public record LoginRequestDTO(
    String email,
    String password
) {
}
