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
import java.util.regex.Pattern;
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

    // Pattern
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

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


    // Validate IP address
    public boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    // Validate Netmask
    public boolean validateNetmask(final String nMask){

        if ( (nMask.length() <= 2) && (isParsable(nMask))) {
            int parsedInt = Integer.parseInt(nMask);

            if ( (parsedInt >= 8) && (parsedInt < 32) ){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }//validateNetmask()


    private static boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(Exception e){
            parsable = false;
        }
        return parsable;
    }//isParsable()


    private void getPcNumber() throws IOException {
        System.out.print("Number of PC: ");
        String temp = br.readLine();

        if(isParsable(temp)) {
            this.numberOfPC = Integer.parseInt(temp);
            //System.out.println("(DEBUG) Number of PC: " + numberOfPC); //DEBUG
        }else {
            System.out.println("Insert a valid number!");
            getPcNumber();
        }
    }//getPcNumber()

    private void getPcInformation() throws IOException {

        String tempPCName, tempIpAddress, tempNetMask, tempGateway;

        for (int i = 0; i < this.numberOfPC; i++) {

            // PC name
            System.out.print("PC " + (i+1) + " name: ");
            tempPCName = br.readLine();
            if (tempPCName == null || tempPCName.isEmpty()) {
                System.out.println("This field cannot be empty!");
                getPcInformation();
                return;
            }

            // IFACE address
            System.out.print("\tPC " + (i+1) + " IP address: ");
            tempIpAddress = br.readLine();
            if (tempIpAddress == null || tempIpAddress.isEmpty()) {
                System.out.println("This field cannot be empty!");
                getPcInformation();
                return;
            }else if(!validateIP(tempIpAddress)){
                System.out.println("Address is not in the correct format!");
                getPcInformation();
                return;
            }

            // IFACE netmask
            System.out.print("\tPC " + (i+1) + " netmask: ");
            tempNetMask = br.readLine();
            if (tempNetMask == null || tempNetMask.isEmpty()) {
                System.out.println("This field cannot be empty!");
                getPcInformation();
                return;
            }else if (!validateNetmask(tempNetMask)){
                System.out.println("Netmask is not in the correct format!");
                getPcInformation();
                return;
            }

            // IFACE gateway
            System.out.print("\tPC " + (i+1) + " gateway: ");
            tempGateway = br.readLine();
            if (tempGateway == null || tempGateway.isEmpty()) {
                System.out.println("This field cannot be empty!");
                getPcInformation();
                return;
            }else if(!validateIP(tempGateway)){
                System.out.println("Gateway is not in the correct format!");
                getPcInformation();
                return;
            }

            // Create the PC with single interface
            nodes.add(new PC(tempPCName, "", new Interface("eth0", tempIpAddress, tempNetMask, tempGateway)));
        }
    }//getPcInformation()


    private void getRouterNumber() throws IOException {
        System.out.print("Number of router: ");
        String temp = br.readLine();

        if(isParsable(temp)) {
            this.numberOfRouter = Integer.parseInt(temp);
            //System.out.println("(DEBUG) Number of router: " + numberOfRouter); //DEBUG
        }else {
            System.out.println("Insert a valid number!");
            getRouterNumber();
        }
    }//getRouterNumber()



    private void getRouterInformation() throws IOException {

        for(int i = 0; i < this.numberOfRouter; i++) {

            System.out.print("Router " + (i+1) + " name: ");
            String routerName = br.readLine();

            if(routerName == null || routerName.isEmpty()) {
                System.out.println("This field cannot be empty!");
                getRouterInformation();
                return;
            }

            Interface[] ifaces = getRouterInterfaces(routerName);

            if (ifaces != null) {
                System.out.println("(DEBUG) Create the router . . .");
                // Create the router
                nodes.add(new Router(routerName, "", ifaces.length, ifaces, "static"));
            }else {
                System.out.println("ifaces array is empty");
                getRouterInformation();
                return;
            }
        }
    }//getRouterInformation()



    // Setup the router interfaces
    private Interface[] getRouterInterfaces(String routerName) throws IOException{

        int numOfInterfaces = 0;

        System.out.print("\tIfaces number of " + routerName + ": ");
        String temp = br.readLine();

        if(isParsable(temp)){
            numOfInterfaces = Integer.parseInt(temp);
            //System.out.println("\t(DEBUG) Number of interfaces: " + numOfInterfaces); //DEBUG
        }else {
            System.out.println("Insert a valid number!");
            return null;
        }

        if (numOfInterfaces > 0){
            Interface[] tempIfaces = new Interface[numOfInterfaces];

            for(int i=0; i<numOfInterfaces; i++){

                String tempIfaceName, tempIpAddress, tempNetMask, tempGateway;

                // IFACE name
                tempIfaceName = "eth" + String.valueOf(i); // default iface name "ethINDEX"

                // IFACE address
                System.out.print("\t\t" + routerName + " interface (" + tempIfaceName + ")" + " IP address: ");
                tempIpAddress = br.readLine();
                if(tempIpAddress == null || tempIpAddress.isEmpty()) {
                    System.out.println("\t\tThis field cannot be empty!");
                    return null;
                }else if(!validateIP(tempIpAddress)){
                    System.out.println("\t\tAddress is not in the correct format!");
                    getPcInformation();
                    return null;
                }

                // IFACE netmask
                System.out.print("\t\t" + routerName + " interface (" + tempIfaceName + ")" + " Netmask: ");
                tempNetMask = br.readLine();
                if(tempNetMask == null || tempNetMask.isEmpty()) {
                    System.out.println("\t\tThis field cannot be empty!");
                    return null;
                }else if (!validateNetmask(tempNetMask)){
                    System.out.println("\t\tNetmask is not in the correct format!");
                    getPcInformation();
                    return null;
                }

                tempIfaces[i] = new Interface(tempIfaceName, tempIpAddress, tempNetMask, "");
            }
            return tempIfaces;

        } else {
            System.out.println("\tNumber of interfaces must be 1 at least!");
            return null;
        }
    }//getRouterInterfaces()


    private void createNodeFolder(String path, NetworkNode node) throws IOException {
        File actualPath = new File(path + File.separator + node.getName() + File.separator + "etc" + File.separator + "network");
        if(actualPath.isFile())
            System.out.println("Bad path! (the actual path is a file)");
        else if(actualPath.isDirectory())
            System.out.println( node.getName() + " already exist at path: \"" + actualPath.getAbsolutePath() + "\"");
        else {
            if (actualPath.mkdirs()) {
                System.out.println(node.getName() + " created at path: \"" + actualPath.getAbsolutePath() + "\"");

                // set path to node
                node.setPath(path + File.separator + node.getName() + File.separator + "etc" + File.separator + "network");

                // create interfaces file
                File interfaceFile = new File(actualPath.getAbsolutePath() + File.separator + "interfaces");
                if(interfaceFile.createNewFile())
                    System.out.println( "Interface file of " + node.getName() + " created at path: \"" + interfaceFile.getAbsolutePath() + "\"");
                else
                    System.out.println( "Error on creating interface file of " + node.getName() + "(Exception to be fixed!)" );
            }
            else
                System.out.println( "Error on creating " + node.getName() + "(Exception to be fixed!)" );
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
                createNodeFolder(path, node);

            // Create lab conf file
            createLabConfFile(path);

            // Call Interface controller to modify interfaces files
            InterfaceController ic = new InterfaceController(nodes);
            ic.writeInterfaces();

            // Call Domain controller to modify lab conf file
            DomainController dc = new DomainController(path, getPC(), getRouter());
            dc.writeLabConf();
        }
        catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

}
