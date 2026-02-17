package wooper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.Temporal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wooper.exception.WooperException;
import wooper.task.Task;
import wooper.task.ToDo;

public class TaskManagerTest {
    private TaskManager tm;

    @BeforeEach
    public void setUp() {
        tm = new TaskManager();
    }

    /**
     * getAllTasks / getTaskListSize
     */
    @Test
    public void getTaskListSize_emptyTaskList_zeroReturned() {
        assertEquals(0, tm.getTaskListSize());
    }

    @Test
    public void getAllTasks_afterAddingTask_listWithOneTaskReturned() throws WooperException {
        tm.addToDoTask("read");
        assertEquals(1, tm.getAllTasks().size());
    }

    /**
     * markTaskDone
     */
    @Test
    public void markTaskDone_validIndex_taskMarkedDone() throws WooperException {
        tm.addToDoTask("read book");
        Task t = tm.markTaskDone(0);
        assertTrue(t.isDone());
    }

    @Test
    public void markTaskDone_invalidIndex_exceptionThrown() throws WooperException {
        tm.addToDoTask("read book");
        assertThrows(WooperException.class, () -> tm.markTaskDone(99));
        assertThrows(WooperException.class, () -> tm.markTaskDone(-1));
    }

    /**
     * unmarkTaskDone
     */
    @Test
    public void unmarkTaskDone_validIndex_taskMarkedUndone() throws WooperException {
        tm.addToDoTask("read book");
        Task t = tm.markTaskDone(0);
        tm.unmarkTaskDone(0);
        assertFalse(t.isDone());
    }

    @Test
    public void unmarkTaskDone_invalidIndex_exceptionThrown() throws WooperException {
        tm.addToDoTask("read book");
        tm.markTaskDone(0);
        assertThrows(WooperException.class, () -> tm.unmarkTaskDone(99));
        assertThrows(WooperException.class, () -> tm.unmarkTaskDone(-1));
    }

    /**
     * addToDoTask
     */
    @Test
    public void addToDoTask_emptyDescription_exceptionThrown() {
        assertThrows(WooperException.class, () -> tm.addToDoTask(""));
    }

    @Test
    public void addToDoTask_blankDescription_exceptionThrown() {
        assertThrows(WooperException.class, () -> tm.addToDoTask("   "));
    }

    @Test
    public void addToDoTask_validDescription_taskAdded() throws WooperException {
        tm.addToDoTask("read book");
        assertEquals(1, tm.getTaskListSize());
        assertEquals("read book", tm.getAllTasks().get(0).getTaskName());
    }

    /**
     * addDeadlineTask
     */
    @Test
    public void addDeadlineTask_localDate_taskAdded() throws WooperException {
        tm.addDeadlineTask("return book", LocalDate.of(2026, 6, 6));
        assertEquals(1, tm.getTaskListSize());
    }

    @Test
    public void addDeadlineTask_localDateTime_taskAdded() throws WooperException {
        tm.addDeadlineTask("submit",
                LocalDateTime.of(2026, 6, 6, 14, 0));
        assertEquals(1, tm.getTaskListSize());
    }

    @Test
    public void addDeadlineTask_blankDescription_exceptionThrown() {
        assertThrows(WooperException.class, () -> tm.addDeadlineTask("   ",
                LocalDate.of(2026, 6, 6)));
    }

    @Test
    public void addDeadlineTask_invalidDate_exceptionThrown() {
        Temporal weird = Year.of(2026);
        assertThrows(WooperException.class, () -> tm.addDeadlineTask("x", weird));
    }

    /**
     * addEventTask
     */
    @Test
    public void addEventTask_localDate_taskAdded() throws WooperException {
        tm.addEventTask("camp",
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3));
        assertEquals(1, tm.getTaskListSize());
    }

    @Test
    public void addEventTask_localDateTime_taskAdded() throws WooperException {
        tm.addEventTask("meeting",
                LocalDateTime.of(2026, 2, 2, 14, 0),
                LocalDateTime.of(2026, 2, 2, 16, 0));
        assertEquals(1, tm.getTaskListSize());
    }

    @Test
    public void addEventTask_mixedTemporalTypes_exceptionThrown() {
        assertThrows(WooperException.class, () -> tm.addEventTask("meeting",
                LocalDate.of(2026, 2, 2),
                LocalDateTime.of(2026, 2, 2, 16, 0)));
    }

    @Test
    public void addEventTask_blankDescription_exceptionThrown() {
        assertThrows(WooperException.class, () -> tm.addEventTask("   ",
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3)));
    }

    /**
     * deleteTask
     */
    @Test
    public void deleteTask_validIndex_taskRemoved() throws WooperException {
        tm.addToDoTask("read book");
        tm.deleteTask(0);
        assertEquals(0, tm.getTaskListSize());
    }

    @Test
    public void deleteTask_invalidIndex_exceptionThrown() throws WooperException {
        tm.addToDoTask("read book");
        assertThrows(WooperException.class, () -> tm.deleteTask(99));
        assertThrows(WooperException.class, () -> tm.deleteTask(-1));
    }

    /**
     * loadTaskList
     */
    @Test
    public void loadTaskList_validList_tasksLoaded() {
        ArrayList<Task> list = new ArrayList<>();
        list.add(new ToDo("a"));
        list.add(new ToDo("b"));

        tm.loadTaskList(list);
        assertEquals(2, tm.getTaskListSize());
    }

    /**
     * findTasks
     */
    @Test
    public void findTasks_caseInsensitiveKeyword_matchingTasksReturned() throws WooperException {
        tm.addToDoTask("Read Book");
        assertEquals(1, tm.findTasks("read").size());
        assertEquals(1, tm.findTasks("BOOK").size());
    }

    @Test
    public void findTasks_blankKeyword_exceptionThrown() throws WooperException {
        tm.addToDoTask("read book");
        assertThrows(WooperException.class, () -> tm.findTasks("   "));
    }

    @Test
    public void findTasks_noMatch_emptyListReturned() throws WooperException {
        tm.addToDoTask("read book");
        assertEquals(0, tm.findTasks("gym").size());
    }

    /**
     * updateTask
     */
    @Test
    public void updateTask_invalidIndex_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> args = buildUpdateArgs("todo", "new", "", "", "");
        assertThrows(WooperException.class, () -> tm.updateTask(99, args));
        assertThrows(WooperException.class, () -> tm.updateTask(-1, args));
    }

    @Test
    public void updateTask_nullArgs_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        assertThrows(WooperException.class, () -> tm.updateTask(0, null));
    }

    @Test
    public void updateTask_argsTooShort_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> bad = new ArrayList<>();
        bad.add("update"); // size < 6
        assertThrows(WooperException.class, () -> tm.updateTask(0, bad));
    }

    @Test
    public void updateTask_emptyTaskType_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> args = buildUpdateArgs("   ", "new", "", "", "");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_wrongTaskType_exceptionThrown() throws WooperException {
        tm.addToDoTask("old"); // actual type is todo
        ArrayList<String> args = buildUpdateArgs("deadline", "new", "", "", "");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_noFieldsProvided_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> args = buildUpdateArgs("todo", "   ", "   ", "   ", "   ");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_todoTaskNameProvided_taskNameUpdated() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> args = buildUpdateArgs("todo", "new name", "", "", "");

        Task updated = tm.updateTask(0, args);
        assertEquals("new name", updated.getTaskName());
    }

    @Test
    public void updateTask_todoByProvided_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> args = buildUpdateArgs("todo", "", "06/06/2026", "", "");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_todoFromToProvided_exceptionThrown() throws WooperException {
        tm.addToDoTask("old");
        ArrayList<String> args = buildUpdateArgs("todo", "", "", "02/02/2026", "03/02/2026");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_deadlineByProvided_deadlineUpdated() throws WooperException {
        tm.addDeadlineTask("dl", LocalDate.of(2026, 6, 6));

        tm.updateTask(0, buildUpdateArgs("deadline", "", "07/06/2026", "", ""));

        wooper.task.Deadline d = (wooper.task.Deadline) tm.getAllTasks().get(0);
        assertEquals("2026-06-07", d.getDeadlineDueBy());
    }

    @Test
    public void updateTask_deadlineFromToProvided_exceptionThrown() throws WooperException {
        tm.addDeadlineTask("dl", LocalDate.of(2026, 6, 6));
        ArrayList<String> args = buildUpdateArgs("deadline", "", "", "02/02/2026", "03/02/2026");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_eventFromToProvided_eventUpdated() throws WooperException {
        tm.addEventTask("event",
                LocalDateTime.of(2026, 2, 2, 14, 0),
                LocalDateTime.of(2026, 2, 2, 16, 0));

        ArrayList<String> args = buildUpdateArgs("event", "", "", "03/02/2026 10:00", "03/02/2026 11:00");
        tm.updateTask(0, args);

        Task t = tm.getAllTasks().get(0);
        assertTrue(t instanceof wooper.task.Event);

        wooper.task.Event e = (wooper.task.Event) t;
        assertEquals(LocalDateTime.of(2026, 2, 3, 10, 0), LocalDateTime.parse(e.getEventStart()));
        assertEquals(LocalDateTime.of(2026, 2, 3, 11, 0), LocalDateTime.parse(e.getEventEnd()));
    }

    @Test
    public void updateTask_eventByProvided_exceptionThrown() throws WooperException {
        tm.addEventTask("event",
                LocalDateTime.of(2026, 2, 2, 14, 0),
                LocalDateTime.of(2026, 2, 2, 16, 0));
        ArrayList<String> args = buildUpdateArgs("event", "", "06/06/2026", "", "");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_eventOnlyFromProvided_exceptionThrown() throws WooperException {
        tm.addEventTask("event",
                LocalDateTime.of(2026, 2, 2, 14, 0),
                LocalDateTime.of(2026, 2, 2, 16, 0));
        ArrayList<String> args = buildUpdateArgs("event", "", "", "02/02/2026 14:00", "");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    @Test
    public void updateTask_eventOnlyToProvided_exceptionThrown() throws WooperException {
        tm.addEventTask("event",
                LocalDateTime.of(2026, 2, 2, 14, 0),
                LocalDateTime.of(2026, 2, 2, 16, 0));
        ArrayList<String> args = buildUpdateArgs("event", "", "", "", "02/02/2026 16:00");
        assertThrows(WooperException.class, () -> tm.updateTask(0, args));
    }

    /**
     * Helper: builds args list in the exact positions TaskManager expects.
     * args.get(1)=taskType, get(2)=taskName, get(3)=by, get(4)=from, get(5)=to
     */
    private ArrayList<String> buildUpdateArgs(String taskType, String taskName, String by, String from, String to) {
        ArrayList<String> args = new ArrayList<>();
        args.add("update");
        args.add(taskType);
        args.add(taskName);
        args.add(by);
        args.add(from);
        args.add(to);
        return args;
    }
}
