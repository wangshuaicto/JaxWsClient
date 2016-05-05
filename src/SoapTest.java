import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.junit.Test;
import org.w3c.dom.Document;


public class SoapTest {
	
	private final static QName SERVICE_NAME=new QName("http://demo.jaxws.com/", "HelloWorldImplService");
	private final static QName PORT_NAME=new QName("http://demo.jaxws.com/", "HelloWorldImplPort");
	private final static String ADDRESS="http://localhost:8080/testjws/service/sayHi?wsdl";
	
	@Test
	public void createSoapMessage() throws SOAPException, IOException
	{
		//测试SOAPMEssage的输出
		
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		QName qname = new QName("http://test", "add", "namespace");
		SOAPBodyElement ele = body.addBodyElement(qname);
		ele.addChildElement("a").setValue("sdfsd");
		ele.addChildElement("b").setValue("good");
		
		//打印到输出
		message.writeTo(System.out);
		
	}

	@Test
	public void soapDispatch() throws JAXBException, SOAPException, IOException
	{
		Service service = Service.create(new URL(ADDRESS),SERVICE_NAME);
		//创建Dispatch
		Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_NAME, SOAPMessage.class, Service.Mode.MESSAGE);
		//创建SOAPMessage
		
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		//创建QName指定消息中传递数据
		QName qname = new QName("http://demo.jaxws.com/", "checkTime", "xlns");
		SOAPBodyElement ele = body.addBodyElement(qname);
		ele.addChildElement("clientTime").setValue("2010-04-17T09:00:00");
		message.writeTo(System.out);
		System.out.println();
		System.out.println("-------------");
		SOAPMessage response = dispatch.invoke(message);
		response.writeTo(System.out);
		
		//将相应的消息转换为doc对象
		 Document doc = response.getSOAPBody().extractContentAsDocument();
	}
	
	
	@Test
	public void soapcmdbDispatch() throws JAXBException, SOAPException, IOException
	{
		Service service = Service.create(new URL("http://localhost:8080/itomws/CMDBService?wsdl"),new QName("http://cmdb.api.ws.itom.mochasoft.com/", "ICMDBServiceService"));
		//创建Dispatch
		Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName("http://cmdb.api.ws.itom.mochasoft.com/", "ICMDBServicePort"), SOAPMessage.class, Service.Mode.MESSAGE);
		//创建SOAPMessage
		
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		//创建QName指定消息中传递数据
		//http://cmdb.api.ws.itom.mochasoft.com/  这个是wsdl中的全名   一定要看清楚，最后全部复制   我就是少了最后一个/导致找不到getCIStockCount
		QName qname = new QName("http://cmdb.api.ws.itom.mochasoft.com/", "getCIStockCount", "xlns");
		SOAPBodyElement ele = body.addBodyElement(qname);
//		ele.addChildElement("name").setValue("sdfsd");
		message.writeTo(System.out);
		System.out.println();
		System.out.println("-------------");
		SOAPMessage response = dispatch.invoke(message);
		response.writeTo(System.out);
		System.out.println();
		//将相应的消息转换为doc对象
		
	    Document doc = response.getSOAPBody().extractContentAsDocument();
//	    Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
	    String result = doc.getElementsByTagName("message").item(0).getTextContent();
	    String respObject = doc.getElementsByTagName("respObject").item(0).getTextContent();
	    System.out.println(result);
	    System.out.println(respObject);
		
		
	}
}
