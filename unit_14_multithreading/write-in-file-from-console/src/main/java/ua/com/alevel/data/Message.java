package ua.com.alevel.data;

public class Message {

    private volatile String text;

    private volatile boolean throwOff;

    public Message() {
        this.text = "";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void makeThrowOff() {
        this.throwOff = true;
    }

    public boolean isThrowOff() {
        return throwOff;
    }

    public String getText() {
        return text;
    }
}
