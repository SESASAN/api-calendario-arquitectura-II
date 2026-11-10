package apicalendario.infraestructura.persistencia.mapeadores;

import apicalendario.dominio.entidades.Calendario;
import apicalendario.infraestructura.persistencia.entidades.CalendarioEntidad;

public final class CalendarioMapeador {

    private CalendarioMapeador() {
    }

    public static Calendario aDominio(CalendarioEntidad entidad) {
        return new Calendario(entidad.getId() == null ? 0 : entidad.getId(), entidad.getFecha(),
                TipoMapeador.aDominio(entidad.getTipo()), entidad.getDescripcion());
    }

    public static CalendarioEntidad aEntidad(Calendario calendario) {
        Long id = calendario.getId() > 0 ? calendario.getId() : null;
        return new CalendarioEntidad(id, calendario.getFecha(),
                TipoMapeador.aEntidad(calendario.getTipo()), calendario.getDescripcion());
    }
}
