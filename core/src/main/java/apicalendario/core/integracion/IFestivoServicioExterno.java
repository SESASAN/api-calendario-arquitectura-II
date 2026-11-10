package apicalendario.core.integracion;

import java.util.List;

import apicalendario.dominio.dtos.FestivoDto;

public interface IFestivoServicioExterno {

    List<FestivoDto> obtenerFestivos(int anio);
}
