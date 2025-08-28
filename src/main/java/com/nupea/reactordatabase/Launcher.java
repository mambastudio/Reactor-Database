/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jmburu
 */
public class Launcher extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReactorDatabase.fxml"));
        Parent root = loader.load();       
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Reactor Database");
        stage.setScene(scene);
        stage.show();
    }
    
}
