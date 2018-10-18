package structures;

public class Interface {

    private String name;
    private String address;
    private String netmask;
    private String gateway;

    public Interface(String address, String netmask, String gateway){

        this.name = "";         // will be assigned next
        this.address = address; // ex. 10.10.10.10
        this.netmask = netmask; // ex. 24 (stands for /24 or 255.255.255.0)
        this.gateway = gateway; // ex. 10.10.10.1
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
}
