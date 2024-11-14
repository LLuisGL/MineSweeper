package com.mycompany.buscaminaspoo;

import controlador.GridPaneController;
import controlador.UsuarioController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import modelo.Juego;

public class creativo {
    @FXML 
    private VBox Vjuego;
    
    private final Image minaImage = new Image(getClass().getResourceAsStream("/com/mycompany/buscaminaspoo/iconos/mine.png"));
    private final Image defaultImage = new Image(getClass().getResourceAsStream("/com/mycompany/buscaminaspoo/iconos/0.png"));
    boolean seleccionbutonMina = false;
    boolean seleccionbutonLimp = false;
    UsuarioController uc = new UsuarioController();
    GridPaneController gc = new GridPaneController();
    
    private Integer minascolocadas;
    GridPane g;
    int numeroJ = 0;
    
    @FXML
    public void setearMapa4x4() throws FileNotFoundException{
        setearMapaxTamano(4, 50);
        numeroJ = 4;
    }
    
    @FXML
    public void setearMapa6x6() throws FileNotFoundException{
        setearMapaxTamano(6, 60);
        numeroJ = 6;    
    }
    
    @FXML
    public void setearMapa10x10() throws FileNotFoundException{
        setearMapaxTamano(10, 43);
        numeroJ = 10;
    }
    
    @FXML
    private void colocarMina(){
        seleccionbutonMina = !seleccionbutonMina;
        seleccionbutonLimp = false;
        System.out.println(seleccionbutonMina);
  
    }
    
    @FXML
    private void limpiarcelda(){
        seleccionbutonLimp  = !seleccionbutonLimp;
        seleccionbutonMina = false;
        System.out.println(seleccionbutonLimp);
    }
    
    @FXML
    public void volver() throws IOException{
        App.setRoot("menuPrincipal");
    }
    
    @FXML
    public void create() throws IOException{
        if(uc.readByUserActive() != 0){
            String coordenadas = leerMatriz();
            int idUsuario = uc.readByUserActive();
            Juego j = new Juego(idUsuario, coordenadas);
            gc.Create(j);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EDITOR");
            alert.setHeaderText("Crear mapa");
            alert.setContentText("Se ha creado el mapa!");
            alert.showAndWait();
            App.setRoot("menuPrincipal");
        } else{
            System.out.println("No hay usuarios activos");
        }
    }
    
    public String leerMatriz(){
        String coordenadas = "";
        for(int i = 0; i<numeroJ; i++){
            for(int j = 0; j<numeroJ; j++){
               String objeto = ((Label)getNodeByRowColumnIndex(i,j,this.g)).getText();
               coordenadas = coordenadas + String.valueOf(i) + String.valueOf(j) + objeto + " ";
            }
        }
        return coordenadas;
    }
    
     public  void handleCellClick(MouseEvent event, int y, int x, GridPane g, int tamano) throws FileNotFoundException{
          ImageView cell = (ImageView) event.getSource();
        if(seleccionbutonMina){
            if(!cell.getImage().equals(minaImage)){
            Label l = (Label)getNodeByRowColumnIndex(y, x, g);
            l.setText("m");
            minasSuma(y, x, g, cell, tamano);
            cell.setImage(minaImage);
            minascolocadas++;      
            }
        }
        else if (seleccionbutonLimp){
            if (cell.getImage().equals(minaImage)) {
            Label l = (Label)getNodeByRowColumnIndex(y, x, g);
            cell.setImage(defaultImage);
            minasResta(y, x, g, tamano);
            l.setText("0");
            minaCentral(y,x,g,tamano, l);
            minascolocadas--;
            }
        }    
        
    }
    
    public void minasSuma(int minai, int minaj, GridPane g, ImageView imagen, int tamano) throws FileNotFoundException{
        for(int i = minai-1; i<minai+2; i++){
            if((getNodeByRowColumnIndex(i, minaj, g)) != null){
                for(int j = minaj-1; j<minaj+2; j++){
                    if((getNodeByRowColumnIndex(i, j, g)) != null){
                        if((!"m".equals(((Label) getNodeByRowColumnIndex(i, j, g)).getText())) ){
                            ((Label) getNodeByRowColumnIndex(i, j, g)).setText(String.valueOf(Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText()) + 1));
                            int num = Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText());
                            String png = num + ".png";
                            FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + png);
                            Image imageObject = new Image(inStream);
                            ImageView imagenueva = new ImageView();
                            imagenueva.setImage(imageObject);
                            imagenueva.setFitHeight(tamano);
                            imagenueva.setFitWidth(tamano);
                            funcionAplastar(imagenueva, i, j, g, tamano);
                            g.add(imagenueva, j, i);
                        }
                    }
                }   
            }
        } 
    }
    
    public void minasResta(int minai, int minaj, GridPane g, int tamano) throws FileNotFoundException{
        for(int i = minai-1; i<minai+2; i++){
            if((getNodeByRowColumnIndex(i, minaj, g)) != null){
                for(int j = minaj-1; j<minaj+2; j++){
                    if((getNodeByRowColumnIndex(i, j, g)) != null){
                        if((!"m".equals(((Label) getNodeByRowColumnIndex(i, j, g)).getText()))){
                            System.out.println("Posicion j" + j);
                            System.out.println("Sin resta" + String.valueOf(Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText())));
                            System.out.println("Con resta" + String.valueOf(Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText()) - 1));
                            ((Label) getNodeByRowColumnIndex(i, j, g)).setText(String.valueOf(Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText()) - 1));
                            int num = Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText());
                            System.out.println(num);
                            if(num < 0){
                                num = 0;
                            }
                            String png = num + ".png";
                            System.out.println(png);
                            FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + png);
                            Image imageObject = new Image(inStream);
                            ImageView imagenueva = new ImageView();
                            imagenueva.setImage(imageObject);
                            imagenueva.setFitHeight(tamano);
                            imagenueva.setFitWidth(tamano);
                            funcionAplastar(imagenueva, i, j, g, tamano);
                            g.add(imagenueva, j, i);
                        }
                    }
                }   
            }
        } 
    }
    
    public void minaCentral(int minai, int minaj, GridPane g, int tamano, Label l) throws FileNotFoundException{
        int conteo = 0;
        for(int i = minai-1; i<minai+2; i++){
            if((getNodeByRowColumnIndex(i, minaj, g)) != null){
                for(int j = minaj-1; j<minaj+2; j++){
                    if((getNodeByRowColumnIndex(i, j, g)) != null) {
                       if(("m".equals(((Label) getNodeByRowColumnIndex(i, j, g)).getText()))){
                        conteo++;
                        } 
                    }
                }   
            }
        }
        String png = conteo + ".png";
        FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + png);
        Image imageObject = new Image(inStream);
        ImageView imagenueva = new ImageView();
        imagenueva.setImage(imageObject);
        imagenueva.setFitHeight(tamano);
        imagenueva.setFitWidth(tamano);
        funcionAplastar(imagenueva, minai, minaj, g, tamano);
        g.add(imagenueva, minaj, minai);
        l.setText(String.valueOf(conteo));
    }
    
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
     
    public void setearMapaxTamano(int tamanoJ, int tamanoCasilla) throws FileNotFoundException{
        minascolocadas = 0;
        Vjuego.getChildren().clear();
        
        g = new GridPane(); 
        for (int i =0 ; i < tamanoJ ; i ++ ){    
            for (int j = 0; j< tamanoJ ; j ++){
                FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/0.png");
                Label l = new Label("0");
                Image imageObject = new Image(inStream);
                ImageView imageView = new ImageView(imageObject);
                imageView.setFitWidth(tamanoCasilla);
                imageView.setFitHeight(tamanoCasilla);
                funcionAplastar(imageView, i, j, g, tamanoCasilla);
                g.add(l, j, i);
                g.add(imageView, j, i);
            }
        }
        ArrayList<ColumnConstraints> cantidadC = new ArrayList<>();
        ArrayList<RowConstraints> cantidadF = new ArrayList<>();
        for(int i=0;i<tamanoJ;i++){
            ColumnConstraints colThird = new ColumnConstraints();
            colThird.setPrefWidth(tamanoCasilla);
            cantidadC.add(colThird);
            RowConstraints rowThird = new RowConstraints();
            rowThird.setPrefHeight(tamanoCasilla);
            cantidadF.add(rowThird);
        }
        g.getColumnConstraints().addAll(cantidadC);
        g.getRowConstraints().addAll(cantidadF); 
        
        
        g.setAlignment(Pos.CENTER);
        Vjuego.getChildren().add(g);
        g.setTranslateY(0);
        
        
    }
    
    public void funcionAplastar(ImageView imageView, int y, int x, GridPane g, int tamano){
        imageView.setOnMouseClicked(event -> {
                    try {
                        handleCellClick(event, y, x, g, tamano);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
        });
    }
    
}