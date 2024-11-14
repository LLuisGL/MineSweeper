package com.mycompany.buscaminaspoo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Clasico implements Initializable {
    @FXML
    private VBox vboxJuego;
    @FXML
    private VBox vboxEmpezar;
    @FXML
    private VBox vboxDificultad;
    
    Label f = new Label();
    Label lnumeroEncontrado = new Label();
    
    ImageView iHoras = new ImageView();
    ImageView iMinutos = new ImageView();
    ImageView iSegundos = new ImageView();
    ImageView puntos0 = new ImageView();
    ImageView puntos1 = new ImageView();
    Contador c = new Contador();
    Thread t = new Thread(c);
    int score = 595959;
    
    @FXML
    public void volver() throws IOException{
        App.setRoot("jugar");
    }
    
    @FXML
    public void modoFacil() throws FileNotFoundException{
        int mina1i, mina1j, mina2i, mina2j, mina3i, mina3j;
        mina1i = mina1j = mina2i = mina2j = mina3i = mina3j = -1;
        int[] minas = {mina1i, mina1j, mina2i, mina2j, mina3i, mina3j};
        ponerModo(4, 50, minas, 2, 108);
    }
    
    
    @FXML
    public void modoMedio() throws FileNotFoundException{
        int mina1i, mina1j, mina2i, mina2j, mina3i, mina3j, mina4i, mina4j, mina5i, mina5j, mina6i, mina6j;
        mina1i = mina1j = mina2i = mina2j = mina3i = mina3j = mina4i = mina4j = mina5i = mina5j = mina6i = mina6j = -1;
        int[] minas = {mina1i, mina1j, mina2i, mina2j, mina3i, mina3j, mina4i, mina4j, mina5i, mina5j, mina6i, mina6j};
        ponerModo(6,60,minas,2,29);
    }
    
    @FXML
    public void modoDificil() throws FileNotFoundException{
        int mina1i, mina1j, mina2i, mina2j, mina3i, mina3j, mina4i, mina4j, mina5i, mina5j, mina6i, mina6j, mina7i, mina7j, mina8i, mina8j, mina9i, mina9j, mina10i, mina10j, mina11i, mina11j, mina12i, mina12j, mina13i, mina13j, mina14i, mina14j, mina15i, mina15j, mina16i, mina16j;
        mina1i = mina1j = mina2i = mina2j = mina3i = mina3j = mina4i = mina4j = mina5i = mina5j = mina6i = mina6j = mina7i = mina7j = mina8i = mina8j = mina9i = mina9j = mina10i = mina10j = mina11i = mina11j = mina12i = mina12j = mina13i = mina13j = mina14i = mina14j = mina15i = mina15j = mina16i = mina16j = -1;
        int[] minas = {mina1i, mina1j, mina2i, mina2j, mina3i, mina3j, mina4i, mina4j, mina5i, mina5j, mina6i, mina6j, mina7i, mina7j, mina8i, mina8j, mina9i, mina9j, mina10i, mina10j, mina11i, mina11j, mina12i, mina12j, mina13i, mina13j, mina14i, mina14j, mina15i, mina15j, mina16i, mina16j};
        ponerModo(10, 43, minas, 3, -7);
    }
    
    //Funcion para disenar modos
    public void ponerModo(int tamanoJ, int tamanoCasilla, int[] minas, int alcanceIni, int translateY) throws FileNotFoundException{
        if (vboxJuego.getChildren() != null){
            vboxJuego.getChildren().clear();
        }
        
        ArrayList<ColumnConstraints> cantidadC = new ArrayList<>();
        ArrayList<RowConstraints> cantidadR = new ArrayList<>();
        
        for(int i=0;i<tamanoJ;i++){
            ColumnConstraints colThird = new ColumnConstraints();
            colThird.setPrefWidth(tamanoCasilla);
            cantidadC.add(colThird);
            RowConstraints rowThird = new RowConstraints();
            rowThird.setPrefHeight(tamanoCasilla);
            cantidadR.add(rowThird);
        }
        
        GridPane g = new GridPane();
        g.getColumnConstraints().addAll(cantidadC);
        g.getRowConstraints().addAll(cantidadR);
        
        while(condicionalesRepetidas(minas)){
            for(int i=0;i<minas.length;i++){
                minas[i] = (int) Math.floor(Math.random()*tamanoJ);
            }
        }
        
        for(int i=0;i<minas.length;i=i+2){
            g.add(new Label("m"), minas[i+1], minas[i]);
        }
        
        for(int i=0; i<tamanoJ; i++){
            for(int j=0; j<tamanoJ; j++){
                if((getNodeByRowColumnIndex(i, j, g)) != null){
                } else{
                    g.add(new Label("0"), j, i);
                }
            }
        }
        
        for(int i=0;i<minas.length;i=i+2){
            minasSuma(minas[i], minas[i+1], g);
        }
        for(int i=0;i<tamanoJ;i++){
            for(int j=0;j<tamanoJ;j++){
                if(!"m".equals(((Label) getNodeByRowColumnIndex(i, j, g)).getText()) ){
                    int num = Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText());
                    String png = num + ".png";
                    FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/cuadrov.png");
                    Image imageObject = new Image(inStream);
                    ImageView imagencuadro = new ImageView();
                    imagencuadro.setImage(imageObject);
                    imagencuadro.setFitWidth(tamanoCasilla);
                    imagencuadro.setFitHeight(tamanoCasilla);
                    g.add(imagencuadro, j, i);
                    funcionAplastar(imagencuadro,png ,i,j,g, alcanceIni, tamanoCasilla, minas.length/2); 

                } else{
                    FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/cuadrov.png");
                    Image imageObject = new Image(inStream);
                    ImageView imagenmina = new ImageView();
                    imagenmina.setImage(imageObject);
                    imagenmina.setFitWidth(tamanoCasilla);
                    imagenmina.setFitHeight(tamanoCasilla);
                    g.add(imagenmina, j, i);
                    String png = "mine.png";
                    funcionAplastar(imagenmina,png ,i,j,g, alcanceIni, tamanoCasilla, minas.length);
                }
            }
        }
        g.setAlignment(Pos.CENTER);
        f.setOpacity(0);
        f.setText("0");
        vboxJuego.getChildren().add(g);
        vboxJuego.getChildren().add(vboxEmpezar);
        vboxJuego.getChildren().add(f);
        g.setTranslateY(250);
        vboxEmpezar.setTranslateY(-200+translateY);
        Button btn = (Button)vboxEmpezar.getChildren().get(0);
        btn.setPrefWidth(150);
        btn.setDisable(false);
        btn.setOnAction(e -> {
            vboxJuego.getChildren().remove(1);
            try {
                stats(String.valueOf(minas.length));
            } catch (FileNotFoundException ex) {
            }
            g.setTranslateY(0);
        });
    }
    
    //Obtiene el (Node) de una fila y columna del Gridpane
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
    
    //Suma una casilla en concreto dependiendo de cuantas minas hay alrededor
    public void minasSuma(int minai, int minaj, GridPane g){
        for(int i = minai-1; i<minai+2; i++){
            if((getNodeByRowColumnIndex(i, minaj, g)) != null){
                for(int j = minaj-1; j<minaj+2; j++){
                    if((getNodeByRowColumnIndex(i, j, g)) != null){
                        if((!"m".equals(((Label) getNodeByRowColumnIndex(i, j, g)).getText()))){
                            ((Label) getNodeByRowColumnIndex(i, j, g)).setText(String.valueOf(Integer.parseInt(((Label) getNodeByRowColumnIndex(i, j, g)).getText()) + 1));
                        }
                    }
                }   
            }
        } 
    }
    
    //Funcion para saber si una de las posiciones de las minas se repite
    public boolean condicionalesRepetidas(int[] minas){
        for(int i=0;i<minas.length; i = i + 2){
            for(int j=1;j<minas.length; j = j + 2){
                if(i>(minas.length - 2) || j>(minas.length - 1)){

                } else if(i == j-1 && i+1 == j){
                    
                } else if(minas[i] == minas[j-1] && minas[i+1] == minas[j]){
                    return true;
                }
            }
        }
        return false;
    }
    
    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }
    
    private int obtenerCuadrosEntrantes(GridPane g){
        int contador = 0;
        for(int i = 0; i<getRowCount(g); i++){
            for(int j = 0; j<getRowCount(g); j++){
                if(((Label)getNodeByRowColumnIndex(i,j,g)).getText().indexOf("i") != -1){
                    contador = contador + 1;
                }
            }
        }
        return contador;
    }
    
    //Funcion para que revele el numero o ponga la marca
    public void funcionAplastar(ImageView imagen, String imagenColocada, int y, int x, GridPane g, int alcance, int tamaño, int cantMinas){
        imagen.setOnMouseClicked((var e) -> {
            FileInputStream inStream1 = null;
            String flag = f.getText();
            if(!"2".equals(flag)){
                if(e.getButton() == MouseButton.PRIMARY) {
                    try {
                    inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/" + imagenColocada);
                    if("mine.png".equals(imagenColocada)){
                        f.setText("2");
                        Label gO = new Label("Perdiste:(");
                        gO.setStyle("-fx-text-fill: #fffbef;");
                        Button btnVolver = new Button("Volver");
                        btnVolver.setOnAction(a -> {
                            try {
                                App.setRoot("clasico");
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
                        HBox lminasEncontradas = (HBox) vboxDificultad.getChildren().get(2);
                        Label minasResta = new Label(String.valueOf(Integer.parseInt(((Label)lminasEncontradas.getChildren().get(1)).getText()) - 1));
                        lminasEncontradas.getChildren().remove(lminasEncontradas.getChildren().get(1));
                        //Agrega la suma de las marcas
                        lminasEncontradas.getChildren().add(minasResta);
                    }
                    if ("0".equals(flag)){
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
                                        ((Label)getNodeByRowColumnIndex(y+i,x+j,g)).setText(((Label)getNodeByRowColumnIndex(y,x,g)).getText() + "i");
                                        g.add(imagencuadro2, x+j, y+i);
                                        
                                    } else{
                                        break;
                                    } 
                                }
                            }
                        }
                        f.setText("1");
                    }
                    
                    ((Label)getNodeByRowColumnIndex(y,x,g)).setText(((Label)getNodeByRowColumnIndex(y,x,g)).getText() + "i");
                    if (obtenerCuadrosEntrantes(g) == ((getRowCount(g)*getRowCount(g)) - cantMinas)){
                        f.setText("2");
                        Label gO = new Label("Ganaste!");
                        gO.setStyle("-fx-text-fill: #fffbef;");
                        Button btnVolver = new Button("Volver");
                        btnVolver.setOnAction(a -> {
                            try {
                                System.out.println(score);
                                App.setRoot("clasico");
                            } catch (IOException ex) {
                            }
                        });
                        c.cambiarFlag(1);
                        vboxDificultad.getChildren().addAll(gO,btnVolver);
                    }
                    System.out.println("Obtenidos: " + obtenerCuadrosEntrantes(g));
                    System.out.println((getRowCount(g)*getRowCount(g)) - cantMinas);
                    } catch (FileNotFoundException ex) {
                    }
                } else if(e.getButton() == MouseButton.SECONDARY){
                    if("1".equals(flag)){
                        //Este if es para verificar si esta o no revelado el numero
                        if(((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("i") == -1){
                            try {
                                inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/mark.png");
                                HBox vbox = (HBox) vboxDificultad.getChildren().get(2);
                                int numeroMarcas = Integer.parseInt(((Label)vbox.getChildren().get(1)).getText());
                                if(((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("b") == -1 && numeroMarcas<16){
                                    ((Label)getNodeByRowColumnIndex(y,x,g)).setText(((Label)getNodeByRowColumnIndex(y,x,g)).getText() + "b");
                                    HBox lminasEncontradas = (HBox) vboxDificultad.getChildren().get(2);
                                    Label minasSuma = new Label(String.valueOf(Integer.parseInt(((Label)lminasEncontradas.getChildren().get(1)).getText()) + 1));
                                    lminasEncontradas.getChildren().remove(lminasEncontradas.getChildren().get(1));
                                    //Agrega la suma de las marcas
                                    lminasEncontradas.getChildren().add(minasSuma);
                                } else if (((Label)getNodeByRowColumnIndex(y,x,g)).getText().indexOf("b") != -1){
                                    inStream1 = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/iconos/cuadrov.png");
                                    //Uso StringBuilder por el metodo deleteCharAt();
                                    StringBuilder sb = new StringBuilder(((Label)getNodeByRowColumnIndex(y,x,g)).getText());
                                    sb.deleteCharAt(1);
                                    String numSinB = sb.toString();
                                    ((Label)getNodeByRowColumnIndex(y,x,g)).setText(numSinB);
                                    HBox lminasEncontradas = (HBox) vboxDificultad.getChildren().get(2);
                                    Label minasResta = new Label(String.valueOf(Integer.parseInt(((Label)lminasEncontradas.getChildren().get(1)).getText()) - 1));
                                    lminasEncontradas.getChildren().remove(lminasEncontradas.getChildren().get(1));
                                    //Agrega la suma de las marcas
                                    lminasEncontradas.getChildren().add(minasResta);
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
                    funcionAplastar(imagenueva, imagenColocada, y, x, g, 0, tamaño, cantMinas);
                    g.getChildren().remove(imagen);
                    g.add(imagenueva, x, y);
                }
            }
        });
    }
    
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
        
        score = score - 100;
        
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
    public void stats(String minas) throws FileNotFoundException{
        vboxDificultad.getChildren().clear();
        Label lstats = new Label("STATS");
        Label lminas = new Label("Minas: " + minas);
        HBox minasEncontradas = new HBox();
        minasEncontradas.setAlignment(Pos.CENTER);
        Label lencontrado = new Label("Encontradas: ");
        lencontrado.setStyle("-fx-text-fill: #fffbef;");
        minasEncontradas.setStyle("-fx-text-fill: #fffbef;");
        lstats.setStyle("-fx-text-fill: #fffbef;");
        lminas.setStyle("-fx-text-fill: #fffbef;");
        lnumeroEncontrado.setStyle("-fx-text-fill: #fffbef;");
        lnumeroEncontrado.setText("0");
        minasEncontradas.getChildren().add(lencontrado);
        minasEncontradas.getChildren().add(lnumeroEncontrado);
        HBox hboxContador = new HBox();
        hboxContador.setPrefHeight(100);
        hboxContador.setAlignment(Pos.CENTER);
        iHoras.setFitHeight(50);
        iHoras.setFitWidth(50);
        iMinutos.setFitHeight(50);
        iMinutos.setFitWidth(50);
        iSegundos.setFitHeight(50);
        iSegundos.setFitWidth(50);
        FileInputStream inStream0 = new FileInputStream("./anim/contador/puntos.png");
        FileInputStream inStream1 = new FileInputStream("./anim/contador/puntos.png");
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
        vboxDificultad.getChildren().addAll(lstats, lminas, minasEncontradas, hboxContador);
        t.start();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Button btn = new Button("Empezar");
        btn.setDisable(true);
        btn.setPrefHeight(50);
        btn.setPrefWidth(150);
        vboxEmpezar.setPrefHeight(500);
        vboxEmpezar.setPrefWidth(540);
        vboxEmpezar.setMinHeight(500);
        vboxEmpezar.setMinWidth(540);
        vboxEmpezar.getChildren().add(btn);
        vboxEmpezar.setAlignment(Pos.CENTER);
        vboxEmpezar.setBackground(new Background(new BackgroundFill( Color.rgb(0, 0,0,0.4), CornerRadii.EMPTY, Insets.EMPTY )));
    }
}