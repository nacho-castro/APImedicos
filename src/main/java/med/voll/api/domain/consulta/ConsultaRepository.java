package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {
    Boolean existsByPacienteIdAndFechaBetween(Long idP, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Page<Consulta> findByActivaTrue(Pageable pageable);

    Boolean existsByMedicoIdAndFecha(Long idM, LocalDateTime fecha);
}
