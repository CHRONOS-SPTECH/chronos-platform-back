package chronos.tech.domain.model.classes;

import chronos.tech.domain.model.enums.TipoVinculo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class Pessoa {

    private Integer id_pessoa;
    private String nome;
    private String genero;
    private TipoVinculo tipoVinculo;
    private LocalDate data_nascimento;
    private LocalDate data_ingresso;
    private LocalDate data_membro;
    private LocalDate data_saida;

    public Pessoa() {
    }

    public Pessoa(String nome, String genero, TipoVinculo tipoVinculo, LocalDate data_nascimento,
                  LocalDate data_ingresso, LocalDate data_membro, LocalDate data_saida) {
        this.nome = nome;
        this.genero = genero;
        this.tipoVinculo = tipoVinculo;
        this.data_nascimento = data_nascimento;
        this.data_ingresso = data_ingresso;
        this.data_membro = data_membro;
        this.data_saida = data_saida;
    }

    public Pessoa(Integer id_pessoa, String nome, String genero,
                  TipoVinculo tipoVinculo, LocalDate data_nascimento,
                  LocalDate data_ingresso, LocalDate data_membro,
                  LocalDate data_saida) {
        this.id_pessoa = id_pessoa;
        this.nome = nome;
        this.genero = genero;
        this.tipoVinculo = tipoVinculo;
        this.data_nascimento = data_nascimento;
        this.data_ingresso = data_ingresso;
        this.data_membro = data_membro;
        this.data_saida = data_saida;
    }

    public Integer getId_pessoa() {
        return id_pessoa;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public TipoVinculo getTipoVinculo() {
        return tipoVinculo;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public LocalDate getData_ingresso() {
        return data_ingresso;
    }

    public LocalDate getData_membro() {
        return data_membro;
    }

    public LocalDate getData_saida() {
        return data_saida;
    }

    public void setId_pessoa(Integer id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setTipoVinculo(TipoVinculo tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public void setData_ingresso(LocalDate data_ingresso) {
        this.data_ingresso = data_ingresso;
    }

    public void setData_membro(LocalDate data_membro) {
        this.data_membro = data_membro;
    }

    public void setData_saida(LocalDate data_saida) {
        this.data_saida = data_saida;
    }

}


