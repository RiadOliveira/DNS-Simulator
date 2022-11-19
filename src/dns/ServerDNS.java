package dns;

import utils.UtilsProvider;

public class ServerDNS {
    private static final int LISTS_DEFAULT_LENGTH = 5;
    private LinkedList[] hashTable;

    public ServerDNS(int expectedTableCapacity) {
        int parsedTableLength = getIdealTableLengthBasedOnExpectedCapacity(
            expectedTableCapacity
        );
        hashTable = new LinkedList[parsedTableLength];
    }

    private int getLengthToReduceCollisions(int expectedLength) {
        int powerOfTwoNextToLength = UtilsProvider.getPowerOfTwoBiggerThan(expectedLength);
        int primeLowerThanPowerOfTwo = UtilsProvider.getFirstPrimeLowerThan(powerOfTwoNextToLength);

        return primeLowerThanPowerOfTwo;
    }

    private int getIdealTableLengthBasedOnExpectedCapacity(int expectedTableCapacity) {
        int hashTableExpectedLength = expectedTableCapacity/LISTS_DEFAULT_LENGTH;
        return getLengthToReduceCollisions(hashTableExpectedLength);
    }

    public void handleUpdateTableCapacity(int expectedTableCapacity) {
        LinkedList[] previousHashTable = this.hashTable;
        
        int parsedTableLength = getIdealTableLengthBasedOnExpectedCapacity(
            expectedTableCapacity
        );
        this.hashTable = new LinkedList[parsedTableLength];

        for (LinkedList list : previousHashTable) {
            if(list == null) continue;

            DNSNode iterationNode = list.getFirstNode();
            while(iterationNode != null) {
                insert(DNSNode.FromAnotherNode(iterationNode));
                iterationNode = iterationNode.getNext();
            }
        }
    }

    public void insert(DNSNode newNode) {
        int urlKey = hashUrl(newNode.getUrl());
        if(hashTable[urlKey] == null) {
            hashTable[urlKey] = new LinkedList();
        }
        
        hashTable[urlKey].insert(newNode);
    }

    public String search(String urlToSearch) {
        int urlKey = hashUrl(urlToSearch);
        if(hashTable[urlKey] == null) return null;

        return hashTable[urlKey].searchDNSIp(urlToSearch);
    }

    public DNSNode remove(String urlToSearch) {
        int urlKey = hashUrl(urlToSearch);
        if(hashTable[urlKey] == null) return null;

        return hashTable[urlKey].remove(urlToSearch);
    }

    private int hashUrl(String urlToHash) {
        int hashedKey = 0;
        int hashTableLength = hashTable.length;

        for(int ind=0 ; ind < urlToHash.length() ; ind++) {
            int iterationCharCode = urlToHash.charAt(ind);
            hashedKey = (hashedKey + iterationCharCode) % hashTableLength;
        }

        return hashedKey;
    }

    public void showTable() {
        for (int ind = 0 ; ind < hashTable.length ; ind++) {
            LinkedList list = hashTable[ind];
            boolean listIsNull = list == null;

            System.out.println("Index: " + ind);
            System.out.println(
                "Conteúdo: " + (listIsNull ? null : "") + '\n'
            );

            if(!listIsNull) list.showNodes();
        }
    }
}
