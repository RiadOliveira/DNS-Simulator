public class ServerDNS {
    private LinkedList[] hashTable;

    public ServerDNS(int tableLength) {
        hashTable = new LinkedList[tableLength];
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

        String findedIp = hashTable[urlKey].searchDNSIp(urlToSearch);
        if(findedIp != null) {
            System.out.println(
                "URL Encontrada na posição "
                + urlKey + " da hash table."
            );
            System.out.println("Respectivo IP: " + findedIp);
        }

        return findedIp;
    }

    public void remove(String urlToSearch) {
        int urlKey = hashUrl(urlToSearch);
        if(hashTable[urlKey] == null) return;

        hashTable[urlKey].remove(urlToSearch);
    }

    private int hashUrl(String urlToHash) {
        int hashedKey = 0;
        int hashTableLength = hashTable.length;

        for(int ind=0 ; ind < urlToHash.length() ; ind++) {
            int iterationCharCode = urlToHash.charAt(ind);
            hashedKey = (31 * hashedKey + iterationCharCode) % hashTableLength;
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
