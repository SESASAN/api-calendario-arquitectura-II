package apicalendario.infraestructura.persistencia.mapeadores;

import apicalendario.dominio.entidades.Tipo;
import apicalendario.infraestructura.persistencia.entidades.TipoEntidad;

public final class TipoMapeador {

    private TipoMapeador() {
    }

    public static Tipo aDominio(TipoEntidad entidad) {
        return entidad == null ? null : new Tipo(entidad.getId(), entidad.getTipo());
    }

    public static TipoEntidad aEntidad(Tipo tipo) {
        if (tipo == null) {
            return null;
        }
        return new TipoEntidad(tipo.getId() > 0 ? tipo.getId() : null, tipo.getTipo());
    }
}
