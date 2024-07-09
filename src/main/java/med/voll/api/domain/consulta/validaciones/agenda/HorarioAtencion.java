package med.voll.api.domain.consulta.validaciones.agenda;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioAtencion implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos){
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        var antesApertura = datos.fecha().getHour()<7;
        var postCierre = datos.fecha().getHour()>19;
        if(domingo || antesApertura || postCierre){
            throw new ValidationException("El horario de atencion es de Lun a Sab. de 7:00 a 19:00hs");
        }
    }
}
