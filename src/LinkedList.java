public class LinkedList {
    DNSNode firstNode = null;

    public LinkedList() {
        super();
    }

    public void insert(String url, String ip) {
        // Update case
        DNSNode findedNode = searchDNSNode(url);
        if(findedNode != null) {
            findedNode.setIp(ip);
            return;
        }

        // Insertion case
        DNSNode newNode = new DNSNode(url, ip);
        if(firstNode == null) {
            firstNode = newNode;
            return;
        }

        DNSNode lastDNSNode = getLastNode();
        lastDNSNode.setNext(newNode);
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

    public void remove(String urlToSearch) {
        DNSNode findedNode = searchDNSNode(urlToSearch);

        DNSNode previous = findedNode.getPrevious();
        DNSNode next = findedNode.getNext();

        if(previous == null && next == null) {
            firstNode = null;
            return;
        }

        if(previous == null) firstNode = next;
        else previous.setNext(next);
        
        if(next != null) next.setPrevious(previous);
    }
}
