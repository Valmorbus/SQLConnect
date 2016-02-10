package lektion2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ProcedureTest {
	
	private Connection conn = null;
	CallableStatement cstm = null;
	ResultSet rs = null;
	
	public static void main (String[] args){
		ProcedureTest pt = new ProcedureTest();
		pt.runAll();
		
	}
	private void runAll(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Skola", "Simon", "abc123");
			
			conn.setAutoCommit(false);
			
			cstm = conn.prepareCall("{call get_kurser(?, ?)}");
			cstm.setString(1, "DBAS");
			cstm.registerOutParameter(2, Types.VARCHAR);
			if (cstm.execute()){
				rs = cstm.getResultSet();
				rs.first();
				System.out.println(rs.getString("Namn"));
			}
			else {
				String kursnamn = cstm.getString(2);
				System.out.println(kursnamn);
			}
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally{
			
				try {
					if(rs != null)
					rs.close();
					cstm.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}

}
