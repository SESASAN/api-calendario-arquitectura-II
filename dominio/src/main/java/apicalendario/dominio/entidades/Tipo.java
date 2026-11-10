package apicalendario.dominio.entidades;

public class Tipo {

    public static final long DIA_LABORAL = 1;
    public static final long FIN_DE_SEMANA = 2;
    public static final long DIA_FESTIVO = 3;

    private long id;
    private String tipo;

    public Tipo() {
    }

    public Tipo(long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
