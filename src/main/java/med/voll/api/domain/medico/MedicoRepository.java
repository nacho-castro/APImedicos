package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT m.activo
            FROM Medico m
            WHERE m.id=:idMedico
            """)
    Boolean findActivoById(Long idMedico);

    @Query("""
       select m from Medico m
       where m.activo= true
       and
       m.especialidad=:especialidad
       and
       m.id not in( 
           select c.medico.id from Consulta c
           where
           c.fecha=:fecha
       )
       order by random()
       limit 1
       """)
    Medico findEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
