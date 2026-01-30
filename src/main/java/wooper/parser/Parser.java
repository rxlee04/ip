package wooper.parser;

import wooper.enums.CommandType;

import java.util.ArrayList;

public class Parser {

    public ParseResult getActionAndArguments(String str) {
        str = str.trim();
        ParseResult pr;
        ArrayList<String> args = new ArrayList<>();
        if (str.equalsIgnoreCase("list")) {
            pr = new ParseResult(CommandType.LIST);
        } else if (str.toLowerCase().startsWith("mark")) {
            String taskNoStr = str.substring(str.indexOf("mark") + 5).trim();
            args.add(taskNoStr);
            pr = new ParseResult(CommandType.MARK, args);
        } else if (str.toLowerCase().startsWith("unmark")) {
            String taskNoStr = str.substring(str.indexOf("unmark") + 7).trim();
            args.add(taskNoStr);
            pr = new ParseResult(CommandType.UNMARK, args);
        } else if (str.toLowerCase().startsWith("todo")) {
            String taskStr = str.substring(str.indexOf("todo") + 5).trim();
            args.add(taskStr);
            pr = new ParseResult(CommandType.TODO, args);
        } else if (str.toLowerCase().startsWith("deadline")) {
            String taskStr = str.substring(str.indexOf("deadline") + 9, str.indexOf("/by")).trim();
            String dl = str.substring(str.indexOf("/by") + 4).trim();
            args.add(taskStr);
            args.add(dl);
            pr = new ParseResult(CommandType.DEADLINE, args);
        } else if (str.toLowerCase().startsWith("event")) {
            String taskStr = str.substring(str.indexOf("event") + 6, str.indexOf("/from")).trim();
            String sdl = str.substring(str.indexOf("/from") + 6, str.indexOf("/to")).trim();
            String edl = str.substring(str.indexOf("/to") + 4).trim();

            args.add(taskStr);
            args.add(sdl);
            args.add(edl);
            pr = new ParseResult(CommandType.EVENT, args);
        } else if (str.toLowerCase().startsWith("delete")) {
            String taskNoStr = str.substring(str.indexOf("delete") + 7).trim();
            args.add(taskNoStr);
            pr = new ParseResult(CommandType.DELETE, args);
        }else if (str.toLowerCase().startsWith("find")) {
            String taskStr = str.substring(str.indexOf("find") + 5).trim();
            args.add(taskStr);
            pr = new ParseResult(CommandType.FIND, args);
        }
        else if (str.equalsIgnoreCase("bye")) {
            pr = new ParseResult(CommandType.BYE);
        } else {
            pr = new ParseResult(CommandType.UNKNOWN);
        }
        return pr;
    }
}
