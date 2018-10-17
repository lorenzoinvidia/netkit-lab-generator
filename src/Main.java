import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;



public class Main {

    public static void main(String[] args) {


//        /* __________(Example) Create directories and sub directories__________*/
//        File file = new File("dir/subD/subSubD");
//        if (!file.exists()) {
//            if (file.mkdirs()) {
//                System.out.println("OK directories created");
//            }else {
//                System.out.println("HEY directories cannot be created!");
//            }
//        }else {
//            System.out.println("HEY directories already existed!");
//        }





        int numOfNodes=0;       // Number of network nodes
        String[] nodes;         // Array of nodes ( Will be an array of node objects )
        File[] arrayOfPaths;    // Array of nodes paths

        // Object reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));




        try {

            /* ____________Add network nodes____________ */

            // Num of nodes
            System.out.println("Number of nodes: ");
            String num = reader.readLine();
            numOfNodes = Integer.parseInt(num);
            System.out.println("Num of nodes " + numOfNodes);


            nodes = new String[numOfNodes];
            arrayOfPaths = new File[numOfNodes];


            // Insert nodes
            for (int i=0; i<numOfNodes; i++){
                System.out.println("Node" + (i+1) + " name: ");
                nodes[i] = reader.readLine();
            }


            // DEBUG: print nodes name
            for (int i=0; i<nodes.length; i++){
                System.out.println(nodes[i]);
            }





            /* __________Create directory (and sub directories)__________ */

            int doNothingForNow = 0; //arrayOfPaths.length

            for (int i=0; i<doNothingForNow; i++){
                arrayOfPaths[i] = new File("NODE_PATH_GOES_HERE");
                if (!arrayOfPaths[i].exists()) {
                    if (arrayOfPaths[i].mkdirs()) {
                        System.out.println("ok");
                    }else {
                        System.out.println("not ok");
                    }
                }
            }//for




        } catch (Exception e) {
            System.err.println("Exceptions to be fixed!");
        }






    }
}
