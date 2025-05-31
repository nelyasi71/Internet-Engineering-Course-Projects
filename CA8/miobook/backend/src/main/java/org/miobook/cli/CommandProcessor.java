package org.miobook.cli;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.text.WordUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.miobook.commands.*;
import org.miobook.responses.BaseResponse;

public class CommandProcessor {

    public void processCommand(String userInput) {
        try {
            int firstSpaceIndex = userInput.indexOf(" ");

            String commandName = userInput.substring(0, firstSpaceIndex);
            String jsonString = userInput.substring(firstSpaceIndex + 1);
            String className = WordUtils.capitalizeFully(commandName, '_').replace("_", "");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            Class<?> commandClass = Class.forName("org.miobook.commands." + className);
            BaseCommand command = (BaseCommand) objectMapper.readValue(jsonString, commandClass);
//            BaseResponse response = command.execute();

//            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}