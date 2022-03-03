import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ParserTest {

    private File file = new File("src/test/ressources/blbl.txt");

    public ParserTest(){

    }

    @Test
    public void fileEmpty(){
        Assertions.assertThat(file).isEmpty();
    }

}
