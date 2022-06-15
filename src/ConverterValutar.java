import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public interface ConverterValutar {

    public static Map<String, Double> getMap() {
        HashMap<String, Double> values = new HashMap<>();
        try
        {
            String adress = "https://www.bnm.md/ro/official_exchange_rates?get_xml=1&date=16.06.2022";

            File file = new File("F:\\XMLFile.xml");

            //an instance of factory that gives a document builder

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            //an instance of builder to parse the specified xml file

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(adress).openStream());
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Valute");

            // nodeList is not iterable, so we are using for loop

            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    String valuta = eElement.getChildNodes().item(3).getTextContent();
                    Double valoare = Double.parseDouble(eElement.getChildNodes().item(9).getTextContent().toString());
                    values.put(valuta, valoare);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return values;
    }
    double mdlToEuro();
    double mdlToUSD();
}
