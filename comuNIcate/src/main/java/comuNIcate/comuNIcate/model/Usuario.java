package comuNIcate.comuNIcate.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(unique = true, length = 20, nullable = false)
    private String nombreUsuario;
    @Column(nullable = false)
    private Date fechaUsuario;
    @Column(nullable = false, length = 50,unique = true)
    private String correoUsuario;

    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    private Comuna fkComuna;



}