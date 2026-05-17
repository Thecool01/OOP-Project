package oopproject.app;

import oopproject.facade.UniversityFacade;
import oopproject.system.UniversitySystem;

public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        app.runDemo();
    }

    public void runDemo() {
        UniversitySystem system = UniversitySystem.getInstance();
        UniversityFacade facade = new UniversityFacade(system);
        new DemoDataLoader().loadDemoData(facade);
        facade.login("admin", "pass");
        system.addLog("system", "application started");
    }
}
