package ua.com.alevel.data.exception;
public class StudySessionDataNotFoundException extends StudySessionDataException {
    public<T> StudySessionDataNotFoundException(Long id, Class<T> requiredClass) {
        super(requiredClass.getName() + " with " + id + " doesn't exist");
    }
}
