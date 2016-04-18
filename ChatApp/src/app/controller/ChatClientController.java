package app.controller;

import app.background.TaskClient;
import app.background.TaskServer;
import app.main.Client;
import app.main.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Azz_B on 17/04/2016.
 */
public class ChatClientController implements Initializable, TaskClient.MsgGet {

    private TaskClient taskClient;

    @FXML
    Text txt_show_msg;
    @FXML
    Button btn_sendMessage;
    @FXML
    TextField tf_message;
    @FXML public AnchorPane root;
    @FXML
    VBox content_msg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ChatClientController context = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    taskClient = new TaskClient();
                    taskClient.setMsgGetCaller(context);
                    new Thread(taskClient).start();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @FXML
    public void sendMessageAction(ActionEvent event){
        String msg = tf_message.getText().trim();
        //System.out.println("send: " + msg);
        if(!msg.equals("") && msg.length() > 0) {
            Text text = new Text(msg);
            text.setId("msg");
            TextFlow textFlow = new TextFlow(text);
            textFlow.setId("flow");
            textFlow.setTextAlignment(TextAlignment.CENTER);
            textFlow.setLineSpacing(10);

            AnchorPane pane = new AnchorPane(textFlow);
            pane.setId("pane");
            content_msg.getChildren().add(pane);
            taskClient.writeData(msg);
            tf_message.setText("");
        }else{

        }
    }

    @FXML
    public void closeAction(ActionEvent event){
        Client.stage.close();

    }

    @FXML
    public void reduiceAction(ActionEvent event){
        Client.stage.setIconified(true);
    }

    @Override
    public void getMsgFromTask(String msg) {
        System.out.println("receive :" + msg);

        Text text = new Text(msg);
        text.setId("msg");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setId("flow_by_client");
        AnchorPane pane = new AnchorPane(textFlow);
        pane.setId("pane");
        final AnchorPane pandTh = pane;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                content_msg.getChildren().add(pandTh);
            }
        });
    }
}
