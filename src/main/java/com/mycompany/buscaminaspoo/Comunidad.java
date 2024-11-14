package com.mycompany.buscaminaspoo;

import controlador.GridPaneController;
import controlador.UsuarioController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Juego;

public class Comunidad implements Initializable {

    
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField buscarText;
    @FXML
    VBox vboxJuegos;
    
    GridPaneController gc = new GridPaneController();
    UsuarioController uc = new UsuarioController();
    
    @FXML
    public void volver() throws IOException{
        App.setRoot("jugar");
    }
    
    public void presionar(Button b, Juego j){
        b.setOnMouseClicked(e->{
                try {
                    gc.ActivarJuego(j.getId());
                    gc.updateViews(j.getId(), j.getVisitas()+1);
                    App.setRoot("nivelComunidad");
                } catch (IOException ex) {
                    Logger.getLogger(Comunidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
    }
    
    @FXML
    public void buscar(){
        vboxJuegos.getChildren().clear();
        String texto = buscarText.getText();
        ArrayList<Juego> juegos = gc.ReadByName(texto);
        int contador = 0;
        int size = 348;
        for(Juego j:juegos){
            
            if(contador>=2){
                size = size + 120;
                vboxJuegos.setPrefHeight(size);
                anchor.setPrefHeight(size);
            }
            
            HBox hboxJuego = new HBox();
            VBox infoJuego = new VBox();
            Button btnJugar = new Button("Jugar");
            presionar(btnJugar, j);
            Label nombreJ = new Label(uc.readByID(j.getIdUsuario()).getNombre());
            Label fecha = new Label(j.getFecha());
            Label visitas = new Label("Visitas: " + String.valueOf(j.getVisitas()));
            Label dificultad = new Label();
            String coordenadas = j.getCoordenadas();
            String png = "";
            if(coordenadas.length()>300){
                dificultad.setText("Dificil");
                png = "./src/main/resources/com/mycompany/buscaminaspoo/comunidad/dificil.png";
            } else if(coordenadas.length()>100){
                dificultad.setText("Medio");
                png = "./src/main/resources/com/mycompany/buscaminaspoo/comunidad/medio.png";
            } else{
                dificultad.setText("Facil");
                png = "./src/main/resources/com/mycompany/buscaminaspoo/comunidad/facil.png";
            }
            FileInputStream inStream;
            ImageView imagen = new ImageView();
            try {
                inStream = new FileInputStream(png);
                Image imageObject = new Image(inStream);
                imagen = new ImageView(imageObject);
                imagen.setFitWidth(100);
                imagen.setFitHeight(100);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Comunidad.class.getName()).log(Level.SEVERE, null, ex);
            }
            infoJuego.getChildren().addAll(nombreJ, dificultad, fecha, visitas);
            infoJuego.setPadding(new Insets(0,0,0,10));
            hboxJuego.getChildren().addAll(imagen, infoJuego, btnJugar);
            hboxJuego.setPadding(new Insets(10,10,10,10));
            hboxJuego.setAlignment(Pos.CENTER_LEFT);
            vboxJuegos.getChildren().add(hboxJuego);
            
            contador++;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Juego> juegos = gc.Read();
        gc.ResetearJuegos();
        int contador = 0;
        int size = 348;
        for(Juego j:juegos){
            
            if(contador>=2){
                size = size + 120;
                vboxJuegos.setPrefHeight(size);
                anchor.setPrefHeight(size);
            }
            
            HBox hboxJuego = new HBox();
            VBox infoJuego = new VBox();
            Button btnJugar = new Button("Jugar");
            presionar(btnJugar, j);
            Label nombreJ = new Label(uc.readByID(j.getIdUsuario()).getNombre());
            Label fecha = new Label(j.getFecha());
            Label visitas = new Label("Visitas: " + String.valueOf(j.getVisitas()));
            Label dificultad = new Label();
            String coordenadas = j.getCoordenadas();
            String png = "";
            if(coordenadas.length()>300){
                dificultad.setText("Dificil");
                png = "./src/main/resources/com/mycompany/buscaminaspoo/comunidad/dificil.png";
            } else if(coordenadas.length()>100){
                dificultad.setText("Medio");
                png = "./src/main/resources/com/mycompany/buscaminaspoo/comunidad/medio.png";
            } else{
                dificultad.setText("Facil");
                png = "./src/main/resources/com/mycompany/buscaminaspoo/comunidad/facil.png";
            }
            FileInputStream inStream;
            ImageView imagen = new ImageView();
            try {
                inStream = new FileInputStream(png);
                Image imageObject = new Image(inStream);
                imagen = new ImageView(imageObject);
                imagen.setFitWidth(100);
                imagen.setFitHeight(100);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Comunidad.class.getName()).log(Level.SEVERE, null, ex);
            }
            infoJuego.getChildren().addAll(nombreJ, dificultad, fecha, visitas);
            infoJuego.setPadding(new Insets(0,0,0,10));
            hboxJuego.getChildren().addAll(imagen, infoJuego, btnJugar);
            hboxJuego.setPadding(new Insets(10,10,10,10));
            hboxJuego.setAlignment(Pos.CENTER_LEFT);
            vboxJuegos.getChildren().add(hboxJuego);
            
            contador++;
        }
    }
}
