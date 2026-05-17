package oopproject.storage;

import java.io.*;
import oopproject.exceptions.DataStorageException;
import oopproject.system.UniversitySystem;

public class FileDataStore implements DataStore {
    private String filePath = "university-system.ser";

    public FileDataStore() {
        this("university-system.ser");
    }

    public FileDataStore(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public void save(UniversitySystem system) {
        if (system == null) {
            throw new IllegalArgumentException("System cannot be null");
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(system);
        } catch (IOException e) {
            throw new DataStorageException(filePath, "failed to save system", e);
        }
    }

    @Override
    public UniversitySystem load() {
        File file = new File(filePath);
        if (!file.exists()) {
            return UniversitySystem.getInstance();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (UniversitySystem) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataStorageException(filePath, "failed to load system", e);
        }
    }
}