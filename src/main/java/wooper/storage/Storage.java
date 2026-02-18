package wooper.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wooper.exception.WooperException;
import wooper.task.Deadline;
import wooper.task.Event;
import wooper.task.Task;
import wooper.task.ToDo;
import wooper.util.DateTimeUtil;

/**
 * Loads tasks from and saves tasks to a JSON file on disk.
 * Tasks are persisted in {@code data/wooper.json} as a JSON array. Each task is stored
 * with its type, description, completion status, and any relevant date information.
 */
public class Storage {
    /**
     * Path to the JSON file used for persisting tasks.
     */
    private final Path filePath = Paths.get("data", "wooper.json");

    /**
     * Returns the list of tasks loaded from the save file.
     * Returns an empty list if the save file does not exist or contains no tasks.
     *
     * @return The list of tasks loaded from storage.
     * @throws WooperException If the file cannot be read or the JSON content is invalid.
     */
    public ArrayList<Task> load() throws WooperException {
        // if file dont exist = no data
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try {
            String content = Files.readString(filePath).trim();

            // file empty = no data
            if (content.isEmpty()) {
                return new ArrayList<>();
            }

            JSONArray jsonArr = new JSONArray(content);
            ArrayList<Task> tasks = new ArrayList<>();

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject obj = jsonArr.getJSONObject(i);
                Task t = jsonToTask(obj);
                tasks.add(t);
            }

            return tasks;

        } catch (IOException e) {
            throw new WooperException("Failed to read save file.");
        } catch (JSONException e) {
            // corrupted / not valid JSON
            throw new WooperException("Save file is corrupted (invalid JSON).");
        }
    }

    /**
     * Saves the specified list of tasks to the save file.
     * Creates the parent directory if it does not already exist.
     *
     * @param taskList List of tasks to be saved.
     * @throws IOException If writing to the save file fails.
     */
    public void save(ArrayList<Task> taskList) throws IOException {
        // create folder if missing
        Files.createDirectories(filePath.getParent());

        JSONArray jsonArray = new JSONArray(
                taskList.stream()
                        .map(this::taskToJson)
                        .toList()
        );

        // write to file
        Files.writeString(filePath, jsonArray.toString(2));
    }

    /**
     * Returns a JSON representation of the specified task.
     *
     * @param t Task to be converted.
     * @return A {@link JSONObject} containing the task data.
     */
    private JSONObject taskToJson(Task t) {
        JSONObject obj = new JSONObject();
        obj.put("done", t.isDone());
        obj.put("desc", t.getTaskName());

        if (t instanceof ToDo) {
            obj.put("type", "T");
        } else if (t instanceof Deadline d) {
            obj.put("type", "D");
            obj.put("by", d.getDeadlineDueBy());
        } else if (t instanceof Event e) {
            obj.put("type", "E");
            obj.put("from", e.getEventStart());
            obj.put("to", e.getEventEnd());
        }

        return obj;
    }

    /**
     * Returns a task constructed from the specified JSON object.
     *
     * @param obj JSON object containing task data.
     * @return The task represented by the JSON object.
     * @throws WooperException If the JSON object is missing required fields or contains invalid values.
     */
    private Task jsonToTask(JSONObject obj) throws WooperException {
        String type = obj.optString("type", "");
        String desc = obj.optString("desc", "");
        boolean done = obj.optBoolean("done", false);

        if (desc.isBlank()) {
            throw new WooperException("Save file is corrupted (missing description).");
        }

        Task task;
        switch (type) {
        case "T":
            task = createTodo(desc);
            break;
        case "D":
            task = createDeadline(obj, desc);
            break;
        case "E":
            task = createEvent(obj, desc);
            break;
        default:
            throw new WooperException("Save file is corrupted (unknown task type).");
        }

        task.setDone(done);
        return task;
    }

    private Task createTodo(String desc) {
        return new ToDo(desc);
    }

    private Task createDeadline(JSONObject obj, String desc)
            throws WooperException {

        String by = obj.optString("by", "");
        if (by.isBlank()) {
            throw new WooperException(
                    "Save file is corrupted (missing deadline).");
        }

        if (DateTimeUtil.isDateTime(by)) {
            return new Deadline(desc, LocalDateTime.parse(by));
        } else {
            return new Deadline(desc, LocalDate.parse(by));
        }
    }

    private Task createEvent(JSONObject obj, String desc)
            throws WooperException {

        String from = obj.optString("from", "");
        String to = obj.optString("to", "");

        if (from.isBlank() || to.isBlank()) {
            throw new WooperException(
                    "Save file is corrupted (missing event time).");
        }

        boolean fromDT = DateTimeUtil.isDateTime(from);
        boolean toDT = DateTimeUtil.isDateTime(to);

        if (fromDT != toDT) {
            throw new WooperException(
                    "Save file is corrupted (event time format mismatch).");
        }

        if (fromDT) {
            return new Event(desc,
                    LocalDateTime.parse(from),
                    LocalDateTime.parse(to));
        } else {
            return new Event(desc,
                    LocalDate.parse(from),
                    LocalDate.parse(to));
        }
    }
}
