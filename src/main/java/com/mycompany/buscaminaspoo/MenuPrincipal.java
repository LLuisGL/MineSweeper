package com.mycompany.buscaminaspoo;

import controlador.ScoreController;
import controlador.UsuarioController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Score;

public class MenuPrincipal implements Initializable {
    // seccion del menu bar
    @FXML 
    private MenuBar menuBAR;
    @FXML 
    private Menu Perfil;
    @FXML 
    private MenuItem iniciarSesion;
    @FXML
    private MenuItem registrarse;
    @FXML
    private VBox vboxPrincipal;
    @FXML 
    private Menu Amigos;
    @FXML
    private Menu VerDatos;
    @FXML
    private Button btncreditos;

    @FXML
    private Button btnjugar;
    
    @FXML
    private Button btnconstruccion;
    
    @FXML
    private VBox vboxAnim;
    
    AnimMenu a = new AnimMenu(); 
    Thread t = new Thread(a);
    
    UsuarioController Musuario = new UsuarioController();
    
    @FXML
    public void IniciarSesion(){
     try{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
         Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setTitle("Iniciar Sesión");
         stage.setScene(scene);
         stage.showAndWait();
         String menuUsuario = Musuario.SesionIniciadaU();
         if (menuUsuario != ""){
             App.setRoot("MenuPrincipal");
         }
         
     }catch(IOException e) {
            e.printStackTrace();
        }   
    }
    @FXML
    public void Registrarse(){
      try{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
         Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setTitle("Registro");
         stage.setScene(scene);
         stage.show();
         
     }catch(IOException e) {
            e.printStackTrace();
        }      
    }
    @FXML
    public void veramigos(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListaAmigos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Lista de Amigos");
            stage.setScene(scene);
            stage.show();
        }catch(IOException e) {
            e.printStackTrace();
        }      
    }
    private void cerrarSesionUsuario(){
            Musuario.CerrarSesion();
            Perfil.getItems().clear();
            Perfil.getItems().add(iniciarSesion);
            Perfil.getItems().add(registrarse);
        
        
    }
    public void menubarDinamico(){
        String menuUsuario = Musuario.SesionIniciadaU();
        if (menuUsuario != ""){
           Perfil.getItems().clear();
           MenuItem menuIUsuario = new MenuItem(menuUsuario);
           MenuItem menuCerrar = new MenuItem ("Cerrar Sesion");
           
           menuCerrar.setOnAction(event -> cerrarSesionUsuario());
           
           Perfil.getItems().addAll(menuIUsuario,menuCerrar);
       }
        else{
            System.out.println("no haz iniciado sesion");
        }
    }
    @FXML
    public void jugar() throws IOException{
        String menuUsuario = Musuario.SesionIniciadaU();
        if (menuUsuario != ""){
            App.setRoot("jugar");
        }
        else{
            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Iniciar Sesión");
            stage.setScene(scene);
            stage.showAndWait();
            String menuUsuari = Musuario.SesionIniciadaU();
            if (menuUsuari != ""){
             App.setRoot("MenuPrincipal");
         }
         
        }catch(IOException e) {
            e.printStackTrace();
        }   
        }  
    }
    @FXML
    public void creditos() throws IOException{
        App.setRoot("creditos");
        a.setFlag(101);
    }
    @FXML
    public void editor() throws IOException {
        String menuUsuario = Musuario.SesionIniciadaU();
        if (menuUsuario != ""){
            App.setRoot("editorMapa");
        }
        else{
            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Iniciar Sesión");
            stage.setScene(scene);
            stage.showAndWait();
            String menuUsuari = Musuario.SesionIniciadaU();
            if (menuUsuari != ""){
             App.setRoot("MenuPrincipal");
         }
         
        }catch(IOException e) {
            e.printStackTrace();
        }   
        }
    }
    int contador;
    ScoreController sc = new ScoreController();
    public void animacionFondo(VBox i) throws FileNotFoundException{
        i.setTranslateY(-400);
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(i);
        translate.setDuration(Duration.millis(10000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByY(800);
        translate.setDelay(Duration.millis(1000));
        translate.play();
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Score> scores = sc.Top10();
        int contadorScore = 0;
        Label lbTitulo = new Label("BEST SCORES");
        lbTitulo.setStyle("-fx-text-fill: #fffbef;");
        for(Score score:scores){
            contadorScore++;
            HBox hbox = new HBox();
            String scoreText = contadorScore + ". " + score.getScore() + ". ";
            String nameScore = Musuario.readByID(score.getIdUsuario()).getNombre();
            Label lbname = new Label(nameScore);
            Label lb = new Label(scoreText);
            lbname.setStyle("-fx-text-fill: #ff1d1d;");
            lb.setStyle("-fx-text-fill: #fffbef;");
            hbox.getChildren().addAll(lb, lbname);
            hbox.setPrefHeight(20);
            hbox.setAlignment(Pos.CENTER);
            vboxAnim.getChildren().add(hbox);
        }
        vboxAnim.getChildren().add(lbTitulo);
        menubarDinamico();
        a.cargarMenu(this.vboxAnim);
        t.start();
        
        btnjugar.setOpacity(0);
        btncreditos.setOpacity(0);
        btnconstruccion.setOpacity(0);
        
        FadeTransition fade1 = new FadeTransition();
        fade1.setNode(btnjugar);
        fade1.setDuration(Duration.millis(2000));
        fade1.setDelay(Duration.millis(1000));
        fade1.setInterpolator(Interpolator.LINEAR);
        fade1.setFromValue(0);
        fade1.setToValue(1);
        
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(btnjugar);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByY(-5);
        translate.setAutoReverse(true);
        translate.setDelay(Duration.millis(1000));
        translate.play();
        
        FadeTransition fade2 = new FadeTransition();
        fade2.setNode(btncreditos);
        fade2.setDuration(Duration.millis(2000));
        fade2.setDelay(Duration.millis(1000));
        fade2.setInterpolator(Interpolator.LINEAR);
        fade2.setFromValue(0);
        fade2.setToValue(1);
        
        FadeTransition fade3 = new FadeTransition();
        fade3.setNode(btnconstruccion);
        fade3.setDuration(Duration.millis(2000));
        fade3.setDelay(Duration.millis(1000));
        fade3.setInterpolator(Interpolator.LINEAR);
        fade3.setFromValue(0);
        fade3.setToValue(1);
        
        fade1.play();
        fade2.play();
        fade3.play();
        
        
    }
}
