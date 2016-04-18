package app.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Azz_B on 17/04/2016.
 */
public class Client extends Application {

    public static Stage stage;



    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/client_chat.fxml"));

        primaryStage.setTitle("Chat Side Client");
        Scene scene = new Scene(root, 429, 468);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);



    }
}