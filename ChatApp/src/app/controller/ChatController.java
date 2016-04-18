package app.controller;


import app.background.TaskServer;
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

public class ChatController implements Initializable, TaskServer.MsgGet {

    private TaskServer taskServer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final ChatController context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    taskServer = new TaskServer();
                    taskServer.setMsgGetCaller(context);
                    new Thread(taskServer).start();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /*public void setTaskServer(TaskServer taskServer){
        this.taskServer = taskServer;
    }*/



    @FXML Text txt_show_msg;
    @FXML Button btn_sendMessage;
    @FXML TextField tf_message;
    @FXML public AnchorPane root;
    @FXML VBox content_msg;

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
            taskServer.writeData(msg);
            tf_message.setText("");
        }else{

        }
    }

    @FXML
    public void closeAction(ActionEvent event){
        Main.stage.close();
        taskServer.closeServer();
    }

    @FXML
    public void reduiceAction(ActionEvent event){
        Main.stage.setIconified(true);
    }

    //implementation when receive message
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
