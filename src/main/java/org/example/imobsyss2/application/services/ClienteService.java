package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusCliente;
import org.example.imobsyss2.application.dtos.ClienteRequest;
import org.example.imobsyss2.application.dtos.ClienteResponse;
import org.example.imobsyss2.application.dtos.EnumStatusCliente;
import org.example.imobsyss2.domain.entities.Cliente;
import org.example.imobsyss2.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteResponse::new)
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::new)
                .orElse(null);
    }

    public ClienteResponse criar(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setCpf(request.cpf());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        cliente.setEndereco(request.endereco());
        cliente.setStatusCliente(EnumStatusCliente.ATIVO); // use um valor que exista no seu enum

        return new ClienteResponse(clienteRepository.save(cliente));
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

    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);

        if (clienteBanco == null) {
            return null;
        }

        clienteBanco.setNome(request.nome());
        clienteBanco.setCpf(request.cpf());
        clienteBanco.setEmail(request.email());
        clienteBanco.setTelefone(request.telefone());
        clienteBanco.setEndereco(request.endereco());

        return new ClienteResponse(clienteRepository.save(clienteBanco));
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