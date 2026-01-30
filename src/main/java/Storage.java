import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Storage {
    private final Path filePath = Paths.get("data", "wooper.json");

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
            throw new WooperException("Woop! Failed to read save file.");
        } catch (JSONException e) {
            // corrupted / not valid JSON
            throw new WooperException("Woop! Save file is corrupted (invalid JSON).");
        }
    }

    public void save(ArrayList<Task> taskList) throws IOException {
        // create folder if missing
        Files.createDirectories(filePath.getParent());

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < taskList.size(); i++) {
            jsonArray.put(taskToJson(taskList.get(i)));
        }

        // write to file
        Files.writeString(filePath, jsonArray.toString(2));
    }

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

    private Task jsonToTask(JSONObject obj) throws WooperException {
        String type = obj.optString("type", "");
        String desc = obj.optString("desc", "");
        boolean done = obj.optBoolean("done", false);

        if (desc.isBlank()) {
            throw new WooperException("Woop! Save file is corrupted (missing description).");
        }

        Task task;
        switch (type) {
            case "T":
                task = new ToDo(desc);
                break;
            case "D":
                String by = obj.optString("by", "");
                if (by.isBlank()) {
                    throw new WooperException("Woop! Save file is corrupted (missing deadline).");
                }
                if (DateTimeUtil.isDateTime(by)) {
                    LocalDateTime dt = LocalDateTime.parse(by);
                    task = new Deadline(desc, dt);
                } else {
                    LocalDate d = LocalDate.parse(by);
                    task = new Deadline(desc, d);
                }
                break;
            case "E":
                String from = obj.optString("from", "");
                String to = obj.optString("to", "");
                if (from.isBlank() || to.isBlank()) {
                    throw new WooperException("Woop! Save file is corrupted (missing event time).");
                }

                boolean fromDT = DateTimeUtil.isDateTime(from);
                boolean toDT = DateTimeUtil.isDateTime(to);

                if (fromDT != toDT) {
                    throw new WooperException("Woop! Save file is corrupted (event time format mismatch).");
                }
                if (fromDT && toDT) {
                    LocalDateTime start = LocalDateTime.parse(from);
                    LocalDateTime end = LocalDateTime.parse(to);
                    task = new Event(desc, start, end);
                } else {
                    LocalDate start = LocalDate.parse(from);
                    LocalDate end = LocalDate.parse(to);
                    task = new Event(desc, start, end);
                }
                break;
            default:
                throw new WooperException("Woop! Save file is corrupted (unknown task type).");
        }
        task.setDone(done);
        return task;
    }
}
