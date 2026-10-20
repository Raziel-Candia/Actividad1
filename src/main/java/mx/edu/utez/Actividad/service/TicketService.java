package mx.edu.utez.Actividad.service;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.Actividad.controller.dto.CrearTicketDTO;
import mx.edu.utez.Actividad.controller.dto.ReasignarPrioridadDTO;
import mx.edu.utez.Actividad.controller.dto.TicketResponseDTO;
import mx.edu.utez.Actividad.exception.BadRequestException;
import mx.edu.utez.Actividad.exception.NotFoundException;
import mx.edu.utez.Actividad.model.EstadoTicket;
import mx.edu.utez.Actividad.model.Ticket;
import mx.edu.utez.Actividad.model.Usuario;
import mx.edu.utez.Actividad.repository.TicketRepository;
import mx.edu.utez.Actividad.repository.UsuarioRepository;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;


    @Transactional
    public TicketResponseDTO crearTicket(CrearTicketDTO dto){
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(()->new NotFoundException("el usuario con id " + dto.getUsuarioId() + "no existe"));
        if (!Boolean.TRUE.equals(usuario.getActivo())){
            throw new BadRequestException("el usuario esta deshabilitado");

        }
        Ticket ticket = Ticket.builder()
                .descripcion(dto.getDescripcion())
                .prioridad(dto.getPrioridad())
                .estado(EstadoTicket.ABIERTO)
                .usuario(usuario)
                .build();
        return mapToDTO(ticketRepository.save(ticket));

    }
    @Transactional
    public TicketResponseDTO actualizarEstadoTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("El ticket con ID " + ticketId + " no existe."));


        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BadRequestException("No se puede modificar un ticket en estado CERRADO.");
        } else if (ticket.getEstado() == EstadoTicket.ABIERTO) {
            ticket.setEstado(EstadoTicket.EN_PROCESO);
        } else if (ticket.getEstado() == EstadoTicket.EN_PROCESO) {
            ticket.setEstado(EstadoTicket.CERRADO);
        }


        return mapToDTO(ticketRepository.save(ticket));
    }

    @Transactional
    public TicketResponseDTO reasignarPrioridad(Long ticketId, ReasignarPrioridadDTO dto) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("El ticket con ID " + ticketId + " no existe"));


        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BadRequestException("No se puede reasignar la prioridad a un ticket CERRADO");
        }


        ticket.setPrioridad(dto.getNuevaPrioridad());
        return mapToDTO(ticketRepository.save(ticket));
    }



    @Transactional(readOnly = true)
    public List<TicketResponseDTO> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new NotFoundException("El usuario con ID " + usuarioId + " no existe.");
        }


        return ticketRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private TicketResponseDTO mapToDTO(Ticket ticket) {
        return TicketResponseDTO.builder()
                .id(ticket.getId())
                .descripcion(ticket.getDescripcion())
                .estado(ticket.getEstado())
                .prioridad(ticket.getPrioridad())
                .usuarioId(ticket.getUsuario().getId())
                .nombreUsuario(ticket.getUsuario().getNombre())
                .build();
    }
}
