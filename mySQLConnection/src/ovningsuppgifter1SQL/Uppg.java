package ovningsuppgifter1SQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Uppg {

	public static void main(String[] args) {
		Uppg u = new Uppg();
		u.getStudentFromClass();
		u.GetSchedule();
		Scanner sc = new Scanner(System.in);
		System.out.println("Vilken klass vill du kolla?");
		String klass = sc.nextLine();
		u.medianGrade(klass);
		//u.addStudent();
		//System.out.println("Sök på personnummer");
		//u.updateStudent(sc.nextLine());
		u.deleteTecher(); 
	}

	private void getStudentFromClass() {
		Connection connect = null;
		Statement stm = null;
		ResultSet rset = null;

		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			stm = connect.createStatement();
			rset = stm.executeQuery("SELECT Klass.Namn, elev.* FROM Elev, Klass where elev.klasskod = "
					+ "klass.kod Order by Elev.efternamn");
			while (rset.next()) {
				String personnummer = rset.getString(1);
				String fornamn = rset.getString(3);
				String efternamn = rset.getString(4);
				System.out.println(personnummer + " " + fornamn + " " + efternamn);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GetSchedule() {
		Connection connect = null;
		Statement stm = null;
		ResultSet rset = null;

		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			stm = connect.createStatement();
			rset = stm.executeQuery("Select Klass.namn, kurs.namn, lokal.namn, kurslokal.tid, "
					+ "kurslokal.datum from kurs inner join klasskurs on klasskurs.KursKod = Kurs.Kod "
					+ "inner join klass on klass.kod = klasskurs.KlassKod "
					+ "inner join kurslokal on kurs.kod = kurslokal.Kurskod "
					+ "inner join lokal on lokal.Lokalnummer = kurslokal.Lokalnummer Order by Datum, tid");
			while (rset.next()) {
				String klass = rset.getString(1);
				String kurs = rset.getString(2);
				String lokal = rset.getString(3);
				Time tid = rset.getTime(4);
				Date datum = rset.getDate(5);
				System.out.println(klass + " " + kurs + " " + lokal + " " + tid + " " + datum);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void medianGrade(String kurs) {
		Connection connect = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {

			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			boolean exists = false;
			Statement stm = connect.createStatement();
			rset = stm.executeQuery("Select kurs.kod from kurs");
			while (rset.next()) {
				String temp = rset.getString(1);
				if (kurs.equalsIgnoreCase(temp))
					exists = true;
			}
			if (exists) {
				pstm = connect.prepareStatement("Select betyg from betygvärde where värde = "
						+ "(select round(avg(betygvärde.värde), 0) from kursbetyg "
						+ "inner join betygvärde on kursbetyg.betyg = "
						+ "betygvärde.betyg where kursbetyg.KursKod =?)");
				pstm.setString(1, kurs);
				rset = pstm.executeQuery();
				while (rset.next()) {
					String betyg = rset.getString(1);
					System.out.println("Snittbetyg för kurs " + kurs + " är " + betyg);
				}
			} else
				System.out.println("Kursen " + kurs + " finns inte");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addStudent() {
		Connection connect = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			PreparedStatement prepstm = connect.prepareStatement("Insert into Elev("
					+ "Personnummer, Förnamn, Efternamn, Epost, Klasskod, Stad)" + " Values (?, ?, ?, ?, ?, ?)");
			prepstm.setString(1, "9012060311");
			prepstm.setString(2, "Testet");
			prepstm.setString(3, "Test");
			prepstm.setString(4, "Jan@banan.se");
			prepstm.setString(5, "JAV15");
			prepstm.setString(6, "Uppsala");
			int a = prepstm.executeUpdate();
			if (a == 1) {
				System.out.println("Elev lades till");
			}
		} catch (MySQLIntegrityConstraintViolationException e1) {
			// TODO Auto-generated catch block
			System.out.println("Eleven finns redan");
			allStudents();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateStudent(String personnummer) {
		Connection connect = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		Scanner sc = new Scanner(System.in);
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			boolean exists = false;
			Statement stm = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rset = stm.executeQuery("Select elev.personnummer from Elev");
			while (rset.next()) {
				String temp = rset.getString(1);
				if (personnummer.equalsIgnoreCase(temp))
					exists = true;
			}
			if (exists) {
				pstm = connect.prepareStatement("Update elev set epost = ? where personnummer = ?",
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				pstm.setString(2, personnummer);
				System.out.println("ny epost?");
				String temp = sc.nextLine();
				pstm.setString(1, temp);
				int i = pstm.executeUpdate();
				if (i == 1)
					System.out.println("Epost uppdaterad");

			} else
				System.out.println("Ingen Elev registrerad med personnummer  " + personnummer + " finns");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void allStudents() {
		Connection connect = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;

		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			Statement stm = connect.createStatement();
			rset = stm.executeQuery("Select * from Elev");
			while (rset.next()) {
				String fornamn = rset.getString("Förnamn");
				String efternamn = rset.getString("Efternamn");
				System.out.println(fornamn + " " + efternamn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteTecher() {
		Connection connect = null;
		Statement stm = null;
		ResultSet rset = null;

		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon", "abc123");
			stm = connect.createStatement();
			rset = stm.executeQuery("SELECT förnamn, efternamn, anställningsnummer FROM lärare");
			while (rset.next()) {
				String fornamn = rset.getString(1);
				String efternamn = rset.getString(2);
				int anstallningsnr = rset.getInt(3);
				System.out.println(fornamn + " " + efternamn + " Anställningsnummer " +anstallningsnr);
			}
			PreparedStatement pstm = connect.prepareStatement("Delete from Lärare where Anställningsnummer = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			Scanner sc = new Scanner(System.in); 
			pstm.setString(1, sc.nextLine());
			int i = pstm.executeUpdate();
			if (i == 1){
				System.out.println("lärare borttagen");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
