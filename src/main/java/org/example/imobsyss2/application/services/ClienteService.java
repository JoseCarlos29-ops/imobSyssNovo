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
import java.util.Optional;

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


    // Multilocacao: usuario com empresa so enxerga/altera registros da propria empresa.
    // Usuario sem empresa (admin geral) enxerga tudo.
    private Optional<Cliente> buscarNaEmpresa(Long id) {
        var empresaId = getUsuarioLogado().empresa_id();

        if (empresaId == null) {
            return clienteRepository.findById(id);
        }
        return clienteRepository.findByIdAndEmpresa_Id(id, empresaId);
    }

    private Optional<Imovel> buscarImovelNaEmpresa(Long imovelId) {
        var empresaId = getUsuarioLogado().empresa_id();

        if (empresaId == null) {
            return imovelRepository.findById(imovelId);
        }
        return imovelRepository.findByIdAndEmpresa_Id(imovelId, empresaId);
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

        var cliente = buscarNaEmpresa(id).orElse(null);
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

        Cliente clienteBanco = buscarNaEmpresa(id).orElse(null);
        if(clienteBanco != null){
            clienteBanco.setStatusCliente(request.statusCliente());
            clienteRepository.save(clienteBanco);
            return true;
        }
        return false;
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {

        Cliente clienteBanco = buscarNaEmpresa(id).orElse(null);
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

        Cliente clienteBanco = buscarNaEmpresa(id).orElse(null);
        Imovel imovelBanco = buscarImovelNaEmpresa(interesseRequest.imovel_id()).orElse(null);
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