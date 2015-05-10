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

import pachong.dao.DateManger;
import util.dbUtil;

@SuppressWarnings("deprecation")
public class pa
{
	// 存 要爬的url的list
	private ArrayList<String> newUrlList = new ArrayList<String>();
	// 爬过的url的list
	@SuppressWarnings("unused")
	private ArrayList<String> oldUrlList = new ArrayList<String>();
	// 计数
	private int flag = 0;

	// URL 正则
	private static String path = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	private static Pattern pattern = Pattern.compile(path);

	private String saveFilePath = "G:\\url\\taobao";

	private DateManger dateManger = new DateManger();

	private dbUtil dbUtil = new dbUtil();

	private Connection con = null;

	// 初始化数据
	public pa(String url)
	{
		newUrlList.add(url);
	}

	// 爬数据 方法
	@SuppressWarnings("resource")
	public void queryUrlContent()
	{
		HttpClient client = new DefaultHttpClient();

		// 要爬的网页大于计数 ，继续爬
		while (newUrlList.size() > 0)
		{

			int n = (int) System.currentTimeMillis();
			String uuu = newUrlList.get(0);
			HttpGet getHttp = new HttpGet(newUrlList.get(0));

			String content = null;

			// 添加到已爬list，计数+1
			// oldUrlList.add(newUrlList.get(flag));
			flag++;

			try
			{
				HttpResponse response;
				// 获取信息
				response = client.execute(getHttp);
				HttpEntity entity = response.getEntity();

				if (entity != null)
				{
					// 转换文本信息
					content = EntityUtils.toString(entity);

					// File f = new File(saveFilePath, String.valueOf(flag)
					// + ".txt");
					// if (!f.exists())
					// {
					// f.createNewFile();
					// }
					// FileOutputStream fos = new FileOutputStream(f);
					// fos.write(content.getBytes());
					// fos.close();

					// 存到数据库里
					con = dbUtil.getConnection();
					dateManger.addContent(con, uuu, content);
					dbUtil.closeCon(con);
					/**
					 * 筛选数据，文本存进数据库，url存进要爬的里面，先做比较有没有存在过
					 */
					Matcher matcher = pattern.matcher(content);
					while (matcher.find())
					{
						String newUrl = matcher.group();
						// System.out.println(newUrl);
						// 判断是否存在过
						if (!newUrlList.contains(newUrl))
						{
							newUrlList.add(newUrl);
						}
					}

					// 输出
					// System.out.println(content);
					int e = (int) System.currentTimeMillis();

				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{

				System.out.println("已爬：" + flag + "--------总共："
						+ newUrlList.size() + "--爬的网页:" + uuu);
				// 移除
				newUrlList.remove(0);
			}
		}

	}

	public static void main(String[] args)
	{
		pa t = new pa("http://www.taobao.com");
		t.queryUrlContent();
	}
}
