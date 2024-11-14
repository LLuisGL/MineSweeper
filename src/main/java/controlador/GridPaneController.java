package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Juego;

public class GridPaneController {
    private DBConnection connection = new DBConnection();
    public int Create(Juego j){
        connection.conectar();
        int id = 0;
        String req = "INSERT INTO juego(idUsuario, coordenadas, fecha, visitas) VALUES(?,?,CAST(CURDATE() AS CHAR), 0);";
        PreparedStatement stmt;
        try {
            stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setInt(1, j.getIdUsuario());
            stmt.setString(2, j.getCoordenadas());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            connection.desconectar();
        }
        return id;
    }
    
    public ArrayList<Juego> Read(){
        connection.conectar();
        ArrayList<Juego> juegos = new ArrayList<>();
        String req = "SELECT * FROM juego;";
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                int id = rss.getInt("id");
                int idJugador = rss.getInt("idUsuario");
                String coordenadas = rss.getString("coordenadas");
                Juego j = new Juego(idJugador, coordenadas);
                j.setFecha(rss.getString("fecha"));
                j.setVisitas(rss.getInt("visitas"));
                j.setId(id);
                juegos.add(j);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            connection.desconectar();
        }
        return juegos;
    }
    
    public void ResetearJuegos(){
        connection.conectar();
        String req = "UPDATE juego SET activo = 0;";
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            connection.desconectar();
        }
        
    }
    
    public void ActivarJuego(int id){
        connection.conectar();
        String req = "UPDATE juego SET activo = 1 WHERE id = ?;";
        PreparedStatement stmt;
        try {
            stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            connection.desconectar();
        }
        
    }
    
    public String getCoords(){
        connection.conectar();
        String req = "SELECT * FROM juego WHERE activo = 1;";
        String coordenadas = "";
        PreparedStatement stmt;
        try {
            stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                coordenadas = rss.getString("coordenadas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordenadas;
    }
    
    public void updateViews(int id, int views){
        connection.conectar();
        String req = "UPDATE juego SET visitas = ? WHERE id = ?;";
        PreparedStatement stmt;
        try {
            stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setInt(1, views);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GridPaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public ArrayList<Juego> ReadByName(String nombre){
        ArrayList<Juego> juegos = new ArrayList<>();
        String req = "SELECT * FROM juego, usuario WHERE juego.idUsuario = usuario.id AND username LIKE ?;";
        try{
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            statement.setString(1, "%" + nombre + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                int idUsuario = resultSet.getInt("idUsuario");
                String coordenadas = resultSet.getString("coordenadas");
                String fecha = resultSet.getString("fecha");
                int visitas = resultSet.getInt("visitas");
                Juego j = new Juego(idUsuario, coordenadas);
                j.setFecha(resultSet.getString("fecha"));
                j.setVisitas(resultSet.getInt("visitas"));
                j.setId(ID);
                juegos.add(j);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(this.connection != null){
                this.connection.desconectar();
            }
        }
        return juegos;
    }
}
