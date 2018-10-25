package controllers;

import structures.Interface;
import structures.PC;
import structures.Router;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DomainController {

    private String path;
    private List<PC> pcs;
    private List<Router> routers;
    private Map<Interface, Integer> collisionDomainsRouter;

    public DomainController(String path, List<PC> pc, List<Router> router) {
        this.path = path;
        this.pcs = pc;
        this.routers = router;
        this.collisionDomainsRouter = new HashMap<>();
    }

    public void writeLabConf() {
        File labConfFile = new File(path + File.separator + "lab.conf");
        try {
            FileOutputStream fos = new FileOutputStream(labConfFile);
            PrintStream ps = new PrintStream(fos);
            for(Router router : routers)
                writeLabConfRouter(ps, router);
            for(PC pc : pcs)
                writeLabConfPC(ps, pc);
            ps.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeLabConfRouter(PrintStream ps, Router router) {
        Interface[] ifaces = router.getInterfaces();
        int numberDomain = new TreeSet<>(collisionDomainsRouter.values()).size();
        for(int i = 0; i < ifaces.length; i++) {
            if(!(ifaces[i].getGateway().equals(""))) {
                // search the collision domain of the gateway
                String actualGateway = ifaces[i].getGateway();
                List<Interface> otherInterface = collisionDomainsRouter.keySet().stream().filter(iface -> iface.getAddress().equals(actualGateway))
                                                .collect(Collectors.toList());
                if(!(otherInterface.isEmpty())) {
                    Integer domainOtherInterface = collisionDomainsRouter.get(otherInterface.get(0));
                    collisionDomainsRouter.put(ifaces[i], (domainOtherInterface));
                    ps.println(router.getName()+"["+(i)+"]="+(domainOtherInterface));
                    ps.println();
                    return;
                }
            }
            // else create the new domain
            collisionDomainsRouter.put(ifaces[i], (numberDomain));
            ps.println(router.getName()+"["+(i)+"]="+(numberDomain));
            numberDomain++;
        }
        ps.println();
    }

    private void writeLabConfPC(PrintStream ps, PC pc) {
        Interface iface = pc.getInterface();
        for (Map.Entry<Interface, Integer> entry : collisionDomainsRouter.entrySet()) {
            if (iface.getGateway().equals(entry.getKey().getAddress())) {
                ps.println(pc.getName() + "[0]=" + entry.getValue());
                return;
            }
        }
    }
}
