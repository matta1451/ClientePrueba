package com.clientes.mantenedor.services.impl;

import com.clientes.mantenedor.models.entities.Cliente;
import com.clientes.mantenedor.models.repositories.ClientesRepository;
import com.clientes.mantenedor.services.interfaces.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ClienteServiceImpl")
public class ClienteServiceImpl implements ClienteService {
    // Aquí hay dos opciones para inyectar la referencia del objeto DAO (ClientesRepository)
    // Una es usando la anotación @Autowired y la otra es pasando el objeto como parámetro del constructor
    // Como yo quiero que sean más escalables y editables los atributos, lo pondré en el constructor, ya que con @Autowired, esto sería más engorroso.
    private final ClientesRepository clientesRepository;
    public ClienteServiceImpl(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    // Hago uso de la anotación @Transactional, para simular el commit (En caso de éxito) o RollBack (En caso de algún error)
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getClienteList() {
        return this.clientesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> getCliente(String id) {
        return this.clientesRepository.findById(id);
    }

    @Override
    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        return this.clientesRepository.save(cliente);
    }

    @Override
    @Transactional
    public void deleteCliente(String id) {
        this.clientesRepository.deleteById(id);
    }
}
