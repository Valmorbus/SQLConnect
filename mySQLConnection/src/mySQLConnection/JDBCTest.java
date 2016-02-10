package mySQLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;



public class JDBCTest {
	
	
	public static void main(String[] args) throws SQLException {
		Connection connect = null; 
		Statement stm = null;
		PreparedStatement pstm = null;
		ResultSet rset = null;
		ResultSet rset2 = null;
		
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost/skola", "Simon","abc123" );
			stm = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rset = stm.executeQuery("SELECT * FROM Elev"); // where f�rnamn = 'Simon'");
			 rset.last();
			 rset.updateString("Efternamn", "Uppsala");
			 rset.updateRow();
			 rset.beforeFirst();
			while(rset.next()){
				String personnummer = rset.getString(1);
				String fornamn = rset.getString("F�rnamn");
				String efternamn = rset.getString("Efternamn");
				System.out.println(personnummer + " " +fornamn + " " + efternamn);
				
			}
			rset.last();
			String personnummer = rset.getString(1);
			String fornamn = rset.getString("F�rnamn");
			String efternamn = rset.getString("Efternamn");
			System.out.println(personnummer + " " +fornamn + " " + efternamn);
			//rset.beforeFirst();
			pstm = connect.prepareStatement("Select * from Elev where f�rnamn =?" );
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			pstm.setString(1, s);
			rset2 = pstm.executeQuery();
			while(rset2.next()){
				personnummer = rset2.getString(1);
				 fornamn = rset2.getString("F�rnamn");
				 efternamn = rset2.getString("Efternamn");
				System.out.println(personnummer + " " +fornamn + " " + efternamn);
				
			}
			//int a = stm.executeUpdate("Insert into Elev(Personnummer, F�rnamn, Efternamn, Epost, Klasskod, Stad)"
				//	+ "Values ('8702121212', 'Sverker', 'Eriksson', 's�mst@s.se', 'JAV15', 'Uppsala'");
			try{PreparedStatement prepstm = connect.prepareStatement("insert into Klass (Kod, Namn, �rskurs) Values (?, ?, ?)");
			prepstm.setString(1, "PHP");
			prepstm.setString(2, "PHP16");
			prepstm.setInt(3, 2016);
			int a = prepstm.executeUpdate();
			if (a == 1){
				System.out.println("klassen lades till");
			}
			} catch(MySQLIntegrityConstraintViolationException e1){
				System.out.println("finns redan");
			}
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		finally{
				pstm.close();
				rset.close();
				stm.close();
				connect.close();
		}
	}

}
