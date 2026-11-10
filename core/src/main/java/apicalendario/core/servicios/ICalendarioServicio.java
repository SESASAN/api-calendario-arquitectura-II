package apicalendario.core.servicios;

import java.util.List;

import apicalendario.dominio.dtos.FestivoDto;
import apicalendario.dominio.entidades.Calendario;

public interface ICalendarioServicio {

    List<FestivoDto> obtenerFestivos(int anio);

    boolean generar(int anio);

    List<Calendario> listar(int anio);
}
