import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ParserTest {

    private File file = new File("src/test/ressources/inputTest");
    private Parser parser = new Parser();

    public ParserTest() {

    }

    @Test
    public void fileEmpty() {
        Assertions.assertThat(file).isEmpty();
    }

    @Test
    public void checkSumTest() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        String checkSum = parser.checkChecksum(numbers);
        Assertions.assertThat(checkSum).isEqualTo("ERR");
    }

}
