import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test
{
	
	public static void main(String[] args) throws ClientProtocolException, IOException
	{
		String content = null;
		// http����
		HttpClient client = new DefaultHttpClient();
		// get����
		HttpGet getHttp = new HttpGet("http://www.zhihu.com");
		// ��ȡҳ��
		HttpResponse response = client.execute(getHttp);

		HttpEntity entity = response.getEntity();

		if (entity != null)
		{
			// ��ȡ�ı���Ϣ
			content = EntityUtils.toString(entity);
			
			/**
			 * ����html
			 */
			Document doc = Jsoup.parse(content);
			Elements elements = doc.select("a[href]");
			
			for(int i =0;i<elements.size();i++)
			{
				System.out.println(elements.get(i));
			}
		}
	}
}
