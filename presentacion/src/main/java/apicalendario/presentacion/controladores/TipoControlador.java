package apicalendario.presentacion.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apicalendario.core.servicios.ITipoServicio;
import apicalendario.dominio.entidades.Tipo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tipos")
@Tag(name = "Tipos", description = "CRUD de los tipos de día")
public class TipoControlador {

    private final ITipoServicio servicio;

    public TipoControlador(ITipoServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista los tipos de día")
    public List<Tipo> listar() {
        return servicio.listar();
    }

    @GetMapping("/obtener/{id}")
    @Operation(summary = "Obtiene un tipo de día por su id")
    public Tipo obtener(@PathVariable long id) {
        return servicio.obtener(id);
    }

    @GetMapping("/buscar/{nombre}")
    @Operation(summary = "Busca tipos de día cuyo nombre contenga el texto")
    public List<Tipo> buscar(@PathVariable String nombre) {
        return servicio.buscar(nombre);
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agrega un tipo de día")
    public Tipo agregar(@RequestBody Tipo tipo) {
        return servicio.agregar(tipo);
    }

    @PutMapping("/modificar")
    @Operation(summary = "Modifica el nombre de un tipo de día")
    public Tipo modificar(@RequestBody Tipo tipo) {
        return servicio.modificar(tipo);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina un tipo de día que no sea base ni esté en uso")
    public boolean eliminar(@PathVariable long id) {
        return servicio.eliminar(id);
    }
}
