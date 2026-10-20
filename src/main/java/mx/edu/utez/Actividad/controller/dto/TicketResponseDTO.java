package mx.edu.utez.Actividad.controller.dto;


import lombok.Builder;
import lombok.Data;
import mx.edu.utez.Actividad.model.EstadoTicket;
import mx.edu.utez.Actividad.model.Prioridad;

@Data
@Builder
public class TicketResponseDTO {
    private Long id;
    private String descripcion;
    private EstadoTicket estado;
    private Prioridad prioridad;
    private Long usuarioId;
    private String nombreUsuario;
}
