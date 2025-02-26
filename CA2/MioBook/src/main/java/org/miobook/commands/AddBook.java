
public class AddBook extends BaseCommand {
    public AddBook(JSONObject inputJson) {
        super(inputJson);
    }

    @Override
    public boolean validate() {
        return ValidationService.validateAddBook(inputJson);
    }

    @Override
    public void execute(){
        if (!validate()) {
            return;
        }
        //logic of add book here
    };
}