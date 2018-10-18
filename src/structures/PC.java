package structures;

public class PC extends NetworkNode {

    private Interface iface;

    public PC(String name, String path, Interface iface){

        super(name, path);
        this.iface = iface;
    }

    public Interface getIface() {
        return iface;
    }

    public void setIface(Interface iface) {
        this.iface = iface;
    }
}
