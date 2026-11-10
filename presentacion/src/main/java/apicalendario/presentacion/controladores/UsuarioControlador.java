package apicalendario.presentacion.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apicalendario.core.servicios.IUsuarioServicio;
import apicalendario.dominio.dtos.UsuarioLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Login y obtención del token de seguridad")
public class UsuarioControlador {

    private final IUsuarioServicio servicio;

    public UsuarioControlador(IUsuarioServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/validar/{nombreUsuario}/{clave}")
    @Operation(summary = "Valida las credenciales y retorna el usuario con su token Bearer")
    public UsuarioLoginDto validar(@PathVariable String nombreUsuario, @PathVariable String clave) {
        return servicio.validar(nombreUsuario, clave);
    }
}
