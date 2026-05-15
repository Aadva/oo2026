package ee.kontrolltoo.backend.dto;

public record TodoItem(
        Long userId,
        Long id,
        String title,
        Boolean completed
) {
}
