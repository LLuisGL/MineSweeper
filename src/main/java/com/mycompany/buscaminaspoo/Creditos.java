package com.mycompany.buscaminaspoo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Creditos implements Initializable {
    @FXML
    private ImageView cvC;

    @FXML
    private ImageView cvL;
    
    @FXML
    private Button btnVolver;
    
     @FXML
    private Label lblCarlos;

    @FXML
    private VBox vboxPrincipal;
    @FXML
    private Label lblLuis;
    
    @FXML
    public void animacionLuis(ImageView i) throws FileNotFoundException{
        int numeroAl = 0;
        int flag = 0;
        if(flag == 1){
            numeroAl = (int) Math.floor(Math.random()*3);
            flag = 1;
        } else {
            numeroAl = (int) Math.floor(Math.random()*3);
            flag = 0;
        }
        String imagen = "cvL" + String.valueOf(numeroAl) + ".png";
        FileInputStream inStream = new FileInputStream("./anim/creditos/Luis/" + imagen);
        Image image = new Image(inStream);
        i.setImage(image);
        
    }
    
    @FXML
    public void animacionCarlos(ImageView i) throws FileNotFoundException{
        int numeroAl = 0;
        int flag = 0;
        if(flag == 1){
            numeroAl = (int) Math.floor(Math.random()*3);
            flag = 1;
        } else {
            numeroAl = (int) Math.floor(Math.random()*3);
            flag = 0;
        }
        String imagen = "cvC" + String.valueOf(numeroAl) + ".png";
        FileInputStream inStream = new FileInputStream("./anim/creditos/Carlos/" + imagen);
        Image image = new Image(inStream);
        i.setImage(image);
        
    }
    
    @FXML
    public void egg() throws FileNotFoundException, InterruptedException{
         FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/porfi.png");
         Image imagen = new Image(inStream);
         ImageView img = new ImageView(imagen);
         img.setFitHeight(300);
         img.setFitWidth(300);
         vboxPrincipal.getChildren().clear();
         vboxPrincipal.getChildren().add(img);
    }
    
    @FXML
    public void volverPantalla() throws IOException{
        App.setRoot("menuPrincipal");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cvL.setTranslateY(400);
        cvC.setTranslateY(400);
        btnVolver.setOpacity(0);
        AnimL l = new AnimL();
        AnimC c = new AnimC(); 
        l.cargarCreditos(this.cvL);
        c.cargarCreditos(this.cvC);
        Thread t = new Thread(l);
        Thread t2 = new Thread(c);
        
        t.start();
        t2.start();
        
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(cvL);
        translate.setDuration(Duration.millis(2000));

        translate.setByY(-400);
        translate.setDelay(Duration.millis(500));
        
        TranslateTransition translate2 = new TranslateTransition();
        translate2.setNode(cvC);
        translate2.setDuration(Duration.millis(2000));

        translate2.setByY(-400);
        translate2.setDelay(Duration.millis(500));
        
        FadeTransition translate3 = new FadeTransition();
        translate3.setNode(btnVolver);
        translate3.setDuration(Duration.millis(2000));
        translate3.setDelay(Duration.millis(2000));

        translate3.setInterpolator(Interpolator.LINEAR);
        translate3.setFromValue(0);
        translate3.setToValue(1);
        
        translate.play();
        translate2.play();
        translate3.play();

    }
}
