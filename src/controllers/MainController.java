package controllers;

import structures.Interface;
import structures.NetworkNode;
import structures.PC;
import structures.Router;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    // Buffer reader to read user input
    private BufferedReader br;
    // Number of network PC
    private int numberOfPC;
    // Number of network router
    private int numberOfRouter;
    // List of network nodes
    private List<NetworkNode> nodes;

    public MainController() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.numberOfPC = 0;
        this.numberOfRouter = 0;
        this.nodes = new ArrayList<>();
    }

    public int getNumberOfPC() {
        return this.numberOfPC;
    }

    public void setNumberOfPC(int numberOfPC) {
        this.numberOfPC = numberOfPC;
    }

    public int getNumberOfRouter() {
        return this.numberOfRouter;
    }

    public void setNumberOfRouter(int numberOfRouter) {
        this.numberOfRouter = numberOfRouter;
    }

    public List<NetworkNode> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<NetworkNode> nodes) {
        this.nodes = nodes;
    }

    private void getPcNumber() throws IOException {
        System.out.print("Number of PC: ");
        this.numberOfPC = Integer.parseInt(br.readLine());
        System.out.println("(DEBUG) Number of PC: " + numberOfPC);//DEBUG

    }

    private void getPcInformation() throws IOException {

        String tempPCName;
        Interface tempIface = new Interface();


        for (int i = 0; i < this.numberOfPC; i++) {

            //PC NAME
            System.out.print("PC " + (i+1) + " name: ");
            tempPCName = br.readLine();

            //IFACE NAME
            tempIface.setName("eth0");//default eth0

            //IFACE ADDR
            System.out.print("PC " + (i+1) + " ipAddress: ");
            tempIface.setAddress(br.readLine());

            //IFACE NETMASK
            System.out.print("PC " + (i+1) + " netMask: ");
            tempIface.setNetmask(br.readLine());

            //IFACE GATEWAY
            System.out.print("PC " + (i+1) + " gateway: ");
            tempIface.setGateway(br.readLine());

            // Create the PC
            nodes.add(new PC(tempPCName, "", tempIface));

        }
    }//getPcInformation()

    private void getRouterNumber() throws IOException {
        System.out.print("Number of router: ");
        this.numberOfRouter = Integer.parseInt(br.readLine());
        System.out.println("(DEBUG) Number of router: " + numberOfRouter);//DEBUG
    }

    private void getRouterInformation() throws IOException {

        String tempRouterName;
        Interface[] ifaces;

        for (int i = 0; i < this.numberOfRouter; i++) {

            System.out.print("Router " + (i+1) + " name: ");
            tempRouterName = br.readLine();

            ifaces = getRouterInterfaces(tempRouterName);

            if (ifaces != null){
                // Create the router
                nodes.add(new Router(tempRouterName, "", ifaces.length, ifaces, "STATIC"));

                //DEBUG
                for (int j=0; j<ifaces.length; j++) {
                    System.out.println("Interface (" + ifaces[j].getName() + "): " + "\n" +
                            "IP addr: " + ifaces[j].getAddress() + "\n" +
                            "Netmask: " + ifaces[j].getNetmask() + "\n" +
                            "Gateway: " + ifaces[j].getGateway() + "\n");
                }

            } else {
                System.out.println("ifaces array is NULL");
            }

        }//for loop
    }//getRouterInformation()


    // Setup the router interfaces
    private Interface[] getRouterInterfaces(String routerName) throws IOException{

        String tempRouterName = routerName;

        System.out.print("Num of ifaces: ");
        int tempNumOfInterfaces = br.read();

        if (tempNumOfInterfaces >= 1){
            //continue execution

            Interface[] tempIfaces = new Interface[tempNumOfInterfaces];

            for(int i=0; i<tempNumOfInterfaces; i++){

                tempIfaces[i] = new Interface();

                String index = "eth" + String.valueOf(i);
                //System.out.print(index);//DEBUG

                //IFACE NAME
                tempIfaces[i].setName(index); //default iface name ethINDEX

                //IFACE IP ADDRESS
                System.out.print("Interface (" + tempIfaces[i].getName() + ")" + " IP address: ");
                tempIfaces[i].setAddress(br.readLine());
                //--------> SHOULD BE A CHECK ON IP ADDRESS FORMAT HERE !!

                //IFACE NETMASK
                System.out.print("Interface (" + tempIfaces[i].getName() + ")" + " Netmask: ");
                tempIfaces[i].setNetmask(br.readLine());
                //--------> SHOULD BE A CHECK ON netmask FORMAT HERE !!

                //IFACE GATEWAY
                System.out.print("Interface (" + tempIfaces[i].getName() + ")" + " Gateway: ");
                tempIfaces[i].setGateway(br.readLine());
                //--------> SHOULD BE A CHECK ON gateway FORMAT HERE !!

            }//for loop

            return tempIfaces;

        }else{
            System.out.println("Num of iface must be 1 at least!");
            getRouterInterfaces(routerName);
        }

        return null;

    }//getRouterInterfaces()


    private void createNodeFolder(String path, String nodeName) throws IOException {
        File actualPath = new File(path + File.separator + nodeName + File.separator + "etc" + File.separator + "network");
        if(actualPath.isFile())
            System.out.println("Bad path! (the actual path is a file)");
        else if(actualPath.isDirectory())
            System.out.println( nodeName + " already exist at path: \"" + actualPath.getAbsolutePath() + "\"");
        else {
            if (actualPath.mkdirs()) {
                System.out.println(nodeName + " created at path: \"" + actualPath.getAbsolutePath() + "\"");

                // create interfaces file
                File interfaceFile = new File(actualPath.getAbsolutePath() + File.separator + "interfaces");
                if(interfaceFile.createNewFile())
                    System.out.println( "Interface file of " + nodeName + " created at path: \"" + interfaceFile.getAbsolutePath() + "\"");
                else
                    System.out.println( "Error on creating interface file of " + nodeName + "(Exception to be fixed!)" );
            }
            else
                System.out.println( "Error on creating " + nodeName + "(Exception to be fixed!)" );
        }
    }

    private void createLabConfFile(String path) throws IOException {
        File filePath = new File(path + File.separator + "lab.conf");
        if(filePath.exists())
            System.out.println("lab.conf file already exist at path: \"" + filePath.getAbsolutePath() + "\"");
        else {
            if(filePath.createNewFile())
                System.out.println("lab.conf created at path: \"" + filePath.getAbsolutePath() + "\"");
            else
                System.out.println( "Error on creating lab.conf (Exception to be fixed!)");
        }
    }



//    private void printNetwork() {
//        System.out.print("Final network nodes: ");
//        nodes.stream().reduce( (a, b) -> a + ", " + b).ifPresent(System.out::println);
//    }

    public void init(String path) {
        try {
            // Request user input
            getPcNumber();
            getRouterNumber();
            getPcInformation();
            getRouterInformation();

            // Print actual network
            //printNetwork(); //-------------> To be implemented

            // Create folder for nodes
            for(int i=0; i<nodes.size(); i++)
                createNodeFolder(path, nodes.get(i).getName());
            
            // Create lab conf file
            createLabConfFile(path);
        }
        catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

}
