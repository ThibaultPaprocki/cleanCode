import java.io.*;
import java.util.Arrays;
import java.util.Map;

public class Parser {
    private PrintWriter writer;
    final private Map<String,Integer> codes = Map.of("020101121",0,
            "000010010",1,
            "020021120",2,
            "020021021",3,
            "000121001",4,
            "020120021",5,
            "020120121",6,
            "020001001",7,
            "020121121",8,
            "020121021",9);


    public void parseFile(File file) throws Exception {

        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String[] numbersCode = new String[100];
        Arrays.fill(numbersCode, "");
        String[] resultCode = new String[9];
        Arrays.fill(resultCode, "");
        String line;
        int countLine = 0;
        int characWidth = 0;
        int[] number = new int[9];
        String character;
        String checkSumResult;
//        try {
//            File myFile = new File("src/test/ressourcesNewFile.txt");
//
//            if (myFile.createNewFile()){
//                System.out.println("Le fichier est créé.");
//            }else{
//                System.out.println("Le fichier existe déjà.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //  writer = new PrintWriter("ressourcesNewFile.txt", "UTF-8");

        while((line = br.readLine()) != null){

            numbersCode[countLine]=line;
            numbersCode[countLine]=addMissingEscape(numbersCode[countLine]);
            countLine++;
            if (countLine%3==0){
                br.readLine();
            }

        }

        for (int i = 0; i < countLine; i++) {
            for (int j = 0; j < numbersCode[i].length(); j++) {
                if(j%3 == 0 && j!=0){
                    characWidth++;
                }
                character=getCharacterCode(numbersCode[i].charAt(j));
                resultCode[characWidth]+=character;
            }
            characWidth = 0;

            if(i%3 == 2){
                for (int j = 0; j < resultCode.length; j++) {
                    number[j] = getCodeValue(resultCode[j]);
                }
                checkSumResult = checkChecksum(number);
                displayResults(number,checkSumResult);
                Arrays.fill(resultCode, "");
                Arrays.fill(number,0);
            }
        }
        br.close();
    }


    private String addMissingEscape(String s) {
        if(s.length()!=27){
            for (int i = s.length(); i < 27 ; i++) {
                s+=" ";
            }
        }
        return s;
    }

    public int getCodeValue(String number) {
        int errorCode = 42;
        for (Map.Entry<String, Integer> entry : codes.entrySet()) {
            if (entry.getKey().equals(String.valueOf(number))) {
                return entry.getValue();
            }
        }
        return errorCode;
    }

    public void displayResults(int[] number, String checksumResult) throws FileNotFoundException, UnsupportedEncodingException {

        String errorMsg = "";
        for (int i = 0; i < number.length; i++) {
            if(number[i] == 42){
                System.out.print("?");
                //writer.write("?");
                errorMsg = " ILL";
            }
            else{
                // writer.write(number[i]);
                System.out.print(number[i]);
            }
        }

        if(errorMsg == " ILL"){
            //writer.print(errorMsg);
            System.out.print(errorMsg);
        }
        else{
            //writer.print(checksumResult);
            System.out.print(checksumResult);
        }
        //writer.println();
        System.out.println();

    }

    public String getCharacterCode(char c){
        switch(c){
            case ' ': return "0";
            case '|': return "1";
            case '_': return "2";
            default: return "";
        }
    }

    private String checkChecksum(int[] number){
        int totalChecksum = 0;
        int position = 1;

        for(int i = number.length; i > 0; i--){
            totalChecksum += (i*position);
            position ++;
        }

        if(totalChecksum % 11 != 0){ // SUSPICIEUX LUCAS  totalChecksum % 11 != 0 ??
            return " ERR";
        }
        return "";
    }
}