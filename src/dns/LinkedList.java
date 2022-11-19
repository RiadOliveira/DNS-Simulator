package dns;
public class LinkedList {
    private DNSNode firstNode = null;

    public LinkedList() {
        super();
    }

    public void insert(DNSNode newNode) {
        // Update case
        DNSNode findedNode = searchDNSNode(newNode.getUrl());
        if(findedNode != null) {
            findedNode.setIp(newNode.getIp());
            return;
        }

        // Insertion case
        if(firstNode == null) {
            firstNode = newNode;
            return;
        }

        DNSNode lastNode = getLastNode();
        lastNode.setNext(newNode);
        newNode.setPrevious(lastNode);
    }

    private DNSNode getLastNode() {
        if(firstNode == null) return null;

        DNSNode iterationNode = firstNode;
        while(iterationNode.getNext() != null) {
            iterationNode = iterationNode.getNext();
        }

        return iterationNode;
    }

    private DNSNode searchDNSNode(String urlToSearch) {
        DNSNode iterationNode = firstNode;
        while(
            iterationNode != null &&
            !iterationNode.hasDNSUrlEqualsTo(urlToSearch)
        ) {
            iterationNode = iterationNode.getNext();
        }

        return iterationNode;
    }

    private boolean nodeNeedsToBeSorted(DNSNode node) {
        DNSNode previous = node.getPrevious();
        if(previous == null) return false;

        int accessComparsion = node.compareAccessFrequencyTo(previous);
        return accessComparsion > 0;
    }

    private void handleSortNodeOnList(DNSNode node) {
        if(!nodeNeedsToBeSorted(node)) return;

        DNSNode previous = node.getPrevious();
        DNSNode previousOfPrevious = previous.getPrevious();
        DNSNode next = node.getNext();

        if(previousOfPrevious == null) firstNode = node;
        else previousOfPrevious.setNext(node);
        
        node.setPrevious(previousOfPrevious);
        node.setNext(previous);
        
        previous.setPrevious(node);
        previous.setNext(next);

        if(next != null) next.setPrevious(previous);
        handleSortNodeOnList(node);
    }

    public String searchDNSIp(String urlToSearch) {
        DNSNode findedNode = searchDNSNode(urlToSearch);
        if(findedNode == null) return null;

        String nodeIp = findedNode.accessIp();
        handleSortNodeOnList(findedNode);

        return nodeIp;
    }

    public DNSNode remove(String urlToSearch) {
        DNSNode findedNode = searchDNSNode(urlToSearch);
        if(findedNode == null) return null;

        DNSNode previous = findedNode.getPrevious();
        DNSNode next = findedNode.getNext();

        if(previous == null && next == null) firstNode = null;
        else {
            if(previous == null) firstNode = next;
            else previous.setNext(next);
    
            if(next != null) next.setPrevious(previous);
        }

        return findedNode;
    }

    public void showNodes() {
        DNSNode iterationNode = firstNode;
        while(iterationNode != null) {
            System.out.println("URL: " + iterationNode.getUrl());
            System.out.println("IP: " + iterationNode.getIp());
            System.out.println(
                "Frequência de busca: " + iterationNode.getAccessFrequency()
            );

            System.out.println();
            iterationNode = iterationNode.getNext();
        }
    }

    public DNSNode getFirstNode() {
        return firstNode;
    }
}
