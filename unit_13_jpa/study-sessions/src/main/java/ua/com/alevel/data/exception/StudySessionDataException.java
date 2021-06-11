package ua.com.alevel.data.exception;

public abstract class StudySessionDataException  extends Exception{

    public StudySessionDataException(String message) {
        super(message);
    }

    public StudySessionDataException(Throwable cause) {
        super(cause);
    }
}
