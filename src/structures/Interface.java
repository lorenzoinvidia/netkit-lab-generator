package structures;

public class Interface {

    private String name;
    private String address;
    private String netmask;
    private String gateway;

    public Interface(String name, String address, String netmask, String gateway) {
        this.name = name;
        this.address = address;
        this.netmask = netmask;
        this.gateway = gateway;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @Override
    public String toString() {
        return String.format("\n\t\t Interface name: %s \n\t\t Interface address: %s \n\t\t Interface netmask: %s \n\t\t Interface gateway: %s", getName(), getAddress(), getNetmask(), getGateway());
    }
}
