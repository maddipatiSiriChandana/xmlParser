import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

    public static void main(String[] args) {
        try {
            // Parse input1.xml and produce output1.txt
            List<String> output1 = parseInput1("input1.xml");
            writeOutputToFile(output1, "output1.txt");

            // Parse input2.xml and produce output2.txt
            List<String> output2 = parseInput2("input2.xml");
            writeOutputToFile(output2, "output2.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> parseInput1(String inputFileName) throws ParserConfigurationException, SAXException, IOException {
        List<String> output = new ArrayList<>();
        File inputFile = new File(inputFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("CONNECTOR");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String fromInstance = element.getAttribute("FROMINSTANCE");
                String fromField = element.getAttribute("FROMFIELD");
                String toInstance = element.getAttribute("TOINSTANCE");
                String toField = element.getAttribute("TOFIELD");
                output.add("connector:fromInstance=" + fromInstance + ", fromField=" + fromField + ", toInstance=" + toInstance + ", toField=" + toField);
            }
        }
        return output;
    }

    public static List<String> parseInput2(String inputFileName) throws ParserConfigurationException, SAXException, IOException {
        List<String> output = new ArrayList<>();
        File inputFile = new File(inputFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("CONNECTOR");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String fromInstance = element.getAttribute("FROMINSTANCE");
                String fromField = element.getAttribute("FROMFIELD");
                String toInstance = element.getAttribute("TOINSTANCE");
                String toField = element.getAttribute("TOFIELD");
                output.add("connector:fromInstance=" + fromInstance + ", fromField=" + fromField + ", toInstance=" + toInstance + ", toField=" + toField);
            }
        }
        nodeList = doc.getElementsByTagName("TRANSFORMATION");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("NAME");
                if (name.equals("trans_ROUTER_EMPLOYEES_BY_JOB_ID")) {
                    NodeList fieldList = element.getElementsByTagName("FIELD");
                    for (int j = 0; j < fieldList.getLength(); j++) {
                        Node fieldNode = fieldList.item(j);
                        if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element fieldElement = (Element) fieldNode;
                            String fieldName = fieldElement.getAttribute("NAME");
                            output.add("connector:fromInstance=SQ_EMPLOYEES, fromField=" + fieldName + ", toInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, toField=" + fieldName);
                        }
                    }
                } else if (name.equals("UC05_ROUTER_EMPLOYEES_MGR")) {
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=EMPLOYEE_ID1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, toField=EMPLOYEE_ID");
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=FIRST_NAME1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, told=FIRST_NAME");
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=LAST_NAME1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, toField=LAST_NAME");
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=EMAIL1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, toField=EMAIL");
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=PHONE_NUMBER1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, toField=PHONE_NUMBER");
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=HIRE_DATE1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, toField=HIRE_DATE");
                    output.add("connector:fromInstance=trans_ROUTER_EMPLOYEES_BY_JOB_ID, fromField=JOB_ID1, toInstance=UC05_ROUTER_EMPLOYEES_MGR, toField=JOB_ID");
                }
            }
        }
        return output;
    }

    public static void writeOutputToFile(List<String> output, String outputFileName) throws IOException, TransformerException {
        FileWriter writer = new FileWriter(outputFileName);
        for (String line : output) {
            writer.write(line + "\n");
        }
        writer.close();
    }
}