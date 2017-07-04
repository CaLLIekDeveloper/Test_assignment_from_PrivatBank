/*
 * Авторство Паршина Александра
 * По всем вопросам писать на e-mail parshin_sashek@mail.ru
 */
package privatserver.classes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import privatserver.Server;

/**
 *
 * @author parsh
 */
public class XML
{
    //XML
    private static final String PATH = Paths.get("").toAbsolutePath().toString();

    static public void createXML(List<Deposit> temp)
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("deposits");
            document.appendChild(rootElement);

            for (int index = 0; index < temp.size(); index++)
            {
                Element Deposit = document.createElement("deposit");
                rootElement.appendChild(Deposit);

                Element name = document.createElement("Name");
                name.appendChild(document.createTextNode(temp.get(index).getNameBank()));
                Deposit.appendChild(name);

                Element country = document.createElement("Country");
                country.appendChild(document.createTextNode(temp.get(index).getCountry()));
                Deposit.appendChild(country);

                Element type = document.createElement("Type");
                type.appendChild(document.createTextNode(temp.get(index).getType()));
                Deposit.appendChild(type);

                Element Depositor = document.createElement("Depositor");
                Depositor.appendChild(document.createTextNode(temp.get(index).getDepositor()));
                Deposit.appendChild(Depositor);

                Element AccountId = document.createElement("AccountId");
                AccountId.appendChild(document.createTextNode(Long.toString(temp.get(index).getAccountId())));
                Deposit.appendChild(AccountId);

                Element AmountOnDeposit = document.createElement("AmountOnDeposit");
                AmountOnDeposit.appendChild(document.createTextNode(temp.get(index).getAmountOnDeposit().toString()));
                Deposit.appendChild(AmountOnDeposit);

                Element Profitability = document.createElement("Profitability");
                Profitability.appendChild(document.createTextNode(Double.toString(temp.get(index).getProfitability())));
                Deposit.appendChild(Profitability);

                Element time = document.createElement("Time");
                time.appendChild(document.createTextNode(Long.toString(temp.get(index).getTime())));
                Deposit.appendChild(time);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(PATH + "\\Profiles.xml"));
            
            transformer.transform(domSource, streamResult);
            Server._getPlog().append("Файл успешно создан");
        }
        catch (ParserConfigurationException | TransformerException pce)
        {
            Server._getPlog().append("Ошибка");
        }


    }
    
    public static void readXml()
    {
        try
        {
            Deposit.setAllSumDeposits(BigDecimal.ZERO);
            File file = new File(PATH + "\\Profiles.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();


            NodeList nodeList = doc.getElementsByTagName("deposit");
            for (int index = 0; index < nodeList.getLength(); index++)
            {
                Deposit b = new Deposit();
                Node node = nodeList.item(index);
                if (Node.ELEMENT_NODE == node.getNodeType())
                {
                    Element element = (Element) node;
                    b.setNameBank(element.getElementsByTagName("Name").item(0).getTextContent());
                    b.setCountry(element.getElementsByTagName("Country").item(0).getTextContent());
                    b.setType(element.getElementsByTagName("Type").item(0).getTextContent());
                    b.setDepositor(element.getElementsByTagName("Depositor").item(0).getTextContent());
                    b.setAccountId(Long.parseLong(element.getElementsByTagName("AccountId").item(0).getTextContent()));
                    b.setAmountOnDeposit(new BigDecimal(element.getElementsByTagName("AmountOnDeposit").item(0).getTextContent()));
                    b.setProfitability(Double.parseDouble(element.getElementsByTagName("Profitability").item(0).getTextContent()));
                    b.setTime(Long.parseLong(element.getElementsByTagName("Time").item(0).getTextContent()));
                    Deposit.getDeposits().add(b);
                    Deposit.setAllSumDeposits(Deposit.getAllSumDeposits().add(new BigDecimal(element
                            .getElementsByTagName("AmountOnDeposit").item(0)
                            .getTextContent())));
                }
            }
        }
        catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e)
        {
            Server._getPlog().append("Ошибка считывание базы\n");
            createXML(Deposit.getDeposits());
        }
    }
}
