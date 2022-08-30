package com.clientes.mantenedor.controllers;

import com.clientes.mantenedor.models.entities.Cliente;
import com.clientes.mantenedor.services.interfaces.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @GetMapping("/clientes")
    public ResponseEntity<?> getClientes(){
        return new ResponseEntity<>(clienteService.getClienteList(), HttpStatus.OK);
    }
    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> getCliente(@PathVariable(value = "id") String id){
        Optional<Cliente> cliente_recuperado = clienteService.getCliente(id);
        if(cliente_recuperado.isPresent()){
            Cliente cliente = cliente_recuperado.get();
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/cliente_guardar")
    public ResponseEntity<?> guardarCliente(@Valid @RequestBody Cliente cliente, BindingResult bindingResult){
        // Con la interfaz BindingResult me permite validar que los campos no estén vacíos o sean null.
        if(bindingResult.hasErrors()){
            return enviarError(bindingResult);
        }
        // Verifico que el cliente exista.
        Optional<Cliente> cliente_recuperado = clienteService.getCliente(cliente.getCod_cliente());
        if(cliente_recuperado.isEmpty()){
            return new ResponseEntity<>(clienteService.saveCliente(cliente), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(Collections.singletonMap("error", "Ya existe un cliente con ese ID en la base de datos"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/actualizar_cliente/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable(value = "id") String id, @Valid @RequestBody Cliente cliente, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return enviarError(bindingResult);
        }
        // Consulto si existe el cliente al que se le quiere actualizar sus datos.
        Optional<Cliente> cliente_consultado = clienteService.getCliente(id);

        if(cliente_consultado.isPresent()) {
            Cliente cliente_recuperado = cliente_consultado.get();
            // En este caso hay dos situaciones, el primero es que el usuario quiere actualizar los datos para el mismo cliente (Su ruc, nombre, etc);
            // Y el segundo es para el caso en el que el usuario quiera cambiar de código al cliente.
            if(cliente.getCod_cliente().equalsIgnoreCase(cliente_recuperado.getCod_cliente())){
                return new ResponseEntity<>(clienteService.saveCliente(cliente), HttpStatus.CREATED);
            }
            // Para ello verifico que no exista otro cliente con el mismo ID en la bd
            Optional<Cliente> cliente_repetitivo = clienteService.getCliente(cliente.getCod_cliente());
            if(cliente_repetitivo.isEmpty()) {
                Cliente nuevo_cliente = clienteService.saveCliente(cliente);
                clienteService.deleteCliente(id);
                return new ResponseEntity<>(nuevo_cliente, HttpStatus.CREATED);
            }
            // Si se llega hasta aquí, es porque ya existe un cliente con el ID brindado.
            return new ResponseEntity<>(Collections.singletonMap("Error", "El cliente ya existe en la base de datos"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar_cliente/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable String id){
        Optional<Cliente> cliente = clienteService.getCliente(id);
        if(cliente.isPresent()){
            clienteService.deleteCliente(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> enviarError(BindingResult bindingResult){
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(e -> {
            errorMap.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
