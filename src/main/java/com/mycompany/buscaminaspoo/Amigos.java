package com.mycompany.buscaminaspoo;

import controlador.DBConnection;
import controlador.UsuarioController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Amigo;

public class Amigos implements Initializable {
    @FXML 
    private Label NombreUsuario;
    @FXML
    private VBox vboxPerfiles;
    @FXML
    private Label estadoUsuario;
    @FXML 
    private Button buscar;
    @FXML
    private Button Agregar;
    @FXML
    private Button solicitudes;
    @FXML
    private TextField nombreUsuarioA;
    
    @FXML
    private ComboBox<String> estadoComboBox;
    @FXML
    private ComboBox<String> amigosComboBox;
    
    private final String estados = "Estados";
    UsuarioController Musuario = new UsuarioController();
    private DBConnection connection = new DBConnection();
    
    public void manejoAgregarAmigo() {
        int usuarioSoli = Musuario.idUserConectado();
        String NombreAmigo = nombreUsuarioA.getText().trim();
        this.connection.conectar();
        // validacion si el usurio esta conectado
        if(usuarioSoli == 0){
            showAlert(AlertType.ERROR, "Error", "No haz iniciado sesion.");
            return;
        }
        // validacion del texfield
        if (NombreAmigo.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "El nombre del amigo no puede estar vacío.");
            return;
        }
        // validacion de si existe el amigo
        String req1 = "SELECT id FROM usuario WHERE username = ?";
        try {
            PreparedStatement stmt1 = this.connection.getConnection().prepareStatement(req1);
            stmt1.setString(1, NombreAmigo);
            ResultSet rs1 = stmt1.executeQuery();
            System.out.println("linea de validacion 1 bien: " + NombreAmigo);
            if (!rs1.next()) {
                showAlert(AlertType.ERROR, "Error", "El usuario no existe.");
                return;
            } 
            int AmigoId = rs1.getInt("id");
            //validacion si el amigo no es el mismo, no puedes enviarte una solicitud a ti mismo
            if (usuarioSoli==AmigoId){
                showAlert(AlertType.ERROR, "Error", "No te puedes enviar solicitudes a ti mismo.");
                return;
            }
            //validacion si ya son amigos
            String reqExi = "SELECT 1 FROM amigos WHERE (idUsuario = ? AND idUsuarioAmigo = ?) OR (idUsuario = ? AND idUsuarioAmigo = ?)";
            try{
                PreparedStatement stmtexi = this.connection.getConnection().prepareStatement(reqExi);
                stmtexi.setInt(1,usuarioSoli);
                stmtexi.setInt(2,AmigoId);
                stmtexi.setInt(3,AmigoId);
                stmtexi.setInt(4,usuarioSoli);
                ResultSet rsexi = stmtexi.executeQuery();
                if (rsexi.next()){
                showAlert(AlertType.ERROR, "Error", "Ya son amigos.");
                return;
                }
            // validacion de si existe una solicitud pendiente de la misma personas
            String req2 = "SELECT * FROM SolicitudAmistad WHERE idUsuarioS = ? AND idUsuarioR = ? AND EstadoSolicitud IN ('P', 'A', 'N') ";
            try{
              PreparedStatement stmt2 = this.connection.getConnection().prepareStatement(req2);
              stmt2.setInt(1,usuarioSoli );
              stmt2.setInt(2,AmigoId);
              ResultSet rs2 = stmt2.executeQuery();
              System.out.println("linea de validacion 2 bien: "+ AmigoId +"usario solicitante: " + usuarioSoli);
              
              if (rs2.next()) {
                    showAlert(AlertType.INFORMATION, "Info", "Ya existe una solicitud para este usuario.");
                    return;
                    }
              //insercion de la solicitud de amistad
              String reqF = "INSERT INTO SolicitudAmistad (idUsuarioS,idUsuarioR) VALUES (?,?)" ;
              try{
                  PreparedStatement stmtF = this.connection.getConnection().prepareStatement(reqF);
                  stmtF.setInt(1,usuarioSoli );
                  stmtF.setInt(2,AmigoId);
                  int rsF = stmtF.executeUpdate();
                  if(rsF == 0){
                  showAlert(AlertType.ERROR, "error", "Solicitud de amistad no enviada.");
                  }
                  System.out.println("linea de validacion 3 bien");
                  showAlert(AlertType.INFORMATION, "Éxito", "Solicitud de amistad enviada.");
              }catch(SQLException e){
                e.printStackTrace();
              }
            }catch(SQLException e){
                e.printStackTrace();
            }
            }catch(SQLException e){
             e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private void ManejoComboBox(){
        estadoComboBox.getItems().addAll("Conectado", "Ausente", "Invisible");
        estadoComboBox.setOnAction(event -> {
            String seleccionEstado = estadoComboBox.getSelectionModel().getSelectedItem();
            estadoUsuario.setText(seleccionEstado);
        });
        estadoComboBox.getSelectionModel().clearSelection();
        estadoComboBox.setPromptText(estados);
    }
    private void manejoUsuario(){
    String menuUsuario = Musuario.SesionIniciadaU(); 
    if (menuUsuario != ""){
           NombreUsuario.setText(menuUsuario);
           estadoUsuario.setText("Conectado");
       }
        else{
            System.out.println("no haz iniciado sesion");
        }
    }
    
    private void ComboAmigos(){
        
    
    }
    
    public void cambioSolicitudes() {
        String menuUsuario = Musuario.SesionIniciadaU();
        if (menuUsuario != "") {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("solicitudes.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Solicitudes de Amistad");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(" no hay sesion iniciada");
        }
    }
    
    
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        int usuarioSoli = Musuario.idUserConectado();
        this.connection.conectar();
        ArrayList<Amigo> amigos = new ArrayList<>();
        String CmbA = "SELECT * FROM amigos JOIN usuario ON (amigos.IdUsuarioAmigo = usuario.id OR amigos.IdUsuario = usuario.id) WHERE (amigos.IdUsuario = ? OR amigos.IdUsuarioAmigo = ?)  AND usuario.id != ? AND amigos.estado = 1";
        try{
            PreparedStatement stmt1 = this.connection.getConnection().prepareStatement(CmbA);
            stmt1.setInt(1, usuarioSoli);
            stmt1.setInt(2, usuarioSoli);
            stmt1.setInt(3, usuarioSoli);
            ResultSet rs = stmt1.executeQuery();

            while (rs.next()) {
                 String nombreAmigo = rs.getString("username");
                 String estadoAmigo = rs.getString("estado");
                 Amigo am = new Amigo(nombreAmigo, estadoAmigo);
                 amigos.add(am);
            }
        }catch(SQLException e){
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los amigos: " + e.getMessage());
        }
        ManejoComboBox();
        manejoUsuario();
        for(Amigo a:amigos){
            HBox amigoHBox = new HBox();
            ImageView fotoP = new ImageView();
            amigoHBox.setAlignment(Pos.CENTER_LEFT);
            try {
                FileInputStream inStream = new FileInputStream("./src/main/resources/com/mycompany/buscaminaspoo/comunidad/pfp.png");
                Image imagen = new Image(inStream);
                fotoP.setImage(imagen);
                fotoP.setFitHeight(60);
                fotoP.setFitWidth(60);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Amigos.class.getName()).log(Level.SEVERE, null, ex);
            }
            VBox infoAmigo = new VBox();
            infoAmigo.setAlignment(Pos.CENTER_LEFT);
            infoAmigo.setPadding(new Insets(0,0,0,10));
            Label nombreAmigo = new Label(a.getNombre());
            Label estadoAmigo = new Label("Desconectado");
            nombreAmigo.setStyle("-fx-text-fill: #fffbef;");
            estadoAmigo.setStyle("-fx-text-fill: #fffbef;");
            infoAmigo.getChildren().addAll(nombreAmigo, estadoAmigo);
            amigoHBox.getChildren().addAll(fotoP, infoAmigo);
            vboxPerfiles.getChildren().add(amigoHBox);
        }
        
    }
    
}
