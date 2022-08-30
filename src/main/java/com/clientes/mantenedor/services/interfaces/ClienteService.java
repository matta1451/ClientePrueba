package com.clientes.mantenedor.services.interfaces;

import com.clientes.mantenedor.models.entities.Cliente;

import java.util.List;
import java.util.Optional;

// Creo una interface respetando los principios SOLID y luego la implementaré.
public interface ClienteService {
    List<Cliente> getClienteList();
    Optional<Cliente> getCliente(String id);
    Cliente saveCliente(Cliente cliente);
    void deleteCliente(String id);
}
