package bookify.ms.book.core.dtos.request;

public record RegisterBookDTO(
    String title,
    float price,
    String gender,
    String author
) {
    
}
