package wooper.parser;

import org.junit.jupiter.api.Test;
import wooper.enums.CommandType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    private Parser parser = new Parser();

    @Test
    public void getActionAndArguments_validListInput_returnsListCommand() {
        ParseResult expected = new ParseResult(CommandType.LIST);
        ParseResult actual = parser.getActionAndArguments("list");
        assertEquals(expected.getCommandType(), actual.getCommandType());
    }

    @Test
    public void getActionAndArguments_validMarkInput_returnsMarkCommand() {
        ArrayList<String> args = new ArrayList<>(List.of("1"));
        ParseResult expected = new ParseResult(CommandType.MARK, args);
        ParseResult actual = parser.getActionAndArguments("mark 1");
        assertEquals(expected.getCommandType(), actual.getCommandType());
        assertEquals(args, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_validUnmarkInput_returnsUnmarkCommand() {
        ArrayList<String> args = new ArrayList<>(List.of("1"));
        ParseResult expected = new ParseResult(CommandType.UNMARK, args);
        ParseResult actual = parser.getActionAndArguments("unmark 1");
        assertEquals(expected.getCommandType(), actual.getCommandType());
        assertEquals(args, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_validTodoInput_returnsTodoCommand() {
        ArrayList<String> args = new ArrayList<>(List.of("taskName"));
        ParseResult expected = new ParseResult(CommandType.TODO, args);
        ParseResult actual = parser.getActionAndArguments("todo taskName");
        assertEquals(expected.getCommandType(), actual.getCommandType());
        assertEquals(args, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_validDeadlineInput_returnsDeadlineCommand() {
        ArrayList<String> args = new ArrayList<>(List.of("return book", "06/06/2026"));
        ParseResult expected = new ParseResult(CommandType.DEADLINE, args);
        ParseResult actual = parser.getActionAndArguments("deadline return book /by 06/06/2026");
        assertEquals(expected.getCommandType(), actual.getCommandType());
        assertEquals(args, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_validEventInput_returnsEventCommand() {
        ArrayList<String> args = new ArrayList<>(List.of("project meeting", "06/08/2026 14:00", "06/08/2026 16:00"));
        ParseResult expected = new ParseResult(CommandType.EVENT, args);
        ParseResult actual = parser.getActionAndArguments("event project meeting /from 06/08/2026 14:00 /to 06/08/2026 16:00");
        assertEquals(expected.getCommandType(), actual.getCommandType());
        assertEquals(args, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_validDeleteInput_returnsDeleteCommand() {
        ArrayList<String> args = new ArrayList<>(List.of("1"));
        ParseResult expected = new ParseResult(CommandType.DELETE, args);
        ParseResult actual = parser.getActionAndArguments("delete 1");
        assertEquals(expected.getCommandType(), actual.getCommandType());
        assertEquals(args, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_validByeInput_returnsByeCommand() {
        ParseResult expected = new ParseResult(CommandType.BYE);
        ParseResult actual = parser.getActionAndArguments("bye");
        assertEquals(expected.getCommandType(), actual.getCommandType());
    }

    @Test
    public void getActionAndArguments_invalidInput_returnsUnknownCommand() {
        ParseResult expected = new ParseResult(CommandType.UNKNOWN);
        ParseResult actual = parser.getActionAndArguments("anything");
        assertEquals(expected.getCommandType(), actual.getCommandType());
    }
}
