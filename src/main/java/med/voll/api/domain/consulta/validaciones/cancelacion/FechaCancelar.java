package med.voll.api.domain.consulta.validaciones.cancelacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class FechaCancelar implements ValidadorCancelacion{

    @Autowired
    ConsultaRepository repository;

    @Override
    public void validar(DatosCancelarConsulta datos) {
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var difHoras = Duration.between(ahora,consulta.getFecha()).toHours();

        if(difHoras < 24){
            throw new ValidationException("Solo puedes cancelar consultas un dia antes");
        }
    }
}
