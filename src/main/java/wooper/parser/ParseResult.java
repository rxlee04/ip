package wooper.parser;

import wooper.enums.CommandType;

import java.util.ArrayList;

/**
 * Represents the result of parsing a user command.
 * Stores the parsed command type and any associated arguments extracted
 * from the user input.
 */
public class ParseResult {
    private CommandType command;
    private ArrayList<String> args;

    /**
     * Creates a parse result with the specified command type.
     *
     * @param inCommand Parsed command type.
     */
    public ParseResult(CommandType inCommand) {
        command = inCommand;
    }

    /**
     * Creates a parse result with the specified command type and arguments.
     *
     * @param inCommand Parsed command type.
     * @param inArgs List of arguments associated with the command.
     */
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
