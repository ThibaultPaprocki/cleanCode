import java.io.File;

public class Util {

    public static void showHelp(){
        System.out.println("""
                Welcome to the help terminal commands !BEWARE !!! You need to write the pathfile in the input\s

                Here you can discover all commands to use :\s

                -help             show all commands
                pathfile.txt      the pathfile to the file to use
                -classificate     returns three files / Authorized / Errored / Unknown with all the checksums
                -result           returns a file 'result.txt that contains the results even unknown ones'
                -checksum         returns a file 'checksum.txt' that contains the results with a checksum
                \s
                Thank you for your help we hope that we help you !!""");
    }

    public static void menu(Parser parser, String[] args) throws Exception {
        File file = new File("cleanCode/src/test/ressources/blbl.txt");
        for(String arg : args){
            if(arg.endsWith(".txt")){
                parser.parseFile(new File(arg));
            }
            if(arg.equals("-help")){
                Util.showHelp();
            }
            if(arg.equals("-result")){
                parser.parseFile(file); //rajouter option result
            }
            if(arg.equals("-classificate")){
                parser.parseFile(file);  //rajouter option classificate
            }
            if(arg.equals("-checksum")){
                parser.parseFile(file); //rajouter option checksum
            }
        }

    }
}
