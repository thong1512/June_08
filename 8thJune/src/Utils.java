import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Utils {
    public static String convertObject2XMLString(List<Student> students){
        String xmlData = null;
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //create element
            Element root = doc.createElement("students");
            for (Student std : students){
                Element student = doc.createElement("student");
                Element id = doc.createElement("id");
                Element name = doc.createElement("name");
                Element grade = doc.createElement("grade");

                //add value
                id.appendChild(doc.createTextNode(String.valueOf(std.getId())));
                name.appendChild(doc.createTextNode(std.getName()));
                grade.appendChild(doc.createTextNode(String.valueOf(std.getGrade())));

                //create tree
                root.appendChild(student);
                student.appendChild(id);
                student.appendChild(name);
                student.appendChild(grade);
            }

            //doc to XML String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            xmlData = writer.getBuffer().toString();
        } catch (Exception e){
            e.printStackTrace();
        }

        return xmlData;
    }

    public static String convertObject2XMLString(Student std){
        String xmlData = null;
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //create element
            Element root = doc.createElement("students");
            Element student = doc.createElement("student");
            Element id = doc.createElement("id");
            Element name = doc.createElement("name");
            Element grade = doc.createElement("grade");

            //add value
            id.appendChild(doc.createTextNode(String.valueOf(std.getId())));
            name.appendChild(doc.createTextNode(std.getName()));
            grade.appendChild(doc.createTextNode(String.valueOf(std.getGrade())));

            //create tree
            doc.appendChild(root);
            root.appendChild(student);
            student.appendChild(id);
            student.appendChild(name);
            student.appendChild(grade);

            //doc to XML String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            xmlData = writer.getBuffer().toString();
        } catch (Exception e){
            e.printStackTrace();
        }

        return xmlData;
    }

    public static List<Student> convertXMLString2Object(String xmlData){
        List<Student> studentsList = new ArrayList<Student>();
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));

            Element root = (Element) doc.getDocumentElement();
            NodeList students = root.getChildNodes();

            for(int i=0; i < students.getLength(); i++){
                Element student = (Element) students.item(i);

                Student std = new Student(
                    Integer.parseInt(student.getElementsByTagName("id").item(0).getTextContent()),
                    student.getElementsByTagName("name").item(0).getTextContent(),
                    Double.parseDouble(student.getElementsByTagName("grade").item(0).getTextContent())
                );
                studentsList.add(std);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return studentsList;
    }
    
    
}
