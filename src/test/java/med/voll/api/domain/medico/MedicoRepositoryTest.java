package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    MedicoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Retorna nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void findEspecialidadEnFecha1() {
        //given
        var proximoLunes = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico= registrarMedico("Pablo","p@mail.com", "12345",Especialidad.CARDIOLOGIA);
        var paciente= registrarPaciente("Jere","j@mail.com","12344");
        registrarConsulta(medico,paciente,proximoLunes);

        //when
        var medicoLibre = repository.findEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes);

        //then
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Retorna medico cuando consulta la Base de Datos en ese horario")
    void findEspecialidadEnFecha2() {

        //given
        var proximoLunes = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico= registrarMedico("Pablo","p@mail.com", "12345",Especialidad.CARDIOLOGIA);

        //when
        var medicoLibre = repository.findEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes);

        //then
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(medico,paciente,fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(nombre,email,documento,especialidad);
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento){
        var paciente = new Paciente(nombre,email,documento);
        em.persist(paciente);
        return paciente;
    }
}