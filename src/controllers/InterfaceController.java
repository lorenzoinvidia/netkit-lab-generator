package controllers;

import structures.NetworkNode;
import structures.PC;
import structures.Router;

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

    }

    private void writeInterfaceRouter(Router router) {

    }
}
