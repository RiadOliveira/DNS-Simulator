package dns;
public class DNSNode {
    private String url;
    private String ip;
    private DNSNode previous = null;
    private DNSNode next = null;

    private int accessFrequency = 0;

    public DNSNode(String url, String ip) {
        this.url = url;
        this.ip = ip;
    }

    public static DNSNode FromAnotherNode(DNSNode node) {
        return new DNSNode(node.getUrl(), node.getIp());
    }

    public String accessIp() {
        accessFrequency++;
        return ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int compareAccessFrequencyTo(DNSNode node) {
        return accessFrequency - node.getAccessFrequency();
    }

    public boolean hasDNSUrlEqualsTo(String urlToCompare) {
        return url.equals(urlToCompare);
    }

    public String getUrl() {
        return url;
    }

    public int getAccessFrequency() {
        return accessFrequency;
    }

    public DNSNode getPrevious() {
        return previous;
    }

    public void setPrevious(DNSNode previous) {
        this.previous = previous;
    }

    public DNSNode getNext() {
        return next;
    }

    public void setNext(DNSNode next) {
        this.next = next;
    }
}
