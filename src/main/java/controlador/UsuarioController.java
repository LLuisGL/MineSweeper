package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

public class UsuarioController {
    private DBConnection connection = new DBConnection();
    
    public int readByUserActive(){
        connection.conectar();
        String req = "SELECT * FROM usuario WHERE activo = 1";
        int id = 0;
        PreparedStatement stmt;
        try {
            stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                id = rss.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.desconectar();
        }
        return id;
    }
    public boolean ValidacionRegistro(String user){
        this.connection.conectar();
        String req = "SELECT username FROM usuario WHERE username = ?";

        try{
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setString(1, user );
            ResultSet result = stmt.executeQuery();
            System.out.println(" retorno");

            return result.next();

        }catch(SQLException e){
              e.printStackTrace();
              return false;
        }finally {
            connection.desconectar();
        }
    }
    public int idUserConectado(){
        this.connection.conectar();
        String req = "SELECT id FROM usuario WHERE activo = 1";
        int id = 0;
        PreparedStatement stmt;
        try {
            stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                id = rss.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.desconectar();
        }
        System.out.println(id);
        return id;
    }
    public Usuario readByID(int id){
        connection.conectar();
        String req = "SELECT * FROM usuario WHERE id = ?";
        Usuario u = null;
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setInt(1, id);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                int ID = rss.getInt("id");
                String nombre = rss.getString("username");
                String password = rss.getString("contrasena");
                int activo = rss.getInt("activo");
                u = new Usuario(ID, nombre, password, activo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    public String SesionIniciadaU(){
        this.connection.conectar();
        String nombreUsuaI = "";
        String req = "SELECT username FROM usuario WHERE activo = 1";
        
        try{
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet result = stmt.executeQuery();
            
            if(result.next()){
                nombreUsuaI = result.getString("username");
            }
            
        }catch(SQLException e){
              throw new RuntimeException();
        }finally {
            connection.desconectar();
        }
        System.out.println(nombreUsuaI);
        return nombreUsuaI;
    }
    public boolean Validacion(String user, String password){
        String req = "SELECT * FROM usuario WHERE username = ? AND  contrasena = ?";
        try{
            this.connection.conectar();
            PreparedStatement  stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setString(1, user);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();  
        } catch (Exception e) {
        e.printStackTrace();
        } finally {
            connection.desconectar();
        }
        return false;
    }
    public boolean Registro(String user, String password){
        String req = "INSERT INTO usuario (username, contrasena) VALUES (?, ?)";
         try{
            this.connection.conectar();
            PreparedStatement  stmt = this.connection.getConnection().prepareStatement(req);
            stmt.setString(1, user.trim());
            stmt.setString(2, password.trim());
            stmt.executeUpdate();
       
            return true;  
        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("no");
        }finally {
            connection.desconectar();
        }
        return false;
    }
    public boolean CerrarSesion(){
        int result = 0;
        this.connection.conectar();
        String req = "UPDATE usuario SET activo = 0";
            try{
                PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
                result = statement.executeUpdate();
            } catch(SQLException e){
              throw new RuntimeException();
            }finally {
            connection.desconectar();
            }
            return result>0; 
    } 
    public void createOrUpdateScore(int score){
        this.connection.conectar();
        int idUsuario = -1;
        int idUsuarioScore = -1;
        String req = "SELECT * FROM usuario WHERE activo = 1;";
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                idUsuario = rss.getInt("id");
            }
            req = "SELECT * FROM score WHERE idUsuario = " + idUsuario + ";";
            stmt = this.connection.getConnection().prepareStatement(req);
            rss = stmt.executeQuery();
            while(rss.next()){
                idUsuarioScore = rss.getInt("idUsuario");
            }
            if(idUsuarioScore != -1){
                req = "UPDATE score SET puntuacion = ? WHERE idUsuario = ?";
                stmt = this.connection.getConnection().prepareStatement(req);
                stmt.setInt(1, score);
                stmt.setInt(2, idUsuario);
                stmt.executeUpdate();
            }else {
                req = "INSERT INTO score(idUsuario, puntuacion, fecha) VALUES (?,?,CURRENT_DATE);";
                stmt = this.connection.getConnection().prepareStatement(req);
                stmt.setInt(1, idUsuario);
                stmt.setInt(2, score);
                stmt.executeUpdate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

