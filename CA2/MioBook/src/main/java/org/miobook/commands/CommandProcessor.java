package org.miobook.commands;

import org.apache.commons.text.WordUtils;
import org.json.JSONObject;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class CommandProcessor {
    public void processCommand(String userInput) {
        try {
            int firstSpaceIndex = userInput.indexOf(" ");

            String commandName = userInput.substring(0, firstSpaceIndex);
            String jsonString = userInput.substring(firstSpaceIndex + 1);
            className = WordUtils.capitalizeFully(commandName, '_').replace("_", "");

            JSONObject inputjson = new JSONObject(jsonInput);

            Class<?> commandClass = Class.forName(commandClassName);
            Constructor<?> constructor = commandClass.getConstructor(jsonInput.class);
            BaseCommand command = (BaseCommand) constructor.newInstance(inputjson);

            if (command.validate()) {
                command.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}