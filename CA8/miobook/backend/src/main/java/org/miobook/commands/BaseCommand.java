package org.miobook.commands;

import org.miobook.responses.BaseResponse;
import org.miobook.services.Services;

public interface BaseCommand<T> {

    void validate();

    BaseResponse<T> execute(Services services);
}
