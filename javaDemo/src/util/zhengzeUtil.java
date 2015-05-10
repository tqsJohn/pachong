package util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * 
 * @author 刘强 2015年5月7日23:31:15
 */
public class zhengzeUtil
{
	// URL 正则
	private static String path = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	private static Pattern pattern = Pattern.compile(path);

	/**
	 * 
	 * @param String
	 *            content
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> contentToUrlAll(String content)
	{
		ArrayList<String> urlList = new ArrayList<String>();
		Matcher matcher = pattern.matcher(content);
		while (matcher.find())
		{
			urlList.add(matcher.group());
		}
		return urlList;
	}

	/**
	 * url 结尾是 css js png jpg gif 的去除
	 *
	 * @param content
	 * @return
	 */
	public static ArrayList<String> contentToUrl(String content)
	{
		ArrayList<String> urlList = new ArrayList<String>();
		Matcher matcher = pattern.matcher(content);
		while (matcher.find())
		{
			String newUrl = matcher.group();
			String str1 = matcher.group(1);
			String str2 = matcher.group(2);
			String str3 = matcher.group(3);
			if (str3 != null && str3.lastIndexOf(".") > 0)
			{
				String str = str3.substring(str3.lastIndexOf("."),
						str3.length());
				if (str.equals(".jpg") 
					|| str.equals(".png")
					|| str.equals(".gif") 
					|| str.equals(".css")
					|| str.equals(".js") 
					|| str.equals(".ico")
					|| str.equals(".xml"))
				{
					;
				} else
				{
					if (!urlList.contains(newUrl))
					{
						urlList.add(newUrl);
					}
				}
			} else
			{
				if (!urlList.contains(newUrl))
				{
					urlList.add(newUrl);
				}
			}
		}
		return urlList;
	}
}
