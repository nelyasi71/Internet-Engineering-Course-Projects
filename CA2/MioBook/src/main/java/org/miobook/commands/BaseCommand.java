package org.miobook.commands;

import org.miobook.responses.BaseResponse;

public interface BaseCommand<T> {

    void validate();

    BaseResponse<T> execute();
}
