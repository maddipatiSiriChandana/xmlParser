//Author:Siri Chandana
/*This program uses the DOM parser to parse the input XML files and extract the necessary information to produce the output text files. 
It uses a list of strings to store the output for each input file. 
It uses the parseInput1 method to parse input1.xml and the parseInput2 method to parse input2.xml. 
The writeOutputToFile method is used to write the output to a file.
It extracts the necessary information from the input XML files using the getElementsByTagName and getAttribute methods of the Element interface. 
It then constructs the output strings by concatenating the extracted information.
To run the program, simply compile it and run the main method. 
It will generate the output files output1.txt and output2.txt in the same directory as the input files.
*/
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
            String inputFileName1 = "input1.xml";
            String inputFileName2 = "input2.xml";

            // Parse input1.xml and input2.xml
            List<String> output1 = parseInput1(inputFileName1);
            List<String> output1Instances = parseInput1Instances(inputFileName1);
            List<String> output2 = parseInput2(inputFileName2);
            List<String> output2InstancesAndGroups = parseInput2InstancesAndGroups(inputFileName2);
		
	    // Sort the individual lists
	    output1.sort(String::compareTo);
            output1Instances.sort(String::compareTo);
            output2.sort(String::compareTo);
            output2InstancesAndGroups.sort(String::compareTo);

            // Merge the outputs into a single list
            List<String> combinedOutput1 = new ArrayList<>(output1);
            combinedOutput1.addAll(output1Instances);
            List<String> combinedOutput2 = new ArrayList<>(output2);
            combinedOutput2.addAll(output2InstancesAndGroups);
	
	    combinedOutput1.sort(String::compareTo);
	    combinedOutput2.sort(String::compareTo);

            // Write connector, instance, and group information to output files
            writeOutputToFile(combinedOutput1, "output1.txt");
            writeOutputToFile(combinedOutput2, "output2.txt");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
        NodeList transformationNodeList = doc.getElementsByTagName("TRANSFORMATION");
        for (int i = 0; i < transformationNodeList.getLength(); i++) {
            Node node = transformationNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("NAME");
                if (name.equals("trans_ROUTER_EMPLOYEES_BY_JOB_ID") || name.equals("UC05_ROUTER_EMPLOYEES_MGR")) {
                    NodeList fieldNodeList = element.getElementsByTagName("FIELD");
                    for (int j = 0; j < fieldNodeList.getLength(); j++) {
                        Node fieldNode = fieldNodeList.item(j);
                        if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element fieldElement = (Element) fieldNode;
                            String fromField = fieldElement.getAttribute("NAME");
                            String toField = fieldElement.getAttribute("PORTTYPE");
                            output.add("fromField=" + fromField + ", toField=" + toField);
                        }
                    }
                }
            }
        }
        return output;
    }

    public static List<String> parseInput1Instances(String inputFileName) throws ParserConfigurationException, SAXException, IOException {
        List<String> output = new ArrayList<>();
        File inputFile = new File(inputFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("INSTANCE");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("NAME");
                String type = element.getAttribute("TYPE");
                output.add("instance:Name=" + name + ", type=" + type);
            }
        }
        return output;
    }

    public static List<String> parseInput2InstancesAndGroups(String inputFileName) throws ParserConfigurationException, SAXException, IOException {
        List<String> output = new ArrayList<>();
        File inputFile = new File(inputFileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList instanceNodeList = doc.getElementsByTagName("INSTANCE");
        for (int i = 0; i < instanceNodeList.getLength(); i++) {
            Node node = instanceNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("NAME");
                String type = element.getAttribute("TYPE");
                output.add("instance:Name=" + name + ", type=" + type);
            }
        }
        NodeList groupNodeList = doc.getElementsByTagName("GROUP");
        for (int i = 0; i < groupNodeList.getLength(); i++) {
            Node node = groupNodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("NAME");
                String expression = element.getAttribute("EXPRESSION");
                String order = element.getAttribute("ORDER");
                String type = element.getAttribute("TYPE");
                output.add("group:name=" + name + ", expression=" + expression + ", order=" + order + ", type=" + type);
            }
        }
        return output;
    }

    public static void writeOutputToFile(List<String> output, String outputFileName) throws IOException {
        FileWriter fileWriter = new FileWriter(outputFileName);
        for (String line : output) {
            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }

    }
