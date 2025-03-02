package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowBookContent extends BaseCommand {

    @NotNull
    private String username;
    @NotNull
    private String title;
    @Override
    public void validate() {
    }
    @Override
    public void execute() {

    }
}
