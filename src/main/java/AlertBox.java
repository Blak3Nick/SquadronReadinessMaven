import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;


public class AlertBox {

    public static void display(String title, String message) {
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Panel label = new Panel();
        label.setText(message);
        label.getStyleClass().add("h5");
        Button closeButton = new Button("Close");
        closeButton.getStyleClass().setAll("btn-lg", "btn-success");
        closeButton.setOnAction(e -> window.close());
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        window.setScene(scene);
        window.showAndWait();

    }

}
