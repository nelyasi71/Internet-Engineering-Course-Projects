

package org.miobook.commands;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBooksByTitle extends BaseCommand {

    private String title;
    private String name;
    private String genre;
    private int from;
    private int to;
    @Override
    public boolean validate() {
        return false;
    }
    @Override
    public void execute() {

    }
}
