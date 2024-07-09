package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    AgendaConsultaService servicio;

    @Autowired
    ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta dato){
        var response = servicio.agendar(dato);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listadoConsultas(@PageableDefault(size = 4) Pageable paginacion){
        return ResponseEntity.ok(repository.findByActivaTrue(paginacion).map(DatosDetalleConsulta::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleConsulta> buscarConsulta(@PathVariable Long id){
       Consulta consulta = repository.getReferenceById(id);
       DatosDetalleConsulta response = new DatosDetalleConsulta(
               consulta.getId(),
               consulta.getPaciente().getId(),
               consulta.getMedico().getId(),
               consulta.getFecha());

       return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelarConsulta dato){
        servicio.cancelar(dato);
        return ResponseEntity.noContent().build();
    }
}
