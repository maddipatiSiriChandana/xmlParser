import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.transform.TransformerException;

class XMLParserTest {

    @Test
    void testP1arseInput1() throws Exception {
	// Your expected value as a single string
        String expectedOutput = "connector:fromInstance=EMPLOYEES, fromField=EMPLOYEE_ID, toInstance=SQ_EMPLOYEES, toField=EMPLOYEE_ID";
	// Read the content of the output1.txt file line by line and check if any line matches the expected value
        boolean isMatchFound = false;
        for (String line : Files.readAllLines(Paths.get("output1.txt"))) {
            if (line.equals(expectedOutput)) {
                isMatchFound = true;
                break;
            }
        }

        // Assert that a match was found
        assertEquals(true, isMatchFound);
    }

    @Test
    void testParseInput2() throws Exception {
         String expectedOutput = "connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=EMPLOYEE_ID5, toInstance=UC05_ROUTER_EMPLOYEES_ACCOUNT, toField=EMPLOYEE_ID";
      // Read the content of the output2.txt file line by line and check if any line matches the expected value
        boolean isMatchFound = false;
        for (String line : Files.readAllLines(Paths.get("output2.txt"))) {
            if (line.equals(expectedOutput)) {
                isMatchFound = true;
                break;
            }
        }

        // Assert that a match was found
        assertEquals(true, isMatchFound);
    }

    @Test
    void testWriteOutputToFile() throws IOException, TransformerException {
        String outputFileName = "testOutput.txt";
        List<String> output = Arrays.asList("line 1", "line 2", "line 3");
        XMLParser.writeOutputToFile(output, outputFileName);

        assertTrue(Files.exists(Paths.get(outputFileName)));
        List<String> actualOutput = Files.readAllLines(Paths.get(outputFileName));
        assertEquals(output, actualOutput);

        Files.deleteIfExists(Paths.get(outputFileName));
    } 
}