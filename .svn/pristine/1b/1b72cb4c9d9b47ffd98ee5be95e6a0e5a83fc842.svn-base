package org.com.drag.web.common;

import java.sql.*;

/**
 * 
 * Copyright © 2017 All rights reserved.

 * @ClassName: DbDataUtil

 * @Description: 数据库连接工具

 * @author: jiaonanyue

 * @date: 2017年5月17日 上午9:43:50
 */
public class DbDataUtil {
	

	public static void main(String[] args) {
		
	 Connection conn1 = null;// 创建一个数据库连接
	 Statement stmt1 = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	 ResultSet  rs1 = null; // 创建一个结果集对象
	    
	//Oracle8/8i/9i数据库（thin模式）
	try{
	/*Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	String url1 = "jdbc:oracle:thin:@localhost:1521:orcl"; 
	String user1 = "scott";
	String passWord1="tiger";
	 conn1= DriverManager.getConnection(url1,user1 ,passWord1); 
	 stmt1=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	String sql1="select * from test"; 
	 rs1=stmt1.executeQuery(sql1);*/
	 
	 
	 Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url1 ="jdbc:mysql://localhost:3306/shiro?user=root&password=jiao&useUnicode=true&characterEncoding=utf-8";
		 conn1= DriverManager.getConnection(url1);
		 System.out.println("连接成功！");
		 stmt1=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		 //查询数据库所有的表
		String sql1="select table_name from information_schema.tables where table_schema='shiro' and table_type='base table';"; 
		 rs1=stmt1.executeQuery(sql1);
		 while (rs1.next())
	            // 当结果集不为空时
	            System.out.println("学号:" + "姓名:"
	                    + rs1.getString("table_name"));
		
	}catch (Exception e)
    {
        e.printStackTrace();
    }
    finally
    {
        try
        {
            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
            // 注意关闭的顺序，最后使用的最先关闭
            if (rs1 != null)
            	rs1.close();
            if (stmt1 != null)
            	stmt1.close();
            if (conn1 != null)
            	conn1.close();
            System.out.println("数据库连接已关闭！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	//MS sql server
	/*Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
	String url2="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=pubs"; 
	String user2="sa"; 
	String password2=""; 
	Connection conn2= DriverManager.getConnection(url2,user2 ,password2); 
	Statement stmt2=conn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	String sql2="select * from test";
	ResultSet rs2=stmt2.executeQuery(sql2);*/ 
	//db2
	/*Class.forName("com.ibm.db2.jdbc.app.DB2Driver ").newInstance();
	String url="jdbc:db2://localhost:5000/sample"; 
	String user="admin"; 
	String password=""; 
	Connection conn= DriverManager.getConnection(url1,user1 ,password);
	Statement stmt=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	String sql="select * from test";
	ResultSet rs=stmt1.executeQuery(sql1);
	//informix
	Class.forName("com.informix.jdbc.IfxDriver").newInstance(); 
	String url = "jdbc:informix-sqli://123.45.67.89:1533/testDB:INFORMIXSERVER=myserver;user=testuser;password=testpassword"; 
	Connection conn= DriverManager.getConnection(url1); 
	Statement stmt=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	String sql="select * from test"; 
	ResultSet rs=stmt1.executeQuery(sql1);
	//sybase
	Class.forName("com.sybase.jdbc.SybDriver").newInstance(); 
	String url =" jdbc:sybase:Tds:localhost:5007/tsdata";
	PRoperties sysProps = System.getProperties(); 
	SysProps.put("user","userid");
	SysProps.put("password","user_password");
	Connection conn= DriverManager.getConnection(url1, SysProps);
	Statement stmt=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	String sql="select * from test"; 
	ResultSet rs=stmt1.executeQuery(sql1);
	//MySQL
	Class.forName("org.gjt.mm.mysql.Driver").newInstance();
	String url ="jdbc:mysql://localhost/softforum?user=soft&password=soft1234&useUnicode=true&characterEncoding=8859_1"
	Connection conn= DriverManager.getConnection(url1);
	Statement stmt=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	String sql="select * from test"; 
	ResultSet rs=stmt1.executeQuery(sql1);
	//postgresql
	Class.forName("org.postgresql.Driver").newInstance();
	String url ="jdbc:postgresql://localhost/soft" 
	String user="myuser";
	String password="mypassword";
	Connection conn= DriverManager.getConnection(url1,user1 ,password); 
	Statement stmt=conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); 
	String sql="select * from test"; 
	ResultSet rs=stmt1.executeQuery(sql1);
	//access
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection conn = DriverManager.getConnection("jdbc:odbc:shnxn","user","");
	Statement stmt = conn1.createStatement();
	ResultSet rs = stmt1.executeQuery("SELECT * FROM member");
*/
	
	}
}
