package bookify.ms.book.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private int id;
    private String roleName;
}
