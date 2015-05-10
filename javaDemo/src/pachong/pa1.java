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
	// 存 要爬的url的list
	private ArrayList<String> newUrlList = new ArrayList<String>();
	// 计数
	private int flag = 0;
	// URL 正则
	private static String path = "(http):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	// private static String path =
	// "(http:\\/\\/www.zhihu.com/question/)+([\\w]*)";

	private static Pattern pattern = Pattern.compile(path);
	// 数据库连接工具类
	private dbUtil dbUtil = new dbUtil();
	// 保存数据到数据库
	private DateManger dateManger = new DateManger();
	// 数据库连接
	private Connection con = null;

	// 被爬的网页内容
	private String content = null;

	private zhengzeUtil zUtil = new zhengzeUtil();

	// 初始化数据
	public pa1(String url)
	{
		newUrlList.add(url);
	}

	/**
	 * 爬网页，并保存在数据库
	 */
	public void queryUrlContent()
	{
		// http连接
		HttpClient client = new DefaultHttpClient();

		// 还有要爬的网页则继续爬
		while (newUrlList.size() > 0)
		{
			// 获得要爬的URL
			String url = newUrlList.get(flag);
			// get请求
			HttpGet getHttp = new HttpGet(url);

			content = "";
			try
			{
				// 获取页面
				HttpResponse response = client.execute(getHttp);

				HttpEntity entity = response.getEntity();

				if (entity != null)
				{
					// 获取文本信息
					content = EntityUtils.toString(entity);
					
					
					
					//通过正则工具类 把匹配的 url放到list里
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
				// 计数+1
				flag++;
				System.out.println("第" + flag + "个,总共：" + newUrlList.size()
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