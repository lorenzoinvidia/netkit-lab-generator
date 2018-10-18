import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Static data for the moment
        int numberOfPC = 0; // Number of network PC
        int numberOfRouter = 0; // Number of network router
        String defaultLabPath = "netkit-lab-generator/labTestFolder"; // Default lab path for testing
        List<String> nodes = new ArrayList<>(); // List of nodes ( Will be an array of network nodes! )
        List<File> nodesPath = new ArrayList<>(); // List of nodes path

        // Object reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            /* ____________Add network nodes____________ */

            // Request number of PC and print out for debug
            System.out.print("Number of PC in the network: ");
            numberOfPC = Integer.parseInt(reader.readLine());
            System.out.println("Number of PC: " + numberOfPC);

            // Request number of router and print out for debug
            System.out.print("Number of router in the network: ");
            numberOfRouter = Integer.parseInt(reader.readLine());
            System.out.println("Number of router: " + numberOfRouter);

            // Request PC names
            for (int i = 0; i < numberOfPC; i++) {
                System.out.print("Insert PC " + (i+1) + " name: ");
                nodes.add(reader.readLine());
            }

            // Request router names
            for (int i = 0; i < numberOfRouter; i++) {
                System.out.print("Insert router " + (i+1) + " name: ");
                nodes.add(reader.readLine());
            }

            // DEBUG: print nodes name
            System.out.print("Final network nodes: ");
            nodes.stream().reduce( (a, b) -> a + ", " + b).ifPresent(System.out::println);

            /* __________Create directory (and sub directories)__________ */

            for (String node : nodes) {
                File actualPath = new File(defaultLabPath + File.separator + node + File.separator + "etc" + File.separator + "network");
                nodesPath.add(actualPath);
                if(actualPath.isFile())
                    System.out.println("Bad path! (the actual path is a file)");
                else if(actualPath.isDirectory())
                    System.out.println( node + " already exist at path: \"" + actualPath.getAbsolutePath() + "\"");
                else {
                    if (actualPath.mkdirs()) {
                        System.out.println(node + " created at path: \"" + actualPath.getAbsolutePath() + "\"");

                        // create interfaces file

                        File interfaceFile = new File(actualPath.getAbsolutePath() + File.separator + "interface");
                        if(interfaceFile.createNewFile())
                            System.out.println( "Interface file of " + node + " created at path: \"" + interfaceFile.getAbsolutePath() + "\"");
                        else
                            System.out.println( "Error on creating interface file of " + node + "(Exception to be fixed!)" );
                    }
                    else
                        System.out.println( "Error on creating " + node + "(Exception to be fixed!)" );
                }
            }

            /* __________Create lab.conf file__________ */

            File filePath = new File(defaultLabPath + File.separator + "lab.conf");
            if(filePath.exists())
                System.out.println("lab.conf file already exist at path: \"" + filePath.getAbsolutePath() + "\"");
            else {
                if(filePath.createNewFile())
                    System.out.println("lab.conf created at path: \"" + filePath.getAbsolutePath() + "\"");
                else
                    System.out.println( "Error on creating lab.conf (Exception to be fixed!)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }






    }
}
