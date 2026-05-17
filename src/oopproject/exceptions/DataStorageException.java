package oopproject.exceptions;

public class DataStorageException extends StorageException {
    public DataStorageException(String filePath, String reason) {
        super(filePath, reason);
    }

    public DataStorageException(String filePath, String reason, Throwable cause) {
        super(filePath, reason, cause);
    }
}
