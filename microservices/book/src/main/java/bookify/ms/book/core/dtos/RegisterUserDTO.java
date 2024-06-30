package bookify.ms.book.core.dtos;

public record RegisterUserDTO(
    String name,
    String email,
    String password
) {
}
