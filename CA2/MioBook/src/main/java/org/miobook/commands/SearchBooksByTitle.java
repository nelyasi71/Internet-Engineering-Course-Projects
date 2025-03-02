

package org.miobook.commands;

import lombok.Getter;
import lombok.Setter;
import org.miobook.responses.BaseResponse;

@Getter
@Setter
public class SearchBooksByTitle extends BaseCommand {

    private String title;
    private String name;
    private String genre;
    private int from;
    private int to;
    @Override
    public void validate() {
    }
    @Override
    public BaseResponse execute() {
        return null;

    }
}
