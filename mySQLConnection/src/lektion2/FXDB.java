package lektion2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

public class FXDB extends Application {

	private Connection conn = null;
	private Statement stm = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	ArrayList<Klass> klassLista = new ArrayList<Klass>();
	ArrayList<Elev> elevLista = new ArrayList<Elev>();

	@Override
	public void start(Stage primaryStage) {
		getDataFromDB();
		ChoiceDialog<Klass> dialog = new ChoiceDialog<Klass>(klassLista.get(0), klassLista);
		dialog.setTitle("Välj klass");
		dialog.setHeaderText("Välj en klass");
		dialog.setContentText("Klass:");

		Optional<Klass> result = dialog.showAndWait();

		if (result.isPresent()) {
			System.out.println(result.get().toString());
			getStudents(result);
			secondWindow(result);
		}

	}

	private void secondWindow(Optional<Klass> result) {
		ChoiceDialog<Elev> elevDialog = new ChoiceDialog<Elev>(elevLista.get(0), elevLista);
		elevDialog.setTitle("Välj Elev");
		elevDialog.setHeaderText("Elev från klassen " + result.get().getNamn());
		elevDialog.setContentText("Elev:");

		Optional<Elev> elevResult = elevDialog.showAndWait();

		if (elevResult.isPresent()) {
			System.out.println(elevResult.get().toString());
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void getDataFromDB() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Skola", "Simon", "abc123");
			stm = conn.createStatement();
			rs = stm.executeQuery("Select * from klass");

			while (rs.next()) {
				Klass k = new Klass(rs.getString(1), rs.getString(2), rs.getInt(3));
				klassLista.add(k);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getStudents(Optional<Klass> result) {
		try {
			// conn =
			// DriverManager.getConnection("jdbc:mysql://localhost/Skola",
			// "Simon", "abc123");
			pstm = conn.prepareStatement("SELECT * from elev where elev.klasskod=?");
			pstm.setString(1, result.get().getKod());
			rs = pstm.executeQuery();
			while (rs.next()) {
				Elev elev = new Elev(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				elevLista.add(elev);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
				stm.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
