package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusCliente;
import org.example.imobsyss2.application.dtos.ClienteRequest;
import org.example.imobsyss2.application.dtos.ClienteResponse;
import org.example.imobsyss2.application.dtos.EnumStatusCliente;
import org.example.imobsyss2.application.dtos.UsuarioLogado;
import org.example.imobsyss2.domain.entities.Cliente;
import org.example.imobsyss2.domain.repository.ClienteRepository;
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
        var usuarioLogado = getUsuarioLogado();
        return clienteRepository.findByEmpresaId(usuarioLogado.empresa_id()).stream()
                .map(ClienteResponse::new)
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        var usuarioLogado = getUsuarioLogado();
        return clienteRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id())
                .map(ClienteResponse::new)
                .orElse(null);
    }

    public ClienteResponse criar(ClienteRequest request) {
        var usuarioLogado = getUsuarioLogado();
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setCpf(request.cpf());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        cliente.setEndereco(request.endereco());
        cliente.setStatusCliente(EnumStatusCliente.CLIENTE_ATIVO);
        cliente.setEmpresaId(usuarioLogado.empresa_id());

        return new ClienteResponse(clienteRepository.save(cliente));
    }

    public boolean atualizarStatusCliente(Long id, AtualizarStatusCliente request) {
        var usuarioLogado = getUsuarioLogado();
        Cliente clienteBanco = clienteRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);

        if (clienteBanco == null) {
            return false;
        }

        clienteBanco.setStatusCliente(request.statusCliente());
        clienteRepository.save(clienteBanco);
        return true;
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        var usuarioLogado = getUsuarioLogado();
        Cliente clienteBanco = clienteRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);

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
        var usuarioLogado = getUsuarioLogado();
        Cliente clienteBanco = clienteRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);

        if (clienteBanco == null) {
            return false;
        }

        clienteBanco.setStatusCliente(EnumStatusCliente.INATIVO);
        clienteRepository.save(clienteBanco);
        return true;
    }
}