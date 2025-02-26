import org.json.JSONObject;

public abstract class BaseCommand {
    protected JSONObject inputJson;

    public BaseCommand(JSONObject inputJson) {
        this.inputJson = inputJson;
    }

    public abstract boolean validate();

    public abstract void execute();
}
