import controllers.MainController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;


public class Main {

    private static void initLaboratoryTest(String defaultLabPath) {

        // Remove all file inside labFolder
        File tempFile = new File(defaultLabPath);
        if(tempFile.isDirectory()) {
            try {
                Files.walk(Paths.get(defaultLabPath)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            }
            catch ( IOException ex) {
                ex.printStackTrace();
            }
        }
        else
            tempFile.mkdirs();
    }

    private static void printReportFile(String defaultPath, MainController mc){

        File reportFile = new File(defaultPath + File.separator + "reportConfig.txt");


        try{
            FileOutputStream fos = new FileOutputStream(reportFile);
            PrintStream ps = new PrintStream(fos);

            //Network
            String temp = mc.getNetworkConfig();
            ps.println(temp);
            ps.println();
            ps.println("lab.conf");

            //Laboratory
            List<String> stringList = Files.readAllLines(Paths.get(defaultPath + File.separator + "labFolder" + File.separator + "lab.conf"));
            for (String s:stringList){
                ps.println("\t" + s);
            }

            System.out.println("reportConfig.txt created at path: \"" + reportFile.getAbsolutePath() + "\"");

            ps.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }//printReportFile()

    public static void main(String[] args) {

        // Static data
        String defaultLabPath = "lab-generator/labFolder";
        String defaultPath = "lab-generator";

        // Init laboratory
        initLaboratoryTest(defaultLabPath);

        // Create new File Controller and request all user data
        MainController fc = new MainController();
        fc.init(defaultLabPath);

        // Print report file
        printReportFile(defaultPath, fc);
    }
}
