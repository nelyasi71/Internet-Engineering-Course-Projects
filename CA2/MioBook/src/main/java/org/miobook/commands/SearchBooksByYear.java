package org.miobook.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.infrastructure.IntDeserializer;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.*;
import org.miobook.services.BookServices;

@Getter
@Setter
public class SearchBooksByYear implements BaseCommand<SearchedBooksRecord> {

    @NotNull
    @JsonDeserialize(using = IntDeserializer.class)
    private Integer from;

    @NotNull
    @JsonDeserialize(using = IntDeserializer.class)
    private Integer to;

    @Override
    public void validate() {
        JsonValidator.validate(this);
        System.out.println(to);
        if (from > to) {
            throw new IllegalArgumentException("Invalid range: 'from' must be less than or equal to 'to'.");
        }
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute() {
        try {
            this.validate();
            SearchedBooksRecord data = BookServices.searchBooksByYear(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(from + "-" + to, data.books());
            return new BaseResponse<>(true, "Books published between " + from + " and " + to + ":", responseData);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
