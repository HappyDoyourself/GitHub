package com.baomidou.springwind.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * jdbc关闭数据库连接
 * 
 * @author
 * 
 */
public class JDBCUtils {
	// 定义数据库的用户名
	private static final String USERNAME = "root";
	// 定义数据库的密码
	private static final String PASSWORD = "8518344";
	// 定义数据库的驱动信息
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	// 定义访问数据库的地址
	// private static final String URL =
	// "jdbc:mysql://10.10.10.224:3306/yn_crm?useServerPrepStmts=false&rewriteBatchedStatements=true&characterEncoding=utf-8&useOldAliasMetadataBehavior=true";
	private static final String URL = "jdbc:mysql://10.10.10.232:3306/yn_crm?useServerPrepStmts=false&rewriteBatchedStatements=true&characterEncoding=utf-8&useOldAliasMetadataBehavior=true";

	// 定义访问数据库的连接
	private static Connection connection;

	public static Connection getJDBCUtilsConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 用事务，必须设置setAutoCommit false，表示手动提交
			connection.setAutoCommit(false);
			// 设置事务的隔离级别。
			connection
					.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void freeJdbc(Connection connection, PreparedStatement prest,
			ResultSet st) {

		try {
			if (connection != null) {
				connection.close();
			}
			if (prest != null) {
				prest.close();
			}
			if (st != null) {
				st.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void freeJdbc(Connection connection, PreparedStatement prest,
			ResultSet rst, Statement st) {

		try {
			if (rst != null) {
				rst.close();
			}
			if (st != null) {
				st.close();
			}
			if (prest != null) {
				prest.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void freeJdbc2(Connection connection, List<PreparedStatement> prest,
			ResultSet rst, Statement st) {

		try {
			if (rst != null) {
				rst.close();
			}
			if (st != null) {
				st.close();
			}
			if (prest != null) {
				for(PreparedStatement e:prest){
					if(e!=null)
						e.close();
				}
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
