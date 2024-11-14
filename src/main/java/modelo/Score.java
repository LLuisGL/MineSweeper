package modelo;
public class Score {
    private int idUsuario;
    private int score;
    private String fecha;

    public Score(int idUsuario, int score, String fecha) {
        this.idUsuario = idUsuario;
        this.score = score;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
