package utility.database;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/** 使用JDBC + C3P0 访问 MySQL 
 * 
 * @author shaofa
 *
 */
public class JdbcPool
{
	public static ComboPooledDataSource c3p0 ;
	
	static{
		c3p0 = new ComboPooledDataSource();
	}
	
	/** 从连接池中获取数据库连接 */
	public static Connection getConnection() throws Exception
	{
		Connection conn = c3p0.getConnection();
		return conn;
	}
	
	/** 关闭连接池 */
	public static void destroy()
	{
		c3p0.close();
	}
}


