package org.howard.edu.lsp.midterm.question4;

public class Main {
    public static void main(String[] args) {
        Thermostat thermostat = new Thermostat("T001", "Living Room", 21.5); // Celsius
        DoorLock doorLock = new DoorLock("D001", "Front Door", 85);          // battery %
        Camera camera = new Camera("C001", "Garage", 72);                     // battery %

        thermostat.connect();
        doorLock.connect();
        camera.connect();

        System.out.println(thermostat.getStatus());
        System.out.println(doorLock.getStatus());
        System.out.println(camera.getStatus());
    }
}
