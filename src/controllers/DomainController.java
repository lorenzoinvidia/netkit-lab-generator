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
        for(int i = 0; i < ifaces.length; i++) {
            collisionDomainsRouter.put(ifaces[i], (i+1));
            ps.println(router.getName()+"["+(i)+"]="+(i+1));
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
