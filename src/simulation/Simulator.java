package simulation;
import java.io.IOException;
import java.util.Scanner;

import dns.DNSNode;
import dns.ServerDNS;

public class Simulator {
    private static int TOTAL_DATA_QUANTITY = 100;
    private static int DATA_QUANTITY_TO_BE_ADDED_PER_TIME = 25;
    private static int dataQuantityAddedToServer = 0;

    private static final Scanner scanner = new Scanner(System.in);

    public static void executeSimulation() throws InterruptedException, IOException {
        ServerDNS serverDNS = new ServerDNS(DATA_QUANTITY_TO_BE_ADDED_PER_TIME);

        boolean hasAddedAllData = dataQuantityAddedToServer == TOTAL_DATA_QUANTITY;
        while(!hasAddedAllData) {
            showDNSDataAddedMessage();
            handleInsertPartOfDNSData(serverDNS);
            serverDNS.showTable();

            hasAddedAllData = dataQuantityAddedToServer == TOTAL_DATA_QUANTITY;
            if(!hasAddedAllData) {
                getAndHandleClientOperation(serverDNS, true);
                Thread.sleep(1000);
            }
        }

        int clientOperation = getAndHandleClientOperation(
            serverDNS, false
        );
        while(clientOperation != 0) {
            clientOperation = getAndHandleClientOperation(
                serverDNS, false
            );
        }
    }

    private static int getAndHandleClientOperation(
        ServerDNS serverDNS, boolean withoutExitOption
    ) {
        showClientMenu(withoutExitOption);
        return getAndHandleClientChoice(serverDNS);
    }

    private static void showClientMenu(boolean withoutExitOption) {
        System.out.println("Escolha a opção desejada:");
        System.out.println("[1] Pesquisar URL");
        System.out.println("[2] Inserir/Atualizar DNS");
        System.out.println("[3] Excluir DNS");

        if(!withoutExitOption) System.out.println("[0] Sair");
        System.out.println();
        System.out.print("Opção: ");
    }

    private static String getStringFromClient(String stringName) {
        System.out.print(stringName + ": ");
        return scanner.next();
    }

    private static void handleSearchChoice(ServerDNS serverDNS) {
        String receivedUrl = getStringFromClient("URL");
        String findedIp = serverDNS.search(receivedUrl);

        if(findedIp != null) 
            System.out.println("IP encontrado: " + findedIp);
        else System.out.println("IP não encontrado.");
    }

    private static void handleInsertChoice(ServerDNS serverDNS) {
        String receivedUrl = getStringFromClient("URL");
        String receivedIp = getStringFromClient("IP");
        
        serverDNS.insert(new DNSNode(receivedUrl, receivedIp));
        System.out.println("Registro DNS adicionado com sucesso!");
    }

    private static void handleRemoveChoice(ServerDNS serverDNS) {
        String receivedUrl = getStringFromClient("URL");
        DNSNode removedNode = serverDNS.remove(receivedUrl);

        if(removedNode == null) {
            System.out.println("URL não está presente no servidor.");
        } else {
            System.out.println(
                "O dado DNS referente ao Ip: " +
                removedNode.getIp() +
                " foi removido do servidor com sucesso!"
            );
        }
    }

    private static int getAndHandleClientChoice(ServerDNS serverDNS) {
        int clientChoice = scanner.nextInt();

        switch(clientChoice) {
            case 1: {
                handleSearchChoice(serverDNS);
                break;
            }
            case 2: {
                handleInsertChoice(serverDNS);
                break;
            }
            case 3: {
                handleRemoveChoice(serverDNS);
                break;
            }
            default: break;
        }

        System.out.println();
        return clientChoice;
    }

    private static void showDNSDataAddedMessage() {
        System.out.println("===============");
        System.out.println("ADIÇÃO DE DADOS");
        System.out.println("===============");
        System.out.println();
    }

    private static void handleInsertPartOfDNSData(ServerDNS serverDNS) {
        int updatedDataQuantityAddedToServer = 
            dataQuantityAddedToServer + DATA_QUANTITY_TO_BE_ADDED_PER_TIME;

        if(dataQuantityAddedToServer > 0) {
            serverDNS.handleUpdateTableCapacity(
                updatedDataQuantityAddedToServer
            );
        }

        for(
            int ind=dataQuantityAddedToServer ;
            ind<updatedDataQuantityAddedToServer
            ; ind++
        ) {
            serverDNS.insert(SimulatorDataProvider.DNS_SIMULATION_DATA[ind]);
        }

        dataQuantityAddedToServer = updatedDataQuantityAddedToServer;
    }
}
