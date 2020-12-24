package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import af.spring.AfRestData;
import utility.database.JdbcPool;

@Controller
public class DatabaseTest {

	@RequestMapping("/query")
	public Object queryInfo() throws SQLException, Exception {

		try (Connection conn = JdbcPool.getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
			ResultSetMetaData res=rs.getMetaData();
			for(int i=1;i<=res.getColumnCount();i++)
			{
				System.out.printf("%-12s", res.getColumnName(i));
			}
			while(rs.next())
			{
				for (int i = 1; i <= res.getColumnCount(); i++) {
					System.out.printf("%-12s", rs.getObject(i));
					
				}
				System.out.println();
			}
		}
		return new AfRestData();
		
	}
	
}
