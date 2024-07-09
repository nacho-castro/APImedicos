package med.voll.api.domain.consulta.validaciones.agenda;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    PacienteRepository repository;

    public void validar(DatosAgendarConsulta datos){
        if(datos.idPaciente() == null){
            return;
        }
        var paciente = repository.findActivoById(datos.idPaciente());
        if(!paciente){
            throw new ValidationException("No se puede agendar paciente inactivo");
        }
    }
}
