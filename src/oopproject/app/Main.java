package oopproject.app;

import oopproject.facade.UniversityFacade;
import oopproject.system.UniversitySystem;

public class Main {
    public static void main(String[] args) {
        UniversitySystem system = UniversitySystem.getInstance();
        new DemoDataLoader().loadDemoData(system);

        UniversityFacade facade = new UniversityFacade(system);
        facade.login("admin", "pass");
        system.addLog("system", "application started");
    }
}
