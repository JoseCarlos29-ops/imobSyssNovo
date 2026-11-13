package org.example.imobsyss2.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.EnumStatusImovel;
import org.example.imobsyss2.application.dtos.ImovelRequest;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String endereco;

    // Localidade preenchida pela API externa ViaCEP (a partir do CEP)
    private String cep;

    private String bairro;

    private String cidade;

    private String uf;

    private String tipo;

    private double valor;

    private double area;

    private int quartos;

    private int banheiros;

    private String descricao;

    private EnumStatusImovel status = EnumStatusImovel.DISPONIVEL;

    private String proprietario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @ManyToMany(mappedBy = "imoveis")
    private Set<Cliente> clientes = new HashSet<>();

    public void setEndereco(String endereco) {
        if(endereco == null || endereco.isBlank()){
            throw new IllegalArgumentException("Endereco Invalido!");
        }
        this.endereco = endereco;
    }

    public void setTipo(String tipo) {
        if(tipo == null || tipo.isBlank()){
            throw new IllegalArgumentException("Tipo Invalido!");
        }
        this.tipo = tipo;
    }

    public void setValor(double valor) {
        if(valor <= 0){
            throw new IllegalArgumentException("Valor Invalido!");
        }
        this.valor = valor;
    }

    public void setArea(double area) {
        if(area <= 0){
            throw new IllegalArgumentException("Area Invalida!");
        }
        this.area = area;
    }

    public void setQuartos(int quartos) {
        if(quartos < 0){
            throw new IllegalArgumentException("Quantidade de quartos Invalida!");
        }
        this.quartos = quartos;
    }

    public void setBanheiros(int banheiros) {
        if(banheiros < 0){
            throw new IllegalArgumentException("Quantidade de banheiros Invalida!");
        }
        this.banheiros = banheiros;
    }

    public void setProprietario(String proprietario) {
        if(proprietario == null || proprietario.isBlank()){
            throw new IllegalArgumentException("Proprietario Invalido!");
        }
        this.proprietario = proprietario;
    }

    // Regra: a localidade so e aceita se o CEP tiver 8 digitos, a cidade existir e a UF tiver 2 letras
    public void informarLocalizacao(String cep, String bairro, String cidade, String uf) {
        String cepNumerico = cep == null ? "" : cep.replaceAll("\\D", "");

        if(cepNumerico.length() != 8){
            throw new IllegalArgumentException("CEP Invalido!");
        }
        if(cidade == null || cidade.isBlank()){
            throw new IllegalArgumentException("Cidade Invalida!");
        }
        if(uf == null || !uf.trim().matches("[A-Za-z]{2}")){
            throw new IllegalArgumentException("UF Invalida!");
        }

        this.cep = cepNumerico;
        this.bairro = bairro == null ? "" : bairro.trim();
        this.cidade = cidade.trim();
        this.uf = uf.trim().toUpperCase();
    }

    // Regra: somente imovel disponivel ou reservado pode receber interesse de clientes
    public boolean aceitaInteresse() {
        return this.status == EnumStatusImovel.DISPONIVEL || this.status == EnumStatusImovel.RESERVADO;
    }

    // Regra: somente imovel disponivel pode ser reservado (apartado) para alugar ou comprar
    public void reservar() {
        if(this.status != EnumStatusImovel.DISPONIVEL){
            throw new IllegalArgumentException("Somente imovel disponivel pode ser reservado!");
        }
        this.status = EnumStatusImovel.RESERVADO;
    }

    // Regras de mudanca de status do imovel
    public void atualizarStatus(EnumStatusImovel novoStatus) {
        if(novoStatus == null){
            throw new IllegalArgumentException("Status Invalido!");
        }
        if(novoStatus == this.status){
            return;
        }
        if(this.status == EnumStatusImovel.INDISPONIVEL && novoStatus != EnumStatusImovel.DISPONIVEL){
            throw new IllegalArgumentException("Imovel indisponivel so pode ser reativado como disponivel!");
        }
        if(this.status == EnumStatusImovel.EM_MANUTENCAO
                && (novoStatus == EnumStatusImovel.ALUGADO || novoStatus == EnumStatusImovel.VENDIDO)){
            throw new IllegalArgumentException("Imovel em manutencao nao pode ser alugado ou vendido!");
        }
        if(this.status == EnumStatusImovel.ALUGADO && novoStatus == EnumStatusImovel.INDISPONIVEL){
            throw new IllegalArgumentException("Imovel alugado nao pode ser excluido!");
        }
        this.status = novoStatus;
    }

    public Imovel(ImovelRequest imovel) {
        this.setEndereco(imovel.endereco());
        this.setTipo(imovel.tipo());
        this.setValor(imovel.valor());
        this.setArea(imovel.area());
        this.setQuartos(imovel.quartos());
        this.setBanheiros(imovel.banheiros());
        this.setDescricao(imovel.descricao());
        this.setProprietario(imovel.proprietario());
    }
}
