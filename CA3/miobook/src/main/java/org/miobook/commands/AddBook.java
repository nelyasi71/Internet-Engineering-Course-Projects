package org.miobook.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.miobook.responses.BaseResponse;
import org.miobook.infrastructure.IntDeserializer;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.services.BookServices;
import org.miobook.services.Services;

import java.util.List;

@Getter
@Setter
public class AddBook implements BaseCommand<Void> {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Username can only contain letters, numbers, dash and underscores")
    private String username;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String publisher;

    @NotNull
    private String synopsis;

    @NotNull
    private String content;

    @NotNull
    @NotEmpty
    private List<String> genres;

    @NotNull
    @JsonDeserialize(using = IntDeserializer.class)
    @Range(min = 1)
    private Integer year;

    @NotNull
    private int price;


    public AddBook() {
        super();
    }

    @Override
    public void validate() {
        JsonValidator.validate(this);
    }

    @Override
    public BaseResponse<Void> execute(Services services) {
        try {
            this.validate();
            ((BookServices)services).addBook(this);
            return new BaseResponse<>(true, "Book added successfully.", null);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}