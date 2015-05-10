package pachong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * ������������ݴ�����
 * @author ��ǿ
 * 2015��5��7��23:35:14
 */
public class DateManger
{
	/**
	 * �������� 
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
