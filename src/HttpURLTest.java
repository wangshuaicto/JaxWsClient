import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class HttpURLTest {

	public static void main(String[] args) {
		try {
			String strurl = "http://localhost:8080/testjws/service/sayHi";
			URL url = new URL(strurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.connect();
			
			OutputStream os = conn.getOutputStream();
			//写入到connection
			String postdata = "<?xml version=\"1.0\" ?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Body><xlns:checkTime xmlns:xlns=\"http://demo.jaxws.com/\"><clientTime xmlns=\"\">2010-04-17T09:00:00</clientTime></xlns:checkTime></SOAP-ENV:Body></SOAP-ENV:Envelope>";
			os.write(postdata.getBytes());
			os.flush();
			os.close();
			
			boolean istest=true;
			
			//从connection中读取返回数据
			if(istest)
			{
				/**********************************测试输出的SOAP返回******************************/
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String str="";
				while((str = br.readLine())!=null)
				{
					System.out.println(str);
				}
				/**********************************测试输出的SOAP返回******************************/
			}else
			{

				/**********************************直接将SOAP XML 返回格式转换为DOM************************/
				InputStream is= conn.getInputStream();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(is);
				System.out.println(doc.getElementsByTagName("tagname").item(0).getTextContent());
				/**********************************直接将SOAP XML 返回格式转换为DOM************************/
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
