package com.clientes.mantenedor.models.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes")
// Al tener la dependencia Lombok, básicamente puedo resumir mucho código con los getters, setters y demás. Por ejemplo:
@Getter
@Setter
// Esta anotación permite tener todos los atributos como parámetros en el constructor y la anotación debajo hace lo contrario.
@AllArgsConstructor
@NoArgsConstructor
@ToString
// Implemento la interfaz Serializable como buena práctica para serializar el contenido de los objetos y posteriormente de ser necesario se deserializa.
public class Cliente implements Serializable {
    // Esta constante tiene un valor que indica la versión UID de la serialización.
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CodCliente", length = 10, nullable = false)
    private String cod_cliente;
    @Column(name = "NombreCompleto", length = 200, nullable = false)
    @NotBlank
    private String nombre_completo;
    @Column(name = "NombreCorto", length = 40, nullable = false)
    @NotBlank
    private String nombre_corto;
    @Column(name = "Abreviatura", length = 40, nullable = false)
    @NotBlank
    private String abreviatura;
    @Column(name = "Ruc", length = 11, nullable = false)
    @NotBlank
    private String ruc;
    @Column(name = "Estado", length = 1, nullable = false)
    @NotBlank
    @Length(max = 1)
    private String estado;
    @Column(name = "GrupoFacturacion", length = 100, nullable = true)
    private String grupo_facturacion;
    @Column(name = "InactivoDesde", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date inactivo_desde;
    @Column(name = "CodigoSAP", length = 20, nullable = true)
    private String codigo_sap;
}
