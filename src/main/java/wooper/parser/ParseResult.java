package wooper.parser;

import wooper.enums.CommandType;

import java.util.ArrayList;

public class ParseResult {
    private CommandType command;
    private ArrayList<String> args;

    public ParseResult(CommandType inCommand) {
        command = inCommand;
    }

    public ParseResult(CommandType inCommand, ArrayList<String> inArgs) {
        command = inCommand;
        args = inArgs;
    }

    public CommandType getCommandType(){
        return command;
    }

    public ArrayList<String> getArgs(){
        return args;
    }
}
