package oopproject.app;

import oopproject.auth.AuthService;
import oopproject.storage.DataStorage;

public class Main {
    public static void main(String[] args) {
        DataStorage storage = DataStorage.getInstance();
        new DemoDataLoader().loadDemoData(storage);
        new AuthService(storage).authenticate("admin", "pass");
        storage.addLog("system", "application started");
    }
}
