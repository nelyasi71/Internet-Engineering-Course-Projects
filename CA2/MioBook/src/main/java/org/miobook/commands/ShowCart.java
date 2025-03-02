package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowCart extends BaseCommand {

    @NotNull
    private String username;

    @Override
    public boolean validate() {
        return false;
    }
    @Override
    public void execute() {

    }
}
