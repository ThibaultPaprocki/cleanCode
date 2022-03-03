import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        Util.menu(parser,args);
    }
}