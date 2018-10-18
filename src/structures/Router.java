package structures;

public class Router extends NetworkNode {

    //private int numOfInterfaces;
    private Interface[] ifaces;
    private String routingProtocol;

    public Router(Interface[] ifaces, String rProtocol){
        this.ifaces = ifaces;
        this.routingProtocol = rProtocol;
    }

    public Interface[] getIfaces() {
        return ifaces;
    }

    public void setIfaces(Interface[] ifaces) {
        this.ifaces = ifaces;
    }

    public String getRoutingProtocol() {
        return routingProtocol;
    }

    public void setRoutingProtocol(String routingProtocol) {
        this.routingProtocol = routingProtocol;
    }
}
