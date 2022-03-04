import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public void parseFile(File file, boolean createFiles) throws Exception {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] numbersCode = new String[100];
        Arrays.fill(numbersCode, "");
        String[] resultCode = new String[9];
        Arrays.fill(resultCode, "");
        String line;
        int countLine = 0;
        int characWidth = 0;
        List<Integer> numbers = new ArrayList<>();
        String character;
        String checkSumResult;

        while((line = bufferedReader.readLine()) != null){

            numbersCode[countLine]=line;
            numbersCode[countLine]=addMissingEscape(numbersCode[countLine]);
            countLine++;
            if (countLine%3==0){
                bufferedReader.readLine();
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
                    numbers.add(j,getCodeValue(resultCode[j]));
                }
                checkSumResult = checkChecksum(numbers);

                if(createFiles){
                    buildResults(numbers, checkSumResult);
                }
                else{
                    displayResults(numbers);
                }

                Arrays.fill(resultCode, "");
                numbers.clear();
            }
        }
        bufferedReader.close();
    }

    private void displayResults(List<Integer> numbers) {
        for(int i = 0; i < numbers.size(); i++){
            System.out.print(numbers.get(i));
        }
        System.out.println();
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

    public void buildResults(List<Integer> numbers, String checksum) throws IOException {

        String errorMsg = "";
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < numbers.size(); i++) {
            if(numbers.get(i) == 42){
                code.append("?");
                errorMsg = "ILL";
            }
            else{
                code.append(numbers.get(i).toString());
            }
        }

        if("".equals(errorMsg)){
            errorMsg = checksum;
        }

        insertInFile(code.toString(), errorMsg);
    }

    public String getCharacterCode(char c){
        switch(c){
            case ' ': return "0";
            case '|': return "1";
            case '_': return "2";
            default: return "";
        }
    }

    public String checkChecksum(List<Integer> numbers){
        int totalChecksum = 0;
        int position = numbers.size();

        for(int i = 0; i < numbers.size() ; i++){
            totalChecksum += (numbers.get(i) * position);
            position --;
        }

        if(totalChecksum % 11 != 0){
            return "ERR";
        }
        return "";
    }

    public void insertInFile(String code, String errorCode) throws IOException {
        try{
            String filePath = defineFileToPutData(errorCode);
            FileOutputStream f = new FileOutputStream(filePath, true);

            String codeNumberInString = formatCodeNumber(code);

            String lineToAppend = codeNumberInString;
            lineToAppend += " ";
            lineToAppend += errorCode;
            lineToAppend += "\n";

            byte[] byteArr = lineToAppend.getBytes();

            f.write(byteArr);

            f.close();

        }catch (IOException e) {
            throw new IOException(e);
        }
    }

    public String defineFileToPutData(String errorCode){
        String filePath;
        switch (errorCode){
            case "ERR":
                filePath = "src/test/ressources/Errored.txt";
                break;
            case "ILL":
                filePath = "src/test/ressources/Unknown.txt";
                break;
            default:
                filePath = "src/test/ressources/Authorized.txt";
        }

        return filePath;
    }

    public String formatCodeNumber(String numbers){
        String listConvertInString = String.join("", numbers);
        StringBuilder codeFormated = new StringBuilder();

        for(int i = 0; i < listConvertInString.length(); i++){
            if(listConvertInString.charAt(i) != ',' && listConvertInString.charAt(i) != ' ' && listConvertInString.charAt(i) != '[' && listConvertInString.charAt(i) != ']'){
                codeFormated.append(listConvertInString.charAt(i));
            }
        }
        return codeFormated.toString();
    }
}
