import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parser {

    final private Map<String,Integer> code = Map.of("000010010",1,
                                                    "020021120",2,
                                                    "020021021",3,
                                                    "000121001",4,
                                                    "020120021",5,
                                                    "020120121",6,
                                                    "020001001",7,
                                                    "020121121",8,
                                                    "020121021",9);





    public void parseFile(File file) throws IOException {

        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        List<String> numbersCode = new ArrayList<>();

        String line = "";
        int countLine = 1;
        int characWidth = 0;

        while((line = br.readLine()) != null){
            System.out.print("aa");
            if(countLine==4) {
                displayNumber(getCodeValue(numbersCode));
                countLine = 1;
                characWidth=0;
            }
            else {

                for (int i = 0; i < 27; i++) {
                    System.out.print("bb");

                    if (i % 3 != 0 || characWidth == 0) {
                        characWidth++;
                        numbersCode.add("" + line.charAt(i));
                    }
                    else {
                        numbersCode.set(characWidth, numbersCode.get(characWidth) + "" + line.charAt(i));
                    }
                    System.out.print("cc");

                }
            }
            countLine++;
        }

        System.out.println(numbersCode);
        System.out.println(line);
        br.close();
    }

    public ArrayList<Integer> getCodeValue(List<String> numberCodes){
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String numberCode: numberCodes) {
            for (Map.Entry<String, Integer> entry : code.entrySet()) {
                if (entry.getValue().equals(numberCode)) {
                    numbers.add(entry.getValue());
                }
            }
        }
        return numbers;
    }

    public void displayNumber(ArrayList<Integer> number){
        for (int i = 0; i < number.size(); i++) {
            System.out.print(number.get(i));
        }
        System.out.println();
    }
}
