package wooper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wooper.exception.WooperException;
import wooper.task.Task;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskManagerTest {
    private TaskManager tm;

    @BeforeEach
    public void setUp() {
        tm = new TaskManager();
    }

    @Test
    public void markTaskDoneTest_validIndex_markDone() throws WooperException {
        tm.addToDoTask("read book");
        Task t = tm.markTaskDone(0);
        assertTrue(t.isDone());
    }

    @Test
    public void markTaskDoneTest_invalidIndex_markDoneFail() throws WooperException {
        tm.addToDoTask("read book");
        assertThrows(WooperException.class, () -> tm.markTaskDone(99));
        assertThrows(WooperException.class, () -> tm.markTaskDone(-1));
    }

    @Test
    public void unmarkTaskDoneTest_validIndex_markUndone() throws WooperException {
        tm.addToDoTask("read book");
        Task t = tm.markTaskDone(0);
        tm.unmarkTaskDone(0);
        assertFalse(t.isDone());
    }

    @Test
    public void unmarkTaskDoneTest_invalidIndex_markUndoneFail() throws WooperException {
        tm.addToDoTask("read book");
        tm.markTaskDone(0);
        assertThrows(WooperException.class, () -> tm.unmarkTaskDone(99));
        assertThrows(WooperException.class, () -> tm.unmarkTaskDone(-1));
    }

    @Test
    public void deleteTaskTest_validIndex_taskDeleted() throws WooperException {
        tm.addToDoTask("read book");
        tm.deleteTask(0);
        assertEquals(0, tm.getTaskListSize());
    }

    @Test
    public void deleteTaskTest_invalidIndex_taskNotDeleted() throws WooperException {
        tm.addToDoTask("read book");
        assertThrows(WooperException.class, () -> tm.deleteTask(99));
        assertThrows(WooperException.class, () -> tm.deleteTask(-1));
    }
}
