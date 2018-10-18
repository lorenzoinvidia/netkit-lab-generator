package structures;

public class PC extends NetworkNode {

    public PC(Interface iface){
        this.iface = iface;
    }


    public Interface getIface() {
        return iface;
    }

    public void setIface(Interface iface) {
        this.iface = iface;
    }

    private Interface iface;
}
