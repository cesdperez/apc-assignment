import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void it_solves_example() {
        provideInput("6\n" +
                "1 2 4 10 5 6\n" +
                "11");

        Main.main(new String[0]);

        String result = getOutput();
        assertEquals("Nr solutions=4\n" +
                "1, 4, 6\n" +
                "1, 10\n" +
                "2, 4, 5\n" +
                "5, 6\n" +
                "Waste=0\n", result);
    }

    @Test
    public void it_computes_with_no_waste() {
        provideInput("10\n" +
                "1 2 4 10 5 6 3 8 23 25\n" +
                "11");

        Main.main(new String[0]);

        String result = getOutput();
        assertEquals("Nr solutions=8\n" +
                "1, 2, 5, 3\n" +
                "1, 2, 8\n" +
                "1, 4, 6\n" +
                "1, 10\n" +
                "2, 4, 5\n" +
                "2, 6, 3\n" +
                "5, 6\n" +
                "3, 8\n" +
                "Waste=0\n", result);
    }

    @Test
    public void it_computes_with_waste() {
        provideInput("4\n" +
                "4 10 5 6\n" +
                "17");

        Main.main(new String[0]);

        String result = getOutput();
        assertEquals("Nr solutions=1\n" +
                "4, 10, 5\n" +
                "Waste=2\n", result);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

}
