package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.*;
import org.example.imobsyss2.application.services.TokenService;
import org.example.imobsyss2.domain.entities.Empresa;
import org.example.imobsyss2.domain.entities.Usuario;
import org.example.imobsyss2.domain.repository.EmpresaRepository;
import org.example.imobsyss2.domain.repository.UsuarioRepository;
import org.example.imobsyss2.domain.valueObject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${spring.secretkey}")
    private String secret;


    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest resquest) {

        if (usuarioRepository.existsUsuarioByEmailAndSenha(resquest.email(), resquest.senha())) {

            var token = tokenService.gerarToken(resquest);
            return new LoginResponse(token);
        }
        return null;
    }



    // Multilocacao: usuario com empresa so enxerga/altera registros da propria empresa.
    // Usuario sem empresa (admin geral) enxerga tudo.
    private Optional<Usuario> buscarNaEmpresa(Long id) {
        var empresaId = getUsuarioLogado().empresa_id();

        if (empresaId == null) {
            return usuarioRepository.findById(id);
        }
        return usuarioRepository.findByIdAndEmpresa_Id(id, empresaId);
    }

    public List<UsuarioResponse> listarTodosUsuariosTable() {
        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return usuarioRepository.findAll()
                    .stream()
                    .map(UsuarioResponse::new)
                    .toList();
        }

        return usuarioRepository.getUsuariosByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)){
            return new CriarAdminResponse(0L,"Chave secreta invalida!");

        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);

        // Bootstrapping: se o desktop informar o nome da empresa, cria a Empresa e vincula o admin a ela
        if (criarAdminRequest.nomeEmpresa() != null && !criarAdminRequest.nomeEmpresa().isBlank()) {
            Empresa empresa = new Empresa();
            empresa.setRazaoSocial(criarAdminRequest.nomeEmpresa().trim());
            empresa.setNomeFantasia(criarAdminRequest.nomeEmpresa().trim());
            empresa.setCnpj(criarAdminRequest.cnpj());
            empresaRepository.save(empresa);
            usuarioAdminSalvar.setEmpresa(empresa);
        }

        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(),"Usuario Salvo com sucesso!");
    }

    public UsuarioResponse criarCorretor(UsuarioRequest usuario) {

        Usuario corretor = new Usuario(usuario);
        corretor.setRole("ROLE_CORRETOR");
        corretor.setEmpresa(buscarEmpresaDoUsuarioLogado());
        usuarioRepository.save(corretor);

        return new UsuarioResponse(corretor);
    }

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

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        var usuarioLogado = getUsuarioLogado();
        //Novo Usuario
        if(id == 0L) {
            Usuario usuarioBanco = new Usuario(usuario);
            usuarioBanco.setEmpresa(buscarEmpresaDoUsuarioLogado());
            usuarioRepository.save(usuarioBanco);

            return new UsuarioResponse(usuarioBanco);
        }else {
            //Usuario existente
            Usuario usuarioBanco = buscarNaEmpresa(id).orElse(null);
            if(usuarioBanco!= null ){
                usuarioBanco.setNome(usuario.nome());
                usuarioBanco.setCpf(new CPF(usuario.cpf()));
                usuarioBanco.setEmail(usuario.email());
                usuarioBanco.setSenha(usuario.senha());
                usuarioRepository.save(usuarioBanco);
                return  new UsuarioResponse(usuarioBanco);
            }

        }

        return null;
    }

    public UsuarioResponse buscarPorId(Long id) {

        var usuario = buscarNaEmpresa(id).orElse(null);
        if(usuario == null){
            return null;
        }

        return new UsuarioResponse(usuario);


    }

    public boolean atualizarStatus(EnumStatusUsuario status,Long id) {

        Usuario usuarioBanco = buscarNaEmpresa(id).orElse(null);
        if(usuarioBanco!= null ){
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return  true;
        }
        return false;

    }

    public UsuarioResponse BuscarUsuarioLogado() {
        try {
            var usuarioLogado = getUsuarioLogado();
            return usuarioRepository.findById(usuarioLogado.id())
                    .stream()
                    .map(UsuarioResponse::new).findFirst().orElse(null);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuario logado!");

        }
    }

}