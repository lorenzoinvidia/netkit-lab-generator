package structures;

public class Router extends NetworkNode {

    private int numOfInterfaces;
    private Interface[] ifaces;
    private String routingProtocol;

    public Router(String name, String path, int numIface, Interface[] ifaces, String rProtocol){

        super(name, path);
        this.ifaces = ifaces;
        this.routingProtocol = rProtocol;
        this.numOfInterfaces = numIface;
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

    public int getNumOfInterfaces() {
        return numOfInterfaces;
    }

    public void setNumOfInterfaces(int numOfInterfaces) {
        this.numOfInterfaces = numOfInterfaces;
    }
}
