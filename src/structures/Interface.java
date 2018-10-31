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
        if(getGateway().equals(""))
            return String.format("\n\t\t Interface name: %s \n\t\t\t Interface address: %s \n\t\t\t Interface netmask: %s", getName(), getAddress(), setupNetmask(getNetmask()));
        else
            return String.format("\n\t\t Interface name: %s \n\t\t\t Interface address: %s \n\t\t\t Interface netmask: %s \n\t\t\t Interface gateway: %s", getName(), getAddress(), setupNetmask(getNetmask()), getGateway());
    }

    public String setupNetmask(String nMask) {
        int parsedInt = Integer.parseInt(nMask);
        long bits = 0;
        bits = 0xffffffff ^ (1 << 32 - parsedInt) - 1;
        return String.format("%d.%d.%d.%d", (bits & 0x0000000000ff000000L) >> 24, (bits & 0x0000000000ff0000) >> 16, (bits & 0x0000000000ff00) >> 8, bits & 0xff);
    }//setupNetmask()

}
