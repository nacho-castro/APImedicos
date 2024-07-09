package med.voll.api.domain.consulta.validaciones.agenda;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var consulta = datos.fecha();

        var dif30min = Duration.between(ahora,consulta).toMinutes()<30;

        if(dif30min){
            throw new ValidationException("Las consultas deben ser con 30min de anticipacion");

        }
    }
}
