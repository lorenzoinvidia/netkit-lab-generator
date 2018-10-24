package controllers;

import structures.Interface;
import structures.NetworkNode;
import structures.PC;
import structures.Router;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceController {

    private List<NetworkNode> nodes;

    public InterfaceController(List<NetworkNode> nodes) {
        this.nodes = nodes;
    }

    private List<String> getPaths() {
        return nodes.stream().map(NetworkNode::getPath).collect(Collectors.toList());
    }

    public void writeInterfaces() {
        for(NetworkNode node : nodes) {
            if(node instanceof PC)
                writeInterfacePC((PC)node);
            else
                writeInterfaceRouter((Router)node);
        }
    }

    private void writeInterfacePC(PC pc) {
        File interfaceFile = new File(pc.getPath() + File.separator + "interfaces");
        Interface iface = pc.getInterface();
        try {
            FileOutputStream fos = new FileOutputStream(interfaceFile);
            PrintStream ps = new PrintStream(fos);
            ps.println("auto " + iface.getName());
            ps.println("iface " + iface.getName() + " inet static");
            ps.println("\taddress " + iface.getAddress());

            String tempNetmask = iface.getNetmask();
            ps.println("\tnetmask " + iface.setupNetmask(tempNetmask));
            ps.println("\tgateway " + iface.getGateway());
            ps.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeInterfaceRouter(Router router) {
        File interfaceFile = new File(router.getPath() + File.separator + "interfaces");
        Interface[] ifaces = router.getInterfaces();
        try {
            FileOutputStream fos = new FileOutputStream(interfaceFile);
            PrintStream ps = new PrintStream(fos);
            for(Interface iface : ifaces) {
                ps.println("auto " + iface.getName());
                ps.println("iface " + iface.getName() + " inet " + router.getRoutingProtocol());
                ps.println("\taddress " + iface.getAddress());

                String tempNetmask = iface.getNetmask();
                ps.println("\tnetmask " + iface.setupNetmask(tempNetmask));
                ps.println();
            }
            ps.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
