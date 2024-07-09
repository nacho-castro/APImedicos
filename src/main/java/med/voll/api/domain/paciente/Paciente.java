package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.medico.Especialidad;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    private Boolean activo;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos){
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.documento();
        this.activo = true;
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarDatos(DatosActualizarPaciente datos){
        if(datos.nombre() != null){
            this.nombre = datos.nombre();
        }
        if(datos.documento() != null){
            this.documento = datos.documento();
        }
        if(datos.direccion() != null){
            this.direccion = direccion.actualizarDatos(datos.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
