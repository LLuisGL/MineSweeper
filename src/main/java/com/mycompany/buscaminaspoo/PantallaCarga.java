package com.mycompany.buscaminaspoo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PantallaCarga implements Initializable{
    @FXML
    private ImageView imageBomb;
    
    @FXML
    public void changeView() throws IOException{
        App.setRoot("menuPrincipal");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(imageBomb);
        translate.setDuration(Duration.millis(500));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByY(-40);
        translate.setAutoReverse(true);
        translate.setDelay(Duration.millis(1000));
        translate.play();
        
        Thread t = new Thread(new Carga());
        t.start();
    }
}
