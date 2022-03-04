import java.io.File;
import java.util.List;

public class Util {

    public static void showHelp() {
        System.out.println("""
                Welcome to the help terminal commands !\s
                BEWARE !!! You need to write the pathfile in the input\s

                Here you can discover all commands to use :\s

                -help             show all commands
                pathfile.txt      the pathfile to the file to use
                -create           returns a file 'result.txt that contains the results even unknown ones'
                \s
                Thank you for your help we hope that we help you !!""");
    }

    public static void menu(Parser parser, String[] args) throws Exception {
        boolean createFile = false;

        List<File> files = null;
        if (args.length <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {
                case "-help":
                    showHelp();
                    break;
                case "-create":
                    createFile = true;
                    break;
                default:
                    if (args[i].endsWith(".txt")) {
                        files.add(new File(args[i]));
                    } else {
                        throw new IllegalArgumentException();
                    }
            }
        }
        for (File file : files) {
            parser.parseFile(file, createFile);
        }
    }
}
