package ovningsuppgifter2SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lektion2.Elev;
import lektion2.Klass;

public class GetStudentInfo extends Application {

	private Connection conn = null;
	private Statement stm = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	ArrayList<Klass> klassLista = new ArrayList<Klass>();
	ArrayList<Elev> elevLista = new ArrayList<Elev>();

	@Override
	public void start(Stage primaryStage) {
		getStudentDataFromDB();
		ChoiceDialog<Elev> dialog = new ChoiceDialog<Elev>(elevLista.get(0), elevLista);
		dialog.setTitle("Välj klass");
		dialog.setHeaderText("Välj en klass");
		dialog.setContentText("Klass:");

		Optional<Elev> result = dialog.showAndWait();

		if (result.isPresent()) {
			System.out.println(result.get().toString());
			Klass k = getKlass(result);
			Pane pane = new Pane();
			Scene scene = new Scene(pane);
			TextArea ta = new TextArea();
			pane.getChildren().add(ta);
			ta.setText(result.get().toString() + " " +k);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		}

	}

	
	public static void main(String[] args) {
		launch(args);
	}

	private void getStudentDataFromDB() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Skola", "Simon", "abc123");
			stm = conn.createStatement();
			rs = stm.executeQuery("Select * from Elev");

			while (rs.next()) {
				Elev elev = new Elev(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				elevLista.add(elev);	
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Klass getKlass(Optional<Elev> result) {
		Klass k = null;
		try {
			pstm = conn.prepareStatement("SELECT * from Klass where klass.kod=?");
			pstm.setString(1, result.get().getKlasskod());
			rs = pstm.executeQuery();
			//while (rs.next()) {
				rs.next();
				k = new Klass(rs.getString(1), rs.getString(2), rs.getInt(3));
				klassLista.add(k);
				
			//}
			return k;
			

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
		return null;
	}
	private void secondWindow(Optional<Elev> result) {
		ChoiceDialog<Klass> klassDialog = new ChoiceDialog<Klass>(klassLista.get(0), klassLista);
		klassDialog.setTitle("Välj Klass");
		klassDialog.setHeaderText("Klass från Elev " + result.get().getPersonnummer());
		klassDialog.setContentText("Klass:");

		Optional<Klass> klassResult = klassDialog.showAndWait();

		if (klassResult.isPresent()) {
			System.out.println(klassResult.get().toString());
		}

	}


}
