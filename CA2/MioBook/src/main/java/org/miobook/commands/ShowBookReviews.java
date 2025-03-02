package org.miobook.commands;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowBookReviews extends BaseCommand {

    @NotNull
    private String title;
    @Override
    public void validate() {
    }
    @Override
    public void execute() {

    }
}
