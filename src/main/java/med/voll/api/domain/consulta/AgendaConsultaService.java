package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.agenda.ValidadorDeConsultas;
import med.voll.api.domain.consulta.validaciones.cancelacion.ValidadorCancelacion;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultaService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadoresAgendar;

    @Autowired
    List<ValidadorCancelacion> validadoresCancelar;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Id de paciente no encontrado");
        }

        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Id de medico no encontrado");
        }

        //Validaciones
        validadoresAgendar.forEach(v->v.validar(datos));

        var medico = seleccionarMedico(datos);
        if(medico == null){ //no hay medicos disponibles
            throw new ValidacionDeIntegridad("Medicos no disponibles");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        Consulta consulta = new Consulta(medico,paciente, datos.fecha());
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos){
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionar especialidad");
        }
        return medicoRepository.findEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }

    public void cancelar(DatosCancelarConsulta datos){
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("Id de consulta no encontrado");
        }

        //Validaciones
        validadoresCancelar.forEach(v->v.validar(datos));
    }
}
