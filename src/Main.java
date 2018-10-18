import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import controllers.MainController;

public class Main {

    public static void main(String[] args) {

        // Static data for the moment
        String defaultLabPath = "netkit-lab-generator/labTestFolder"; // Default lab path for testing


        // Remove all file inside labTestFolder
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

        // Create new File Controller and request all user data
        MainController fc = new MainController();
        fc.init(defaultLabPath);
    }
}
