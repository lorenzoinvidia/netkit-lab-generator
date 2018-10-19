package structures;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Router extends NetworkNode {

    private int numOfInterfaces;
    private Interface[] ifaces;
    private String routingProtocol;

    public Router(String name, String path, int numIface, Interface[] ifaces, String rProtocol){

        super(name, path);
        this.ifaces = ifaces;
        this.routingProtocol = rProtocol; //default: STATIC
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

    public int getNumberOfInterfaces() {
        return numOfInterfaces;
    }

    public void setNumOfInterfaces(int numOfInterfaces) {
        this.numOfInterfaces = numOfInterfaces;
    }

    public Interface[] getInterfaces() {
        return ifaces;
    }

    public void setInterfaces(Interface[] ifaces) {
        this.ifaces = ifaces;
    }

    @Override
    public String toString() {
        return String.format("Router name: %s \n\t Router protocol: %s \n\t Interfaces: %s\n\t",
                getName(),
                getRoutingProtocol(),
                Arrays.stream(getInterfaces()).map(Interface::toString).collect(Collectors.joining("")));
    }
}
