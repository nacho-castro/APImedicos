package med.voll.api.domain.consulta.validaciones.agenda;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos){
        if(datos.idMedico() == null){
            return;
        }
        var medico = repository.existsByMedicoIdAndFecha(datos.idMedico(),datos.fecha());
        if(medico){
            throw new ValidationException("El medico ya tiene el turno ocupado");
        }
    }
}
