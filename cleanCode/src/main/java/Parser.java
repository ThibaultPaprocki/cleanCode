import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Parser {

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
        Arrays.fill(resultCode, "");
        String character;
        String checksumResult;

        while((line = br.readLine()) != null){
            numbersCode[countLine]=line;
            numbersCode[countLine]=addMissingEscape(numbersCode[countLine]);
            countLine++;
            if(countLine%4 == 0) {
                countLine=0;
            }
        }


        for (int i = 0; i < countLine; i++) {
            for (int j = 0; j < numbersCode[i].length(); j++) {
                if(j == 3 || j == 6 || j == 9 || j == 12 || j == 15 || j == 18 || j == 21 || j == 24 || j == 27 ){
                    characWidth++;
                }
                character=getCharacterCode(numbersCode[i].charAt(j));
                resultCode[characWidth]+=character;
            }
            characWidth = 0;

            if(i==2 || i==5){
                for (int j = 0; j < resultCode.length; j++) {
                    number[j] = getCodeValue(resultCode[j]);
                }

                checksumResult = checkChecksum(number);

                displayResults(number,checksumResult);
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
            if (entry.getKey().equals(String.valueOf(number))) {  // ici c'est la key
                return entry.getValue();
            }
        }
        return errorCode;
    }

    public void displayResults(int[] number, String checksumResult){
        String errorMsg = "";

        for (int i = 0; i < number.length; i++) {
            if(number[i] == 42){
                System.out.print("?");
                errorMsg = " ILL";
            }
            else System.out.print(number[i]);
        }

        if(errorMsg == " ILL"){
            System.out.print(errorMsg);
        }
        else{
            System.out.print(checksumResult);
        }

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
        int position = 0;

        for(int i = number.length; i > 0; i--){
            totalChecksum += (i*position);
            position ++;
        }

        if(totalChecksum % 11 != 0){
            return " ERR";
        }

        return "";
    }
}