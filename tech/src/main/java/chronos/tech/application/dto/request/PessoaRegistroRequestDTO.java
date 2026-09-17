package chronos.tech.application.dto.request;

import java.util.List;

public class PessoaRegistroRequestDTO {
    private String nome;
    private String email;
    private String telefone;
    private String genero;
    private String cpf;
    private Boolean bolsista;
    private Integer tipo_vinculo_id;
    private String data_nascimento;
    private String data_ingresso;
    private String data_membro;
    private String data_saida;

    // Novos campos que recebem a imagem convertida e o vetor em JSON
    private String imagemPerfilBase64;
    private List<Double> vetorBiometrico;

    public PessoaRegistroRequestDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Boolean getBolsista() { return bolsista; }
    public void setBolsista(Boolean bolsista) { this.bolsista = bolsista; }

    public Integer getTipo_vinculo_id() { return tipo_vinculo_id; }
    public void setTipo_vinculo_id(Integer tipo_vinculo_id) { this.tipo_vinculo_id = tipo_vinculo_id; }

    public String getData_nascimento() { return data_nascimento; }
    public void setData_nascimento(String data_nascimento) { this.data_nascimento = data_nascimento; }

    public String getData_ingresso() { return data_ingresso; }
    public void setData_ingresso(String data_ingresso) { this.data_ingresso = data_ingresso; }

    public String getData_membro() { return data_membro; }
    public void setData_membro(String data_membro) { this.data_membro = data_membro; }

    public String getData_saida() { return data_saida; }
    public void setData_saida(String data_saida) { this.data_saida = data_saida; }

    public String getImagemPerfilBase64() { return imagemPerfilBase64; }
    public void setImagemPerfilBase64(String imagemPerfilBase64) { this.imagemPerfilBase64 = imagemPerfilBase64; }

    public List<Double> getVetorBiometrico() { return vetorBiometrico; }
    public void setVetorBiometrico(List<Double> vetorBiometrico) { this.vetorBiometrico = vetorBiometrico; }
}