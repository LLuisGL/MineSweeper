package controlador;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

        
public class DBConnection {
   private Connection connection = null;
   public boolean conectar(){
       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/buscaminas", "root","admin");
           return true;
       } catch(ClassNotFoundException|SQLException e){
           e.printStackTrace();
           System.exit(1);
           return false;
       }
   }
   public void desconectar(){
       try{
           connection.close();
       } catch (SQLException ex){
           Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE,null, ex);
       }
   }
   public Connection getConnection(){
       return connection;
   }
}
