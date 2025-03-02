package org.miobook.commands;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class BaseCommand {

    public BaseCommand() {}

    public abstract boolean validate();

    public abstract void execute();
}
