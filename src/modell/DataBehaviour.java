package modell;

import application.LFJDAnalyticsDatabaseFeederApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBehaviour {
    private String name;
    private int id;
    private List<Integer> multiplicators;
    private static List<DataBehaviour> behaviourList = new ArrayList<>();

    public DataBehaviour(String name, int id, List<Integer> multiplicators){
        this.name = name;
        this.id = id;
        this.multiplicators = multiplicators;
        behaviourList.add(this);
    }

    public static void createBehaviourFromXml() {
        File file = new File("C:\\Program Files\\LFJDAnalyticsDatabaseFeeder\\behaviours.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        NodeList nodes = document.getElementsByTagName("*");

        String name = "";
        int id = 0;
        List<Integer> multiplicators;

        for (int i = 0; i < nodes.getLength(); i++) {
            multiplicators = new ArrayList<>();
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println(element.getElementsByTagName(""));
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getMultiplicators() {
        return multiplicators;
    }

    public static List<DataBehaviour> getBehaviourList() {
        return behaviourList;
    }
}
