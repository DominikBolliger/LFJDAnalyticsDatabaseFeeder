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
    private static Document document;

    public DataBehaviour(String name, int id, List<Integer> multiplicators) {
        this.name = name;
        this.id = id;
        this.multiplicators = multiplicators;
        behaviourList.add(this);
    }

    public static void getXmlFile(){
        File file = new File("C:\\Program Files\\LFJDAnalyticsDatabaseFeeder\\behaviours.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
            document.normalize();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createBehaviourFromXml() {
        String name;
        int id;
        List<Integer> multiplicatorsList;

        NodeList behaviourNodes = document.getElementsByTagName("behaviour");
        Node current;
        for (int i = 0; i < behaviourNodes.getLength(); i++) {
            current = behaviourNodes.item(i);
            name = "";
            id = 0;
            multiplicatorsList = new ArrayList<>();
            if (current.getNodeType() == Node.ELEMENT_NODE) {
                NodeList behaviourNodeChilds = current.getChildNodes();
                Node currentChild;
                for (int j = 0; j < behaviourNodeChilds.getLength(); j++) {
                    currentChild = behaviourNodeChilds.item(j);
                    if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                        if (currentChild.getNodeName() == "id") {
                            id = Integer.parseInt(currentChild.getTextContent());
                        } else if (currentChild.getNodeName() == "name") {
                            name = currentChild.getTextContent();
                        } else if (currentChild.getNodeName() == "multiplicators") {
                            multiplicatorsList.add(Integer.valueOf(currentChild.getTextContent()));
                        }
                    }
                }
                new DataBehaviour(name, id, multiplicatorsList);
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
