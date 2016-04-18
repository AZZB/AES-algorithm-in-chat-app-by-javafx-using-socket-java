package app.main;

import app.background.TaskServer;
import app.controller.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class Main extends Application {

    public static Stage stage;



    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../view/chat_controller.fxml"));

        primaryStage.setTitle("Chat Side Server");
        Scene scene = new Scene(root, 429, 468);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);



    }
}
