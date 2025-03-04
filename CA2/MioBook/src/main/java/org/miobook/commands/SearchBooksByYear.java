package org.miobook.commands;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.miobook.repositories.Repositories;
import org.miobook.responses.*;

import java.util.List;

@Getter
@Setter
public class SearchBooksByYear implements BaseCommand<SearchedBooksRecord> {

    @NonNull
    private int from;
    @NonNull
    private int to;

    @Override
    public void validate() {
        if (from > to) {
            throw new IllegalArgumentException("not aaa");
        }
    }

    @Override
    public BaseResponse<SearchedBooksRecord> execute() {
        try {
            this.validate();
            SearchedBooksRecord data = Repositories.bookRepository.searchBooksByYear(this);
            SearchedBooksRecord responseData = new SearchedBooksRecord(from + " - " + to, data.books());
            return new BaseResponse<>(true, "Books published between " + from + " and " + to + ":", responseData);
        } catch (IllegalArgumentException exp) {
            return new BaseResponse<>(false, exp.getMessage(), null);
        }
    }
}
