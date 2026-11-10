package apicalendario.infraestructura.integracion;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import apicalendario.core.excepciones.ServicioFestivosException;
import apicalendario.core.integracion.IFestivoServicioExterno;
import apicalendario.dominio.dtos.FestivoDto;

@Component
public class FestivoServicioExterno implements IFestivoServicioExterno {

    private static final ParameterizedTypeReference<List<FestivoDto>> LISTA_FESTIVOS =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient cliente;

    public FestivoServicioExterno(RestClient.Builder constructor,
            @Value("${festivos.api.url}") String urlBase) {
        this.cliente = constructor.baseUrl(urlBase).build();
    }

    @Override
    public List<FestivoDto> obtenerFestivos(int anio) {
        try {
            List<FestivoDto> festivos = cliente.get()
                    .uri("/api/festivos/obtener/{anio}", anio)
                    .retrieve()
                    .body(LISTA_FESTIVOS);
            return festivos == null ? List.of() : festivos;
        } catch (RestClientException e) {
            throw new ServicioFestivosException("No fue posible obtener los festivos del año " + anio
                    + " desde la API Festivos", e);
        }
    }
}
