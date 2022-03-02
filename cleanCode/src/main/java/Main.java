import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        File file = new File("C:\\Users\\lucas\\IdeaProjects\\cleanCode\\cleanCode\\src\\test\\ressources\\blbl");
        parser.parseFile(file);
    }
}