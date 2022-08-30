package com.clientes.mantenedor.models.repositories;

import com.clientes.mantenedor.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Con este repositorio tengo métodos que me facilitarán los CRUDs con la base de datos.
// De ser necesario se agregan más métodos usando la nomenclatura que proporciona Spring Data JPA
public interface ClientesRepository extends JpaRepository<Cliente, String> {
}
