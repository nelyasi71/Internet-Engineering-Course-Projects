package org.miobook.commands;

import org.miobook.responses.BaseResponse;

public abstract class BaseCommand {

    public BaseCommand() {}

    public abstract void validate();

    public abstract BaseResponse execute();
}
