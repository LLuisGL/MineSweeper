package com.mycompany.buscaminaspoo;

import controlador.UsuarioController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;

public class Registro {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    
    @FXML 
    private Button Registrarse;
    
    UsuarioController usuaDb = new UsuarioController();
    @FXML
    public void ManejoRegister() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if (username.isEmpty() || password.isEmpty()){
            showAlert("Registro fallido", "No se pudo registrarse."); 
        }
        else{
            boolean usarioValidacion= usuaDb.ValidacionRegistro(username);

            if(usarioValidacion){
            showAlert("Registro fallido", "Ya existe un usuario con ese nombre.");
            return;
            }
            usuaDb.Registro(username, password);
            showAlert("Registro exitoso! ", "Usuario registrado correctamente.");
            Stage stag = (Stage) Registrarse.getScene().getWindow();
            stag.close();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
        }
}
