package ovningsuppgifter2SQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class CheckCourses {

	private Connection conn = null;
	CallableStatement cstm = null;
	ResultSet rs = null;

	public static void main(String[] args) {

		CheckCourses cc = new CheckCourses();
		Scanner sc = new Scanner(System.in);
		System.out.println("Skriv in start och slutdatum");
		cc.check(sc.nextLine(), sc.nextLine());
	}

	private void check(String start, String slut) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			
			conn.setAutoCommit(false);

			cstm = conn.prepareCall("{call GetPagaendeKurser(?, ?)}");
			cstm.setString(1, start);
			cstm.setString(2, slut);
			
			if (cstm.execute()) {
				rs = cstm.getResultSet();
				while (rs.next()){
					String kursnamn = rs.getString(1);
					String fornamn = rs.getString(2);
					String efternamn = rs.getString(3);
					System.out.println(kursnamn + " " + fornamn + " " + efternamn);
				}
			}
				

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
