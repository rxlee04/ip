package wooper.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import wooper.enums.CommandType;

public class ParserTest {
    private final Parser parser = new Parser();

    /**
     * getActionAndArguments
     */
    @Test
    public void getActionAndArguments_listCommand_listReturned() {
        ParseResult actual = parser.getActionAndArguments("list");
        assertEquals(CommandType.LIST, actual.getCommandType());
    }

    @Test
    public void getActionAndArguments_markCommandWithIndex_markReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("1"));
        ParseResult actual = parser.getActionAndArguments("mark 1");
        assertEquals(CommandType.MARK, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_unmarkCommandWithIndex_unmarkReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("1"));
        ParseResult actual = parser.getActionAndArguments("unmark 1");
        assertEquals(CommandType.UNMARK, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_todoWithTaskName_todoReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("taskName"));
        ParseResult actual = parser.getActionAndArguments("todo taskName");
        assertEquals(CommandType.TODO, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_deadlineWithBy_deadlineReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("return book", "06/06/2026"));
        ParseResult actual = parser.getActionAndArguments("deadline return book /by 06/06/2026");
        assertEquals(CommandType.DEADLINE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_eventWithFromTo_eventReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(
                List.of("project meeting", "06/08/2026 14:00", "06/08/2026 16:00"));
        ParseResult actual = parser
                .getActionAndArguments("event project meeting /from 06/08/2026 14:00 /to 06/08/2026 16:00");
        assertEquals(CommandType.EVENT, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_deleteWithIndex_deleteReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("1"));
        ParseResult actual = parser.getActionAndArguments("delete 1");
        assertEquals(CommandType.DELETE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_byeCommand_byeReturned() {
        ParseResult actual = parser.getActionAndArguments("bye");
        assertEquals(CommandType.BYE, actual.getCommandType());
    }

    @Test
    public void getActionAndArguments_unknownCommand_unknownReturned() {
        ParseResult actual = parser.getActionAndArguments("anything");
        assertEquals(CommandType.UNKNOWN, actual.getCommandType());
    }

    /**
     * getActionAndArguments edge cases
     */
    @Test
    public void getActionAndArguments_nullInput_unknownReturned() {
        ParseResult actual = parser.getActionAndArguments(null);
        assertEquals(CommandType.UNKNOWN, actual.getCommandType());
        assertEquals(new ArrayList<>(), actual.getArgs());
    }

    @Test
    public void getActionAndArguments_blankInput_unknownReturned() {
        ParseResult actual = parser.getActionAndArguments("   ");
        assertEquals(CommandType.UNKNOWN, actual.getCommandType());
        assertEquals(new ArrayList<>(), actual.getArgs());
    }

    @Test
    public void getActionAndArguments_commandWithLeadingTrailingSpaces_parsedCorrectly() {
        ParseResult actual = parser.getActionAndArguments("   list   ");
        assertEquals(CommandType.LIST, actual.getCommandType());
    }

    @Test
    public void getActionAndArguments_markWithoutArg_markReturnedWithEmptyArg() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of(""));
        ParseResult actual = parser.getActionAndArguments("mark");
        assertEquals(CommandType.MARK, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_deleteWithoutArg_deleteReturnedWithEmptyArg() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of(""));
        ParseResult actual = parser.getActionAndArguments("delete");
        assertEquals(CommandType.DELETE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_findCommandWithKeyword_findReturned() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("book"));
        ParseResult actual = parser.getActionAndArguments("find book");
        assertEquals(CommandType.FIND, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    /**
     * deadline parsing edge cases
     */
    @Test
    public void getActionAndArguments_deadlineWithoutBy_deadlineReturnedWithEmptyBy() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("return book", ""));
        ParseResult actual = parser.getActionAndArguments("deadline return book");
        assertEquals(CommandType.DEADLINE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_deadlineByButNoDate_deadlineReturnedWithEmptyByValue() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("return book", ""));
        ParseResult actual = parser.getActionAndArguments("deadline return book /by");
        assertEquals(CommandType.DEADLINE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    /**
     * event parsing edge cases
     */
    @Test
    public void getActionAndArguments_eventMissingFrom_eventReturnedWithEmptyFromTo() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("meeting /to 06/08/2026 16:00", "", ""));
        ParseResult actual = parser.getActionAndArguments("event meeting /to 06/08/2026 16:00");
        assertEquals(CommandType.EVENT, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_eventMissingTo_eventReturnedWithEmptyFromTo() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("meeting /from 06/08/2026 14:00", "", ""));
        ParseResult actual = parser.getActionAndArguments("event meeting /from 06/08/2026 14:00");
        assertEquals(CommandType.EVENT, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_eventWrongOrderToBeforeFrom_eventReturnedWithEmptyFromTo() {
        ArrayList<String> expectedArgs = new ArrayList<>(
                List.of("meeting /to 06/08/2026 16:00 /from 06/08/2026 14:00", "", ""));
        ParseResult actual = parser
                .getActionAndArguments("event meeting /to 06/08/2026 16:00 /from 06/08/2026 14:00");
        assertEquals(CommandType.EVENT, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    /**
     * update parsing
     *
     * Parser returns args in this order:
     * [0]=taskNo, [1]=taskType, [2]=taskName, [3]=by, [4]=from, [5]=to
     */
    @Test
    public void getActionAndArguments_updateAllFieldsProvided_updateArgsParsed() {
        ArrayList<String> expectedArgs = new ArrayList<>(
                List.of("1", "todo", "newName", "", "", ""));
        ParseResult actual = parser.getActionAndArguments("update 1 /taskType todo /taskName newName");
        assertEquals(CommandType.UPDATE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_updateDeadlineByProvided_updateArgsParsed() {
        ArrayList<String> expectedArgs = new ArrayList<>(
                List.of("2", "deadline", "", "06/06/2026", "", ""));
        ParseResult actual = parser.getActionAndArguments("update 2 /taskType deadline /by 06/06/2026");
        assertEquals(CommandType.UPDATE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_updateEventFromToProvided_updateArgsParsed() {
        ArrayList<String> expectedArgs = new ArrayList<>(
                List.of("3", "event", "", "", "02/02/2026 14:00", "02/02/2026 16:00"));
        ParseResult actual = parser.getActionAndArguments(
                "update 3 /taskType event /from 02/02/2026 14:00 /to 02/02/2026 16:00");
        assertEquals(CommandType.UPDATE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_updateNoArgsProvided_updateReturnedWithAllEmpty() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("", "", "", "", "", ""));
        ParseResult actual = parser.getActionAndArguments("update");
        assertEquals(CommandType.UPDATE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_updateOnlyTaskNoProvided_updateReturnedWithEmptyFields() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("1", "", "", "", "", ""));
        ParseResult actual = parser.getActionAndArguments("update 1");
        assertEquals(CommandType.UPDATE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }

    @Test
    public void getActionAndArguments_updateFlagsMissingValues_updateReturnedWithEmptyValues() {
        ArrayList<String> expectedArgs = new ArrayList<>(List.of("1", "", "", "", "", ""));
        ParseResult actual = parser.getActionAndArguments("update 1 /taskType /taskName /by /from /to");
        assertEquals(CommandType.UPDATE, actual.getCommandType());
        assertEquals(expectedArgs, actual.getArgs());
    }
}
