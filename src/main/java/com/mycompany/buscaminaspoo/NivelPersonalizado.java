package com.mycompany.buscaminaspoo;

import controlador.GridPaneController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class NivelPersonalizado implements Initializable{
    
    GridPaneController gc = new GridPaneController();
    @FXML
    VBox vboxJuego;
    @FXML
    private VBox vboxDificultad;
    @FXML
    private VBox vboxEmpezar;
    
    ImageView iHoras = new ImageView();
    ImageView iMinutos = new ImageView();
    ImageView iSegundos = new ImageView();
    ImageView puntos0 = new ImageView();
    ImageView puntos1 = new ImageView();
    Contador c = new Contador();
    Thread t = new Thread(c);
    
    int conteoS = 0;
    int conteoM = 0;
    int conteoH = 0;
    
    @FXML
    public void animacionContador(ImageView h, ImageView m, ImageView s) throws FileNotFoundException{
        String imagenH = conteoH + ".png";
        String imagenM = conteoM + ".png";
        String imagenS = conteoS + ".png";
        FileInputStream inStreamH = new FileInputStream("./anim/contador/" + imagenH);
        FileInputStream inStreamM = new FileInputStream("./anim/contador/" + imagenM);
        FileInputStream inStreamS = new FileInputStream("./anim/contador/" + imagenS);
        Image imageH = new Image(inStreamH);
        Image imageM = new Image(inStreamM);
        Image imageS = new Image(inStreamS);
        h.setImage(imageH);
        m.setImage(imageM);
        s.setImage(imageS);
        conteoS++;
        if(conteoS == 60){
            conteoS = 0;
            conteoM++;
            if(conteoM == 60){
                conteoM = 0;
                conteoH++;
            }
        }
    }
    
    @FXML
    private Label flag = new Label();
    
    public Node getNodeByRowColumnIndex(final int row,final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
    
    @FXML
    public void funcionAplastar(ImageView imagen, int x, int y, GridPane g, int alcance, int tamaño){
        imagen.setOnMouseClicked(e -> {
            FileInputStream inStream1 = null;
            if(!"2".equals(flag.getText())){
                if(e.getButton() == MouseButton.PRIMARY){
                    try {
                        String objeto = ((Label)getNodeByRowColumnIndex(y, x, g)).getText();
                        if("m".equals(objeto)){
                             inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/mine.png");
                        } else if(objeto.indexOf("b") == -1){
                            inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + objeto + ".png");
                        }
                        if("m".equals(objeto)){
                            flag.setText("2");
                            Label gO = new Label("Perdiste:(");
                            Button btnVolver = new Button("Volver");
                            gO.setStyle("-fx-text-fill: #fffbef;");
                            btnVolver.setOnAction(a -> {
                                try {
                                    App.setRoot("comunidad");
                                } catch (IOException ex) {
                                }
                            });
                            c.cambiarFlag(0);
                            vboxDificultad.getChildren().addAll(gO,btnVolver);
                        }
                        if (((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("b") != -1){
                            //Uso StringBuilder por el metodo deleteCharAt();
                            StringBuilder sb = new StringBuilder(((Label)getNodeByRowColumnIndex(y,x,g)).getText());
                            sb.deleteCharAt(1);
                            String numSinB = sb.toString();
                            ((Label)getNodeByRowColumnIndex(y,x,g)).setText(numSinB);
                            inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + numSinB + ".png");
                        }
                        if(flag.getText() == "0"){
                            for(int i=-(alcance);i<alcance;i++){
                                for(int j=-(alcance);j<alcance;j++){
                                    if(getNodeByRowColumnIndex(y+i, x+j, g) != null){
                                        Label imagenalado = (Label) getNodeByRowColumnIndex(y+i, x+j, g);
                                        if(!"m".equals(imagenalado.getText())){
                                            int num = Integer.parseInt(((Label) getNodeByRowColumnIndex(y+i, x+j, g)).getText());
                                            String png = num + ".png";
                                            FileInputStream inStream2 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + png);
                                            Image imageObject2 = new Image(inStream2);
                                            ImageView imagencuadro2 = new ImageView();
                                            imagencuadro2.setImage(imageObject2);
                                            imagencuadro2.setFitWidth(tamaño);
                                            imagencuadro2.setFitHeight(tamaño);
                                            g.add(imagencuadro2, x+j, y+i);
                                        } else{
                                            break;
                                        } 
                                    }
                                }
                            }
                            flag.setText("1");
                        }
                        ((Label)getNodeByRowColumnIndex(y,x,g)).setText(((Label)getNodeByRowColumnIndex(y,x,g)).getText() + "i");
                    } catch (FileNotFoundException ex) {
                    }
                } else if(e.getButton() == MouseButton.SECONDARY) {
                    if("1".equals(flag.getText())){
                        //Este if es para verificar si esta o no revelado el numero
                        if(((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("i") == -1){
                            try {
                                inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/mark.png");
                                if(((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("b") == -1){
                                    ((Label)getNodeByRowColumnIndex(y,x,g)).setText(((Label)getNodeByRowColumnIndex(y,x,g)).getText() + "b");
                                } else if(((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("b") != -1){
                                    inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/cuadrov.png");
                                    //Uso StringBuilder por el metodo deleteCharAt();
                                    StringBuilder sb = new StringBuilder(((Label)getNodeByRowColumnIndex(y,x,g)).getText());
                                    sb.deleteCharAt(1);
                                    String numSinB = sb.toString();
                                    ((Label)getNodeByRowColumnIndex(y,x,g)).setText(numSinB);
                                } else {
                                    inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/cuadrov.png");
                                }
                            } catch (FileNotFoundException ex) {
                            }
                        }
                    }
                }
                if(inStream1 != null){
                    Image imageObject1 = new Image(inStream1);
                    ImageView imagenueva = new ImageView();
                    imagenueva.setImage(imageObject1);
                    imagenueva.setFitHeight(tamaño);
                    imagenueva.setFitWidth(tamaño);
                    //Esta funcion de la pone ya que
                    funcionAplastar(imagenueva, x, y, g, 0, tamaño);
                    g.getChildren().remove(imagen);
                    g.add(imagenueva, x, y);
                }  
            }
            
        });
    }
    
    public void modoJuego(String[] lCoordenadas, int tamano, int alcance, int translateY, int translateX){
        ArrayList<ColumnConstraints> cantidadC = new ArrayList<>();
        ArrayList<RowConstraints> cantidadR = new ArrayList<>();
        for(int i=0;i<10;i++){
            ColumnConstraints colThird = new ColumnConstraints();
            colThird.setPrefWidth(tamano);
            cantidadC.add(colThird);
            RowConstraints rowThird = new RowConstraints();
            rowThird.setPrefHeight(tamano);
            cantidadR.add(rowThird);
        }
        
        GridPane g = new GridPane();
        g.getColumnConstraints().addAll(cantidadC);
        g.getRowConstraints().addAll(cantidadR);
        
        for(int i = 0; i<lCoordenadas.length; i++){
            System.out.println(lCoordenadas[i]);
            int y = Integer.valueOf(String.valueOf(lCoordenadas[i].charAt(0)));
            System.out.println(y);
            int x = Integer.valueOf(String.valueOf(lCoordenadas[i].charAt(1)));
            System.out.println(x);
            String objeto = String.valueOf(lCoordenadas[i].charAt(2));
            System.out.println(objeto);
            String png = objeto + "png";
            FileInputStream inStream;
            ImageView imagencuadro = new ImageView();
            try {
                inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/cuadrov.png");
                Image imageObject = new Image(inStream);
                imagencuadro.setImage(imageObject);
                imagencuadro.setFitWidth(tamano);
                imagencuadro.setFitHeight(tamano);
                if(objeto.equals("m")){
                    png = "mine.png";
                }
                funcionAplastar(imagencuadro, x, y, g, alcance, tamano);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NivelPersonalizado.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.setAlignment(Pos.CENTER);
            g.add(new Label(objeto), x, y);
            g.add(imagencuadro, x, y);
            g.setTranslateY(translateY);
            g.setTranslateX(translateX);
        }
        vboxEmpezar.getChildren().add(flag);
        vboxJuego.getChildren().add(g);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HBox hboxContador = new HBox();
        hboxContador.setPrefHeight(100);
        hboxContador.setAlignment(Pos.CENTER);
        iHoras.setFitHeight(50);
        iHoras.setFitWidth(50);
        iMinutos.setFitHeight(50);
        iMinutos.setFitWidth(50);
        iSegundos.setFitHeight(50);
        iSegundos.setFitWidth(50);
        FileInputStream inStream0 = null;
        try {
            inStream0 = new FileInputStream("./anim/contador/puntos.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NivelPersonalizado.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileInputStream inStream1 = null;
        try {
            inStream1 = new FileInputStream("./anim/contador/puntos.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NivelPersonalizado.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image imagenPuntos0 = new Image(inStream0);
        Image imagenPuntos1 = new Image(inStream1);
        puntos0.setImage(imagenPuntos0);
        puntos1.setImage(imagenPuntos1);
        puntos0.setFitHeight(20);
        puntos0.setFitWidth(4);
        puntos1.setFitHeight(20);
        puntos1.setFitWidth(4);
        c.cargarContadorS(this.iSegundos);
        c.cargarContadorM(this.iMinutos);
        c.cargarContadorH(this.iHoras);
        hboxContador.getChildren().addAll(iHoras, puntos0, iMinutos, puntos1, iSegundos);
        vboxDificultad.getChildren().addAll(hboxContador);
        t.start();
        flag.setText("0");
        flag.setDisable(true);
        flag.setOpacity(0);
        String coordenadas = gc.getCoords();
        String[] lCoordenadas = coordenadas.split(" ");
        
        if(lCoordenadas.length > 80){
            modoJuego(lCoordenadas, 43, 3, -10, 10);
        } else if(lCoordenadas.length > 30){
            modoJuego(lCoordenadas, 60, 2, 50, 80);
        } else {
            modoJuego(lCoordenadas, 50, 2, 130, 160);
        }
        
    }
    
}
