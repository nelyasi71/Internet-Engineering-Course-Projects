package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowCart extends BaseCommand {

    @NotNull
    private String username;

    @Override
    public void validate() {
    }
    @Override
    public void execute() {

    }
}
