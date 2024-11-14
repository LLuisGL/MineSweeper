package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Score;

public class ScoreController {
    DBConnection connection = new DBConnection();
    public ArrayList<Score> Top10(){
        connection.conectar();
        ArrayList<Score> scores = new ArrayList<>();
        String req = "SELECT * FROM score ORDER BY puntuacion ASC LIMIT 15";
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(req);
            ResultSet rss = stmt.executeQuery();
            while(rss.next()){
                int idUsuario = rss.getInt("idUsuario");
                int score = rss.getInt("puntuacion");
                String fecha = rss.getString("fecha");
                Score sc = new Score(idUsuario, score, fecha);
                scores.add(sc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }
}
