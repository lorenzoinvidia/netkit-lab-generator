package structures;

public class PC extends NetworkNode {

    private Interface iface;

    public PC(String name, String path, Interface iface) {

        super(name, path);
        this.iface = iface;
    }

    public Interface getInterfaces() {
        return iface;
    }

    public void setInterfaces(Interface iface) {
        this.iface = iface;
    }

    @Override
    public String toString() {
        return String.format("PC name: %s \n\t Interface: %s \n\t", getName(), getInterfaces().toString());
    }
}
