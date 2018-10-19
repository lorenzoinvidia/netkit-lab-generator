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
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public List<PC> getPC() {
        return this.nodes.stream().filter(PC.class::isInstance).map(PC.class::cast).collect(Collectors.toList());
    }

    public List<Router> getRouter() {
        return this.nodes.stream().filter(Router.class::isInstance).map(Router.class::cast).collect(Collectors.toList());
    }

    private void getPcNumber() throws IOException {
        System.out.print("Number of PC: ");
        this.numberOfPC = Integer.parseInt(br.readLine());
        System.out.println("(DEBUG) Number of PC: " + numberOfPC); // Debug

    }

    private void getPcInformation() throws IOException {

        String tempPCName, tempIpAddress, tempNetMask, tempGateway;

        for (int i = 0; i < this.numberOfPC; i++) {

            // PC name
            System.out.print("PC " + (i+1) + " name: ");

            if ((tempPCName = br.readLine()) == null) {
                getPcInformation();
            }

            // IFACE address
            System.out.print("\tPC " + (i+1) + " IP address: ");
            if ((tempIpAddress = br.readLine()) == null) {
                getPcInformation();
            }

            // IFACE netmask
            System.out.print("\tPC " + (i+1) + " netmask: ");
            if ((tempNetMask = br.readLine()) == null) {
                getPcInformation();
            }

            // IFACE gateway
            System.out.print("\tPC " + (i+1) + " gateway: ");
            if ((tempGateway = br.readLine()) == null) {
                getPcInformation();
            }

            // Create the PC with single interface
            nodes.add(new PC(tempPCName, "", new Interface("eth0", tempIpAddress, tempNetMask, tempGateway)));
        }
    }


    private void getRouterNumber() throws IOException {
        System.out.print("Number of router: ");
        this.numberOfRouter = Integer.parseInt(br.readLine());
        System.out.println("(DEBUG) Number of router: " + numberOfRouter); // Debug
    }

    private void getRouterInformation() throws IOException {

        for (int i = 0; i < this.numberOfRouter; i++) {

            System.out.print("Router " + (i+1) + " name: ");
            String routerName = br.readLine();

            Interface[] ifaces = getRouterInterfaces(routerName);

            if (ifaces != null)
                // Create the router
                nodes.add(new Router(routerName, "", ifaces.length, ifaces, "STATIC"));
            else
                System.out.println("ifaces array is empty");
        }
    }

    // Setup the router interfaces
    private Interface[] getRouterInterfaces(String routerName) throws IOException{

        System.out.print("\tIfaces number of " + routerName + ": ");
        int tempNumOfInterfaces = Integer.parseInt(br.readLine());

        if (tempNumOfInterfaces >= 1){
            Interface[] tempIfaces = new Interface[tempNumOfInterfaces];

            for(int i=0; i<tempNumOfInterfaces; i++){

                String tempIfaceName, tempIpAddress, tempNetMask, tempGateway;

                // IFACE name
                tempIfaceName = "eth" + String.valueOf(i); // default iface name "ethINDEX"

                // IFACE address
                System.out.print("\t\t" + routerName + " interface (" + tempIfaceName + ")" + " IP address: ");
                if((tempIpAddress = br.readLine()) == null)
                    getRouterInterfaces(routerName);

                //--------> SHOULD BE A CHECK ON IP ADDRESS FORMAT HERE !!

                // IFACE netmask
                System.out.print("\t\t" + routerName + " interface (" + tempIfaceName + ")" + " Netmask: ");
                if((tempNetMask = br.readLine()) == null)
                    getRouterInterfaces(routerName);

                //--------> SHOULD BE A CHECK ON netmask FORMAT HERE !!

                // IFACE gateway
                System.out.print("\t\t" + routerName + " interface (" + tempIfaceName + ")" + " Gateway: ");
                if((tempGateway = br.readLine()) == null)
                    getRouterInterfaces(routerName);

                //--------> SHOULD BE A CHECK ON gateway FORMAT HERE !!

                tempIfaces[i] = new Interface(tempIfaceName, tempIpAddress, tempNetMask, tempGateway);
            }
            return tempIfaces;
        }
        else {
            System.out.println("Number of interfaces must be 1 at least!");
            getRouterInterfaces(routerName);
        }
        return null;
    }


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



    private void printNetwork() {
        System.out.println("Final network nodes: ");
        System.out.println(nodes.stream().map(NetworkNode::toString).collect(Collectors.joining("\n")));
    }

    public void init(String path) {
        try {
            // Request user input
            getPcNumber();
            getRouterNumber();
            getPcInformation();
            getRouterInformation();
            System.out.println("\n");

            // Print actual network
            printNetwork();

            // Create folder for nodes
            for(NetworkNode node : nodes)
                createNodeFolder(path, node.getName());
            
            // Create lab conf file
            createLabConfFile(path);
        }
        catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

}
