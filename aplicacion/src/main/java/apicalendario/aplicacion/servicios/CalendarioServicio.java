package apicalendario.aplicacion.servicios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import apicalendario.core.excepciones.AnioNoValidoException;
import apicalendario.core.excepciones.ConflictoException;
import apicalendario.core.integracion.IFestivoServicioExterno;
import apicalendario.core.repositorios.ICalendarioRepositorio;
import apicalendario.core.repositorios.ITipoRepositorio;
import apicalendario.core.servicios.ICalendarioServicio;
import apicalendario.dominio.dtos.FestivoDto;
import apicalendario.dominio.entidades.Calendario;
import apicalendario.dominio.entidades.Tipo;

@Service
public class CalendarioServicio implements ICalendarioServicio {

    private static final Locale ESPANOL = Locale.of("es", "CO");

    private final ICalendarioRepositorio repositorio;
    private final ITipoRepositorio repositorioTipo;
    private final IFestivoServicioExterno servicioFestivos;

    public CalendarioServicio(ICalendarioRepositorio repositorio,
            ITipoRepositorio repositorioTipo,
            IFestivoServicioExterno servicioFestivos) {
        this.repositorio = repositorio;
        this.repositorioTipo = repositorioTipo;
        this.servicioFestivos = servicioFestivos;
    }

    @Override
    public List<FestivoDto> obtenerFestivos(int anio) {
        validarAnio(anio);
        return servicioFestivos.obtenerFestivos(anio);
    }

    @Override
    public boolean generar(int anio) {
        validarAnio(anio);
        Set<LocalDate> festivos = servicioFestivos.obtenerFestivos(anio).stream()
                .map(FestivoDto::fecha)
                .collect(Collectors.toSet());
        Map<Long, Tipo> tipos = cargarTipos();

        List<Calendario> dias = new ArrayList<>();
        for (LocalDate fecha = LocalDate.of(anio, 1, 1); fecha.getYear() == anio; fecha = fecha.plusDays(1)) {
            Tipo tipo = tipos.get(clasificar(fecha, festivos));
            dias.add(new Calendario(0, fecha, tipo, nombreDia(fecha)));
        }

        return repositorio.reemplazarAnio(anio, dias).size() == dias.size();
    }

    @Override
    public List<Calendario> listar(int anio) {
        validarAnio(anio);
        return repositorio.listarPorAnio(anio);
    }

    static long clasificar(LocalDate fecha, Set<LocalDate> festivos) {
        if (festivos.contains(fecha)) {
            return Tipo.DIA_FESTIVO;
        }
        DayOfWeek dia = fecha.getDayOfWeek();
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY ? Tipo.FIN_DE_SEMANA : Tipo.DIA_LABORAL;
    }

    static String nombreDia(LocalDate fecha) {
        String nombre = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, ESPANOL);
        return nombre.substring(0, 1).toUpperCase(ESPANOL) + nombre.substring(1);
    }

    private Map<Long, Tipo> cargarTipos() {
        Map<Long, Tipo> tipos = repositorioTipo.listar().stream()
                .collect(Collectors.toMap(Tipo::getId, tipo -> tipo));
        for (long id : new long[] { Tipo.DIA_LABORAL, Tipo.FIN_DE_SEMANA, Tipo.DIA_FESTIVO }) {
            if (!tipos.containsKey(id)) {
                throw new ConflictoException("Falta el tipo de día " + id + " en la base de datos; no se puede generar el calendario");
            }
        }
        return tipos;
    }

    private static void validarAnio(int anio) {
        if (anio < 1 || anio > 9999) {
            throw new AnioNoValidoException(anio);
        }
    }
}
