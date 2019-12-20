package com.bigdata2019.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2019.emaillist.vo.EmaillistVo;


public class EmaillistDao {

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8";
		Connection conn = DriverManager.getConnection(url, "webdb", "webdb");

		return conn;
	}

	public List<EmaillistVo> findAll() {
		List<EmaillistVo> voList = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
			// Create Statement Object
			stmt = conn.createStatement();

			// Execute SQL commands
			String sql = "select no, first_name, last_name, email from emaillist";
			rs = stmt.executeQuery(sql);

			// Show Results
			while (rs.next()) {
				Long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);

				EmaillistVo vo = new EmaillistVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);

				voList.add(vo);

//				System.out.println(no + ". First name: " + firstName + "\t Last name: " + lastName + "\t Email:" + email);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load driver: " + e);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} finally {
			// remove resources
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return voList;
	}

	// remove static to make this an instance
	@SuppressWarnings("finally")
	public Boolean insert(EmaillistVo vo) {
		Boolean result = false;
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = getConnection();
			// Create Statement Object
			stmt = conn.createStatement();

			// Execute SQL Statment
			String sql = "insert into emaillist values(null,'" + vo.getEmail() + "','" + vo.getLastName() + "','"
					+vo.getFirstName() + "')";

			int count = stmt.executeUpdate(sql);
			result = (count == 1);

			
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load driver: " + e);
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		} finally {
			// remove resources
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

	}

}
