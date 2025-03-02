package org.miobook.commands;

public abstract class BaseCommand {

    public BaseCommand() {}

    public abstract void validate();

    public abstract void execute();
}
