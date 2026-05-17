package oopproject.storage;

import oopproject.exceptions.DataStorageException;
import oopproject.system.UniversitySystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileDataStore implements DataStore, Serializable {
    private String filePath;

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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(system);
        } catch (IOException e) {
            throw new DataStorageException(filePath, "failed to save system", e);
        }
    }

    @Override
    public UniversitySystem load() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (UniversitySystem) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DataStorageException(filePath, "failed to load system", e);
        }
    }
}
