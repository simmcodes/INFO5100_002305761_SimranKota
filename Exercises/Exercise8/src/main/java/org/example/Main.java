package org.example;
import com.google.gson.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class Main {
    public static void main(String[] args) throws Exception {

        parseAndModifyXML();
        
        parseAndModifyJSON();
    }

    public static void parseAndModifyXML() throws Exception {
        File xmlFile = new File("src/input.xml");

        // Parse XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();

        System.out.println("XML Parsed:");
        NodeList books = document.getElementsByTagName("Book");
        for (int i = 0; i < books.getLength(); i++) {
            Node book = books.item(i);
            if (book.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) book;
                System.out.println("Title: " + element.getElementsByTagName("title").item(0).getTextContent());
                System.out.println("Year: " + element.getElementsByTagName("publishedYear").item(0).getTextContent());
                System.out.println("Pages: " + element.getElementsByTagName("numberOfPages").item(0).getTextContent());

                NodeList authors = element.getElementsByTagName("author");
                System.out.print("Authors: ");
                for (int j = 0; j < authors.getLength(); j++) {
                    System.out.print(authors.item(j).getTextContent() + (j < authors.getLength() - 1 ? ", " : ""));
                }
                System.out.println("\n");
            }
        }

        // Add a new book programmatically
        Element newBook = document.createElement("Book");

        Element title = document.createElement("title");
        title.appendChild(document.createTextNode("Programming in Python"));
        newBook.appendChild(title);

        Element year = document.createElement("publishedYear");
        year.appendChild(document.createTextNode("2022"));
        newBook.appendChild(year);

        Element pages = document.createElement("numberOfPages");
        pages.appendChild(document.createTextNode("350"));
        newBook.appendChild(pages);

        Element authors = document.createElement("authors");
        Element author = document.createElement("author");
        author.appendChild(document.createTextNode("Derek White"));
        authors.appendChild(author);
        newBook.appendChild(authors);

        document.getDocumentElement().appendChild(newBook);

        //Output the modified XML
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }

    public static void parseAndModifyJSON() throws IOException {
        //Read JSON file
        FileReader reader = new FileReader("src/input.json");
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        JsonArray books = jsonObject.getAsJsonArray("BookShelf");

        System.out.println("\nJSON Parsed:");
        for (JsonElement book : books) {
            JsonObject obj = book.getAsJsonObject();
            System.out.println("Title: " + obj.get("title").getAsString());
            System.out.println("Year: " + obj.get("publishedYear").getAsInt());
            System.out.println("Pages: " + obj.get("numberOfPages").getAsInt());
            System.out.println("Authors: " + obj.get("authors").getAsJsonArray());
            System.out.println();
        }

        //Add a new book programmatically
        JsonObject newBook = new JsonObject();
        newBook.addProperty("title", "Programming in Python");
        newBook.addProperty("publishedYear", 2022);
        newBook.addProperty("numberOfPages", 350);

        JsonArray authors = new JsonArray();
        authors.add("Derek White");
        newBook.add("authors", authors);

        books.add(newBook);

        //Print updated JSON
        System.out.println("Modified JSON:");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jsonObject));
    }
}
