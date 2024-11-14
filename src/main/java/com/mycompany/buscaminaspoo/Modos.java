package com.mycompany.buscaminaspoo;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Modos {
    @FXML
    private Button btnClasico;

    @FXML
    private Button btnExplosivo;
    
    @FXML
    public void volver() throws IOException{
        App.setRoot("MenuPrincipal");
    }

    @FXML
    public void cambiarClasico() throws IOException{
        if(btnClasico.isHover()){
            App.setRoot("clasico");
        } else if(btnExplosivo.isHover()){
            App.setRoot("comunidad");
        }
    }
}
