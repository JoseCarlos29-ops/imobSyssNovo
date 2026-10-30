package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.*;
import org.example.imobsyss2.domain.entities.Cliente;
import org.example.imobsyss2.domain.repository.ClienteRepository;
import org.example.imobsyss2.domain.valueObject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<ClienteResponse> listarTodos() {

        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponse::new)
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {

        var cliente = clienteRepository.findById(id).orElse(null);
        if(cliente == null){
            return null;
        }

        return new ClienteResponse(cliente);
    }

    public ClienteResponse criar(ClienteRequest request) {

        var usuarioLogado = getUsuarioLogado();

        Cliente clienteBanco = new Cliente(request);
        clienteBanco.setEmpresa_id(usuarioLogado.empresa_id());
        clienteRepository.save(clienteBanco);

        return new ClienteResponse(clienteBanco);
    }

    public boolean atualizarStatusCliente(Long id, AtualizarStatusCliente request) {

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if(clienteBanco != null){
            clienteBanco.setStatusCliente(request.statusCliente());
            clienteRepository.save(clienteBanco);
            return true;
        }
        return false;
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {

        var usuarioLogado = getUsuarioLogado();

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if(clienteBanco != null){
            clienteBanco.setNome(request.nome());
            clienteBanco.setCpf(new CPF(request.cpf()));
            clienteBanco.setEmail(request.email());
            clienteBanco.setTelefone(request.telefone());
            clienteBanco.setEndereco(request.endereco());
            clienteBanco.setEmpresa_id(usuarioLogado.empresa_id());
            clienteRepository.save(clienteBanco);
            return new ClienteResponse(clienteBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        return atualizarStatusCliente(id, new AtualizarStatusCliente(EnumStatusCliente.INATIVO));
    }
}