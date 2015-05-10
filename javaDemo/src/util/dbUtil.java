package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接
 * 
 * @author 刘强 2015年5月7日23:01:43
 */
public class dbUtil
{
	public static final String url = "jdbc:mysql://127.0.0.1/pachong";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "123456";

	private Connection conn = null;

	public Connection getConnection()
	{
		try
		{
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeCon(Connection conn)throws SQLException
	{
		if(conn != null)
		{
			conn.close();
		}
	}
}
