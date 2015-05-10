package pachong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 爬虫出来的数据处理类
 * @author 刘强
 * 2015年5月7日23:35:14
 */
public class DateManger
{
	/**
	 * 保存数据 
	 * @param url
	 * @param content
	 */
	public void addContent(Connection conn,String url,String content) throws SQLException
	{
		String sql = "insert into reptile (`url`,`content`,`add_time`) values(?,?,NOW())";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,url);
		pstm.setString(2, content);
		pstm.execute();
	}
	
}
