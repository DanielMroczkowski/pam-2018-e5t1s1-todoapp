package pl.wat.e5t1s1.todoapp;

/**
 * Definicja zadania
 */
public class Task {
    private int id;
    private String title;
    private String text;
    private String date;
    private String time;
    private int alarm;

    public Task() {
    }

    public Task(int id, String title, String text, String date, String time, int alarm) {

        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.time = time;
        this.alarm = alarm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
