package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SolicitudesAmistad {
    private StringProperty id;
    private StringProperty nombreUsuarioSolicitante;
    
    public SolicitudesAmistad(int id, String nombreUsuarioSolicitante) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.nombreUsuarioSolicitante = new SimpleStringProperty(nombreUsuarioSolicitante);
    }

    public StringProperty getId() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty getNombreUsuarioSolicitante() {
        return nombreUsuarioSolicitante;
    }

    public void setNombreUsuarioSolicitante(StringProperty nombreUsuarioSolicitante) {
        this.nombreUsuarioSolicitante = nombreUsuarioSolicitante;
    }

}
