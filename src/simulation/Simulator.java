package simulation;
import dns.ServerDNS;

public class Simulator {
    public static void executeSimulation() {
        ServerDNS serverDNS = new ServerDNS(5);

        for(int ind=0 ; ind<5 ; ind++) {
            serverDNS.insert(SimulatorDataProvider.DNS_SIMULATION_DATA[ind]);
        }

        serverDNS.showTable();
        serverDNS.handleUpdateTableCapacity(10);
        serverDNS.showTable();
    }
}
