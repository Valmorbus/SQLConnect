package ovningsuppgifter2SQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class RegisterTransition {
	Connection connect = null;
	PreparedStatement pstm = null;
	ResultSet rset = null;
	private static boolean checkok = true;
	private static String pnummer;
	private static String klassid;


	public static void main(String[] args) {
		RegisterTransition rt = new RegisterTransition ();
		
		Scanner sc = new Scanner(System.in);
		while (checkok){
			System.out.println("Skriv in personnummer");
			pnummer = sc.nextLine();
			if (pnummer.length()==10)
				checkok = false;
		}
		while (checkok == false){
			System.out.println("Skriv in Klasskod");
			klassid = sc.nextLine();
			if (klassid.length()<=5)
				checkok = true;
		}
		System.out.println("Skriv in förnamn");
		String fornamn = sc.nextLine();
		System.out.println("Skriv in efternamn");
		String efternamn = sc.nextLine();
		System.out.println("Skriv in epost");
		String epost = sc.nextLine();
		System.out.println("Skriv in stad");
		String stad = sc.nextLine();
		rt.addStudent(pnummer, klassid, fornamn, efternamn, epost, stad);
		
	}

	private void addStudent(String personnummer, String KlassKod, String fornamn, String efternamn, String epost, String stad) {
		

		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			connect.setAutoCommit(false);
			PreparedStatement prepstm = connect.prepareStatement("Insert into Elev("
					+ "Personnummer, Förnamn, Efternamn, Epost, Klasskod, Stad)" + " Values (?, ?, ?, ?, ?, ?)");
			prepstm.setString(1, personnummer);
			prepstm.setString(2, fornamn);
			prepstm.setString(3, efternamn);
			prepstm.setString(4, epost);
			prepstm.setString(5, KlassKod);
			prepstm.setString(6, stad);
			int a = prepstm.executeUpdate();
			if (a == 1) {
				System.out.println("Elev lades till");
				connect.commit();
			}
		} catch (MySQLIntegrityConstraintViolationException e1) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong or student allready exists. Rollback activated");
			 try {
				connect.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
}
