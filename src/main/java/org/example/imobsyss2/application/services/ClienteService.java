package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusCliente;
import org.example.imobsyss2.domain.entities.Cliente;
import org.example.imobsyss2.application.dtos.EnumStatusCliente;
import org.example.imobsyss2.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {

        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {

        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente criar(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    public boolean atualizarStatusCliente(Long id, AtualizarStatusCliente request) {

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);

        if (clienteBanco == null) {
            return false;
        }

        clienteBanco.setStatusCliente(request.statusCliente());
        clienteRepository.save(clienteBanco);
        return true;
    }

    public Cliente atualizar(Long id, Cliente cliente) {

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);

        if (clienteBanco == null) {
            return null;
        }

        clienteBanco.setStatusCliente(cliente.getStatusCliente());
        clienteBanco.setNome(cliente.getNome());
        clienteBanco.setCpf(cliente.getCpf());
        clienteBanco.setEndereco(cliente.getEndereco());
        clienteBanco.setEmail(cliente.getEmail());
        clienteBanco.setTelefone(cliente.getTelefone());

        return clienteRepository.save(clienteBanco);
    }

    public boolean excluir(Long id) {

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);

        if (clienteBanco == null) {
            return false;
        }

        clienteBanco.setStatusCliente(EnumStatusCliente.INATIVO);
        clienteRepository.save(clienteBanco);
        return true;
    }
}