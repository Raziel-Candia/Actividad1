package mx.edu.utez.Actividad.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.Actividad.model.Prioridad;

@Data
public class CrearTicketDTO {

    @NotBlank(message = "la descripcion no debe de estar vacia")
    private String descripcion;

    @NotNull(message = "la prioridad es obligatoria")
    private Prioridad prioridad;

    @NotNull(message = "el id es obligatorio")
    private Long usuarioId;
}
