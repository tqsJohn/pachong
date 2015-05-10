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
	// �� Ҫ����url��list
	private ArrayList<String> newUrlList = new ArrayList<String>();
	// ������url��list
	@SuppressWarnings("unused")
	private ArrayList<String> oldUrlList = new ArrayList<String>();
	// ����
	private int flag = 0;

	// URL ����
	private static String path = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	private static Pattern pattern = Pattern.compile(path);

	private String saveFilePath = "G:\\url\\taobao";

	private DateManger dateManger = new DateManger();

	private dbUtil dbUtil = new dbUtil();

	private Connection con = null;

	// ��ʼ������
	public pa(String url)
	{
		newUrlList.add(url);
	}

	// ������ ����
	@SuppressWarnings("resource")
	public void queryUrlContent()
	{
		HttpClient client = new DefaultHttpClient();

		// Ҫ������ҳ���ڼ��� ��������
		while (newUrlList.size() > 0)
		{

			int n = (int) System.currentTimeMillis();
			String uuu = newUrlList.get(0);
			HttpGet getHttp = new HttpGet(newUrlList.get(0));

			String content = null;

			// ��ӵ�����list������+1
			// oldUrlList.add(newUrlList.get(flag));
			flag++;

			try
			{
				HttpResponse response;
				// ��ȡ��Ϣ
				response = client.execute(getHttp);
				HttpEntity entity = response.getEntity();

				if (entity != null)
				{
					// ת���ı���Ϣ
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

					// �浽���ݿ���
					con = dbUtil.getConnection();
					dateManger.addContent(con, uuu, content);
					dbUtil.closeCon(con);
					/**
					 * ɸѡ���ݣ��ı�������ݿ⣬url���Ҫ�������棬�����Ƚ���û�д��ڹ�
					 */
					Matcher matcher = pattern.matcher(content);
					while (matcher.find())
					{
						String newUrl = matcher.group();
						// System.out.println(newUrl);
						// �ж��Ƿ���ڹ�
						if (!newUrlList.contains(newUrl))
						{
							newUrlList.add(newUrl);
						}
					}

					// ���
					// System.out.println(content);
					int e = (int) System.currentTimeMillis();

				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{

				System.out.println("������" + flag + "--------�ܹ���"
						+ newUrlList.size() + "--������ҳ:" + uuu);
				// �Ƴ�
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
