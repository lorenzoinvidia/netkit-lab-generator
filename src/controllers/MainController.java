package controllers;

import structures.Interface;
import structures.NetworkNode;
import structures.PC;

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
        System.out.print("Number of PC in the network: ");
        this.numberOfPC = Integer.parseInt(br.readLine());
        System.out.println("Number of PC: " + numberOfPC);

    }

    private void getPcInformation() throws IOException {

        String tempPCName;
        Interface tempIface = new Interface("","","");


        for (int i = 0; i < this.numberOfPC; i++) {

            System.out.print("PC " + (i+1) + " name: ");
            tempPCName = br.readLine();

            System.out.print("PC " + (i+1) + " ipAddress: ");
            tempIface.setName(br.readLine());

            System.out.print("PC " + (i+1) + " netMask: ");
            tempIface.setGateway(br.readLine());

            System.out.print("PC " + (i+1) + " gateway: ");
            tempIface.setGateway(br.readLine());

            // Create the PC
            nodes.add(new PC(tempPCName, "", tempIface));

        }
    }//getPcInformation()

    private void getRouterNumber() throws IOException {
        System.out.print("Number of router in the network: ");
        this.numberOfRouter = Integer.parseInt(br.readLine());
        System.out.println("Number of router: " + numberOfRouter);
    }

//    private void getRouterInformation() throws IOException {
//        for (int i = 0; i < this.numberOfRouter; i++) {
//            System.out.print("Insert Router " + (i+1) + " name: ");
//            nodes.add(br.readLine());
//        }
//    }

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
            //getRouterInformation(); //-------------> To be implemented

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
