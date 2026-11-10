package apicalendario.dominio.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {

    private long id;
    private String usuario;
    private String nombre;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clave;

    private String roles;

    public Usuario() {
    }

    public Usuario(long id, String usuario, String nombre, String clave, String roles) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
