package mx.edu.utez.Actividad.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.Actividad.controller.dto.CrearTicketDTO;
import mx.edu.utez.Actividad.controller.dto.ReasignarPrioridadDTO;
import mx.edu.utez.Actividad.controller.dto.TicketResponseDTO;
import mx.edu.utez.Actividad.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TickertController {


    private final TicketService ticketService;


    @PostMapping
    public ResponseEntity<TicketResponseDTO> crearTicket(@Valid @RequestBody CrearTicketDTO dto) {
        return new ResponseEntity<>(ticketService.crearTicket(dto), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}/estado")
    public ResponseEntity<TicketResponseDTO> actualizarEstadoTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.actualizarEstadoTicket(id));
    }


    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<TicketResponseDTO> reasignarPrioridad(
            @PathVariable Long id,
            @Valid @RequestBody ReasignarPrioridadDTO dto) {
        return ResponseEntity.ok(ticketService.reasignarPrioridad(id, dto));
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TicketResponseDTO>> obtenerTicketsUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ticketService.obtenerTicketsUsuario(usuarioId));
    }
}