package wooper.parser;

import java.util.ArrayList;

import wooper.enums.CommandType;

/**
 * Parses user input strings into commands and their corresponding arguments.
 */
public class Parser {

    private static final String BY = "/by";
    private static final String FROM = "/from";
    private static final String TO = "/to";

    /**
     * Returns the command type and arguments parsed from the given user input.
     * Identifies the command keyword and extracts any required arguments based on
     * the expected command format. Returns an unknown command type if the input
     * does not match any supported command.
     *
     * @param str User input string to be parsed.
     * @return A {@link ParseResult} containing the parsed command type and arguments.
     */
    public ParseResult getActionAndArguments(String str) {
        if (str == null) {
            return new ParseResult(CommandType.UNKNOWN, new ArrayList<>());
        }

        str = str.trim();
        ArrayList<String> args = new ArrayList<>();

        if (str.isEmpty()) {
            return new ParseResult(CommandType.UNKNOWN, args);
        }

        String[] parts = str.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String rest = (parts.length > 1) ? parts[1].trim() : "";

        switch (cmd) {
        case "list":
            return new ParseResult(CommandType.LIST, args);
        case "mark":
            args.add(rest);
            return new ParseResult(CommandType.MARK, args);
        case "unmark":
            args.add(rest);
            return new ParseResult(CommandType.UNMARK, args);
        case "todo":
            args.add(rest);
            return new ParseResult(CommandType.TODO, args);
        case "deadline":
            parseDeadlineArgs(rest, args);
            return new ParseResult(CommandType.DEADLINE, args);
        case "event":
            parseEventArgs(rest, args);
            return new ParseResult(CommandType.EVENT, args);
        case "delete":
            args.add(rest);
            return new ParseResult(CommandType.DELETE, args);
        case "find":
            args.add(rest);
            return new ParseResult(CommandType.FIND, args);
        case "bye":
            return new ParseResult(CommandType.BYE, args);
        default:
            return new ParseResult(CommandType.UNKNOWN, args);
        }
    }

    private void parseDeadlineArgs(String rest, ArrayList<String> args) {
        int byIdx = rest.toLowerCase().indexOf(BY);

        if (byIdx == -1) {
            args.add(rest.trim());
            args.add("");
            return;
        }

        String desc = rest.substring(0, byIdx).trim();
        String by = rest.substring(byIdx + BY.length()).trim(); // 3 = length of "/by"
        args.add(desc);
        args.add(by);
    }

    private void parseEventArgs(String rest, ArrayList<String> args) {
        int fromIdx = rest.toLowerCase().indexOf(FROM);
        int toIdx = rest.toLowerCase().indexOf(TO);

        // Missing markers or wrong order, fill blanks so later code throws a clear error
        if (fromIdx == -1 || toIdx == -1 || toIdx < fromIdx + FROM.length()) {
            args.add(rest.trim()); // description best-effort
            args.add("");
            args.add("");
            return;
        }

        String desc = rest.substring(0, fromIdx).trim();
        String from = rest.substring(fromIdx + FROM.length(), toIdx).trim(); // 5 = length of "/from"
        String to = rest.substring(toIdx + TO.length()).trim(); // 3 = length of "/to"

        args.add(desc);
        args.add(from);
        args.add(to);
    }
}
