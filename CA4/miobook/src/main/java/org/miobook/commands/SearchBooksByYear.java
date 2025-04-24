package org.miobook.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.miobook.Exception.MioBookException;
import org.miobook.infrastructure.IntDeserializer;
import org.miobook.infrastructure.JsonValidator;
import org.miobook.responses.*;
import org.miobook.services.BookServices;
import org.miobook.services.Services;

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
        if (from > to) {
            throw new MioBookException("to", "Invalid range: 'from' must be less than or equal to 'to'.");
        }
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute(Services services) {
        try {
            this.validate();
            SearchedBooksRecord data = ((BookServices) services).searchBooksByYear(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(from + "-" + to, data.books());
            return new BaseResponse<>(true, "Books published between " + from + " and " + to + ":", responseData);
        } catch (MioBookException exp) {
            return BaseResponse.fromMioBookException(exp);
        }
    }
}
