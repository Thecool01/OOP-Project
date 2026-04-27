package oopproject.exceptions;

public class StorageException extends UniversitySystemException {
    private final String filePath;
    private final String reason;

    public StorageException(String filePath, String reason) {
        super("Storage operation failed for file '" + filePath + "': " + reason);
        this.filePath = filePath;
        this.reason = reason;
    }

    public StorageException(String filePath, String reason, Throwable cause) {
        super("Storage operation failed for file '" + filePath + "': " + reason, cause);
        this.filePath = filePath;
        this.reason = reason;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getReason() {
        return reason;
    }
}
