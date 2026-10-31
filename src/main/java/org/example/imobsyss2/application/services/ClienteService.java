package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.*;
import org.example.imobsyss2.domain.entities.Empresa;
import org.example.imobsyss2.domain.entities.Cliente;
import org.example.imobsyss2.domain.entities.Imovel;
import org.example.imobsyss2.domain.repository.EmpresaRepository;
import org.example.imobsyss2.domain.repository.ImovelRepository;
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

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Empresa buscarEmpresaDoUsuarioLogado() {
        var empresaId = getUsuarioLogado().empresa_id();
        if (empresaId == null) {
            return null;
        }
        return empresaRepository.findById(empresaId).orElse(null);
    }

    public List<ClienteResponse> listarTodos() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return clienteRepository.findAll()
                    .stream()
                    .map(ClienteResponse::new)
                    .toList();
        }

        return clienteRepository.getClientesByEmpresa_Id(usuarioLogado.empresa_id())
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

        Cliente clienteBanco = new Cliente(request);
        clienteBanco.setEmpresa(buscarEmpresaDoUsuarioLogado());
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

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        if(clienteBanco != null){
            clienteBanco.setNome(request.nome());
            clienteBanco.setCpf(new CPF(request.cpf()));
            clienteBanco.setEmail(request.email());
            clienteBanco.setTelefone(request.telefone());
            clienteBanco.setEndereco(request.endereco());
            clienteRepository.save(clienteBanco);
            return new ClienteResponse(clienteBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        return atualizarStatusCliente(id, new AtualizarStatusCliente(EnumStatusCliente.INATIVO));
    }

    public boolean registrarInteresse(Long id, InteresseRequest interesseRequest) {

        Cliente clienteBanco = clienteRepository.findById(id).orElse(null);
        Imovel imovelBanco = imovelRepository.findById(interesseRequest.imovel_id()).orElse(null);
        if (clienteBanco != null && imovelBanco != null) {

            var imovel = clienteBanco.getImoveis().stream()
                    .anyMatch(i -> i.getId().equals(imovelBanco.getId()));

            if (imovel) {
                return true;
            } else {
                clienteBanco.getImoveis().add(imovelBanco);

                clienteRepository.save(clienteBanco);

                return true;
            }

        }
        return false;
    }
}