
package com.mycompany.buscaminaspoo;


import controlador.DBConnection;
import controlador.UsuarioController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblRegister;
    
    UsuarioController usuaDb = new UsuarioController();
    DBConnection connection = new DBConnection();
    
    @FXML
    public boolean ManejoLogin() {
        boolean islogginin = false;
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        int result;
        if (usuaDb.Validacion(username, password)) {
            String req1 = "UPDATE usuario SET activo = 1 WHERE username = ?";
            try{
                this.connection.conectar();
                PreparedStatement statement = this.connection.getConnection().prepareStatement(req1);
                statement.setString(1, username);
                result = statement.executeUpdate();
                islogginin= true;
                
            } catch(SQLException e){
              throw new RuntimeException();
            } 
            showAlert("ingreso valido", "Bienvenido " + username + "!");
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
         
        } else {
            showAlert("Ingreso no valido", "credenciales no validas, porfavor intenta de nuevo.");
            
        }
        System.out.println(islogginin);
        return islogginin;
    }
    
    @FXML
    public void cambiarRegistro(){
     try{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
         Parent root = loader.load();
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setTitle("Registro");
         stage.setScene(scene);
         stage.show();
         Stage stag = (Stage) btnLogin.getScene().getWindow();
         stag.close();
     }catch(IOException e) {
            e.printStackTrace();
        }   
    }
    
    /*
    public String getUsernameI(){
        
        if(ManejoLogin()){
            String username = usuaDb.SesionIniciadaU();
            System.out.println(username);
            return username;
        }
        else{
            System.out.println(" no se pudo obtener");
            return null;
        }
    }*/
    
        private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
        }
}