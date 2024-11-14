package com.mycompany.buscaminaspoo;

import controlador.DBConnection;
import controlador.UsuarioController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.SolicitudesAmistad;

public class Solicitudes implements Initializable{
    @FXML
    private TableView<SolicitudesAmistad> solicitudes;
    @FXML 
    TableColumn <SolicitudesAmistad, String> ccodigo;
    
    @FXML
    private TableColumn<SolicitudesAmistad, String> nombreUsuarioA;

    @FXML
    private Button btnaceptar;

    @FXML
    private Button btbrechazar;

    private ObservableList<SolicitudesAmistad> solicitudesPendientes;
    private DBConnection connection = new DBConnection();
    UsuarioController Musuario = new UsuarioController();

    private void cargarSolicitudesPendientes() {
        solicitudesPendientes = FXCollections.observableArrayList();
        this.connection.conectar();
        int usuarioSoli = Musuario.idUserConectado();
        
        String req = "SELECT SolicitudAmistad.IdSolicitud, usuario.username FROM SolicitudAmistad JOIN usuario ON SolicitudAmistad.IdUsuarioS = usuario.id  WHERE SolicitudAmistad.IdUsuarioR = ? AND SolicitudAmistad.EstadoSolicitud ='P' ";
            
            try{
                PreparedStatement stmt1 = this.connection.getConnection().prepareStatement(req);
                stmt1.setInt(1, usuarioSoli);
                ResultSet rs = stmt1.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("idSolicitud");
                    String nombreUsuario = rs.getString("username");
                    solicitudesPendientes.add(new SolicitudesAmistad(id, nombreUsuario));
                }
            }catch (Exception e) {
                e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "No se pudieron cargar las solicitudes pendientes: " + e.getMessage());
        }
        solicitudes.setItems(solicitudesPendientes);
    }
    
    public void aceptarSolicitud() {
        
        SolicitudesAmistad solicitudSeleccionada = solicitudes.getSelectionModel().getSelectedItem();
        StringProperty idProperty = solicitudSeleccionada.getId();
        StringProperty NombreUsuarioProperty = solicitudSeleccionada.getNombreUsuarioSolicitante();
        String idString = idProperty.get();
        String NombreUString = NombreUsuarioProperty.get();
        System.out.println(idString);
        System.out.println(NombreUString);
        int usuarioSoli = Musuario.idUserConectado();

        this.connection.conectar();
        
        if (solicitudSeleccionada == null) {
            showAlert(AlertType.WARNING, "Advertencia", "Por favor seleccione una solicitud.");
            return;
        }
        
        // Actualizar estado de la solicitud a "A"
        String req = "UPDATE SolicitudAmistad SET EstadoSolicitud = 'A' WHERE IdSolicitud = ?";
        try {
 
            PreparedStatement Stmt = this.connection.getConnection().prepareStatement(req); 
            Stmt.setInt(1, Integer.parseInt(idString));
            Stmt.executeUpdate();
            // sacar el  id usuario del amigo
            String reqU = "SELECT usuario.id FROM usuario JOIN SolicitudAmistad on SolicitudAmistad.IdUsuarioS = usuario.id WHERE username = ? AND idUsuarioR = ?";
            try{
                PreparedStatement StmtU = this.connection.getConnection().prepareStatement(reqU);
                StmtU.setString(1,NombreUString);
                StmtU.setInt(2,usuarioSoli);
                ResultSet rs = StmtU.executeQuery();
                
                if (!rs.next()) {
                showAlert(AlertType.ERROR, "Error", "El id del usuario no existe.");
                return;
                }
                int idUsuarioAmig = rs.getInt("usuario.id");
                
                
                // Insertar en la tabla amigos
            String req1 = "INSERT INTO amigos (idUsuario, idUsuarioAmigo, nombreAmigo) VALUES (?, ?, ?)";
                try {
                PreparedStatement Stmt1 = this.connection.getConnection().prepareStatement(req1);
                Stmt1.setInt(1, usuarioSoli); 
                Stmt1.setInt(2, idUsuarioAmig);
                Stmt1.setString(3, NombreUString );
                Stmt1.executeUpdate();
                
                }catch(SQLException e){
                e.printStackTrace();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            solicitudesPendientes.remove(solicitudSeleccionada);
            showAlert(AlertType.INFORMATION, "Éxito", "Solicitud aceptada y amigo añadido.");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "No se pudo aceptar la solicitud: " + e.getMessage());
        }
    }
    public void rechazarSolicitud() {
        SolicitudesAmistad solicitudSeleccionada = solicitudes.getSelectionModel().getSelectedItem();
        StringProperty idProperty = solicitudSeleccionada.getId();
        String idString = idProperty.get();
        this.connection.conectar();
        
        if (solicitudSeleccionada == null) {
            showAlert(AlertType.WARNING, "Advertencia", "Por favor seleccione una solicitud.");
            return;
        }
        String reqD = "UPDATE SolicitudAmistad SET EstadoSolicitud = 'N' WHERE IdSolicitud = ?";
        try{
            PreparedStatement Stmt1 = this.connection.getConnection().prepareStatement(reqD);
            Stmt1.setInt(1, Integer.parseInt(idString));
            Stmt1.executeUpdate();
            solicitudesPendientes.remove(solicitudSeleccionada);
            showAlert(AlertType.INFORMATION, "Éxito", "Solicitud rechazada.");
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "No se pudo rechazar la solicitud: " + e.getMessage());
        }
      
    }
    


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
    cargarSolicitudesPendientes();
    ccodigo.setCellValueFactory(cellData -> cellData.getValue().getId());
    nombreUsuarioA.setCellValueFactory(cellData -> cellData.getValue().getNombreUsuarioSolicitante());
    }
}
