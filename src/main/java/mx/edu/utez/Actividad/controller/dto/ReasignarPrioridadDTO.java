package mx.edu.utez.Actividad.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.Actividad.model.Prioridad;

@Data
public class ReasignarPrioridadDTO {

    @NotNull(message = "la nueva prioridad es obligatoria")
    private Prioridad nuevaPrioridad;
}
