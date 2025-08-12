package io.github.renatoconrado.steel_frame.shared;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification {

    public static <Entity> Specification<Entity> likeLower(
        String fieldName,
        String value
    ) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }

            return builder.like(
                builder.lower(root.get(fieldName)),
                "%" + value.toLowerCase() + "%"
            );
        };
    }

}
