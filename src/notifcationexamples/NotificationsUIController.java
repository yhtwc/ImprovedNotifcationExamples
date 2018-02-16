/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Btn;
    @FXML
    private Button task2Btn;
    @FXML
    private Button task3Btn;
    
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
            if (task1 != null) task1.end();
            if (task2 != null) task2.end();
            if (task3 != null) task3.end();
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        task1Btn.setText("End Task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        }
        else
        {
            task1.end();
            task1 = null;
            task1Btn.setText("Start Task 1");
            textArea.appendText("Task 1 end\n");        
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1Btn.setText("Start Task 1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
//            if("End Task 2".equals(task2Btn.getText()))
//            {
//                task2Btn.setText("Start Task 2");
//            }

            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {   
                textArea.appendText(message + "\n");
                if(message.equals("Task2 done."))
                {
                    task2Btn.setText("Start Task 2");
                    task2 = null;
                }
                
            });
            
            task2.start();
            task2Btn.setText("End Task 2");
        }
        else
        {
            task2.end();
            task2 = null;
            task2Btn.setText("Start Task 2");
            textArea.appendText("Task 2 end\n");        
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                if("Task3 done.".equals((String)evt.getNewValue()))
                {
                    task3Btn.setText("Start Task 3");
                    task3 = null;
                }
            });
            
            task3.start();
            task3Btn.setText("End Task 3");
        }
        else
        {
            task3.end();
            task3 = null;
            task3Btn.setText("Start Task 3");
            textArea.appendText("Task 3 end\n");        
        }
        
    } 
}
