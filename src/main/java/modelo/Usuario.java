package modelo;
public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private int activo;

    public Usuario(int id, String nombre, String password, int activo) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.activo = activo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
