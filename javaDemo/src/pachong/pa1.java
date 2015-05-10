package pachong;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pachong.dao.DateManger;
import util.dbUtil;
import util.zhengzeUtil;

public class pa1
{
	// �� Ҫ����url��list
	private ArrayList<String> newUrlList = new ArrayList<String>();
	// ����
	private int flag = 0;
	// URL ����
	private static String path = "(http):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	// private static String path =
	// "(http:\\/\\/www.zhihu.com/question/)+([\\w]*)";

	private static Pattern pattern = Pattern.compile(path);
	// ���ݿ����ӹ�����
	private dbUtil dbUtil = new dbUtil();
	// �������ݵ����ݿ�
	private DateManger dateManger = new DateManger();
	// ���ݿ�����
	private Connection con = null;

	// ��������ҳ����
	private String content = null;

	private zhengzeUtil zUtil = new zhengzeUtil();

	// ��ʼ������
	public pa1(String url)
	{
		newUrlList.add(url);
	}

	/**
	 * ����ҳ�������������ݿ�
	 */
	public void queryUrlContent()
	{
		// http����
		HttpClient client = new DefaultHttpClient();

		// ����Ҫ������ҳ�������
		while (newUrlList.size() > 0)
		{
			// ���Ҫ����URL
			String url = newUrlList.get(flag);
			// get����
			HttpGet getHttp = new HttpGet(url);

			content = "";
			try
			{
				// ��ȡҳ��
				HttpResponse response = client.execute(getHttp);

				HttpEntity entity = response.getEntity();

				if (entity != null)
				{
					// ��ȡ�ı���Ϣ
					content = EntityUtils.toString(entity);
					
					
					
					//ͨ�����򹤾��� ��ƥ��� url�ŵ�list��
					ArrayList<String> zhengzeURL = zhengzeUtil.contentToUrl(content);
					if(zhengzeURL != null && zhengzeURL.size() >0)
					{
						for(int f =0;f<zhengzeURL.size();f++)
						{
							if(!newUrlList.contains(zhengzeURL.get(f)))
							{
								newUrlList.add(zhengzeURL.get(f));
							}
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				// ����+1
				flag++;
				System.out.println("��" + flag + "��,�ܹ���" + newUrlList.size()
						+ ",URL:" + url);
			}

		}

	}

	public static void main(String[] args)
	{
		pa1 p = new pa1("http://www.zhihu.com/question/22021222");
		p.queryUrlContent();
	}

}