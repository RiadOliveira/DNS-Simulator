import java.io.IOException;

import simulation.Simulator;

public class Main {
    public static void main(String[] args) {
        try {
            Simulator.executeSimulation();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}