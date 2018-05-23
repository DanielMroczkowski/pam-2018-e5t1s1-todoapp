package pl.wat.e5t1s1.todoapp;

public class Task {
    private String title;
    private String text;

    public Task() {
    }

    public Task(String title, String text) {
        this.title = title;
        this.text = text;
    }

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
