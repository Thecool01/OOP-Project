package oopproject.storage;

import oopproject.system.UniversitySystem;

public interface DataStore {
    void save(UniversitySystem system);

    UniversitySystem load();
}
