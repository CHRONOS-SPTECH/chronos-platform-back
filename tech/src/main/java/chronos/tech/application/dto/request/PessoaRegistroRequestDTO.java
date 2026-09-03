package chronos.tech.application.dto.request;

import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile imagemPerfil;
    private MultipartFile biometriaFacial;

    public PessoaRegistroRequestDTO() {}

    public PessoaRegistroRequestDTO(String nome, String email, String telefone, String genero,
                                     String cpf, Boolean bolsista, Integer tipo_vinculo_id,
                                     String data_nascimento, String data_ingresso, String data_membro,
                                     String data_saida, MultipartFile imagemPerfil, MultipartFile biometriaFacial) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.genero = genero;
        this.cpf = cpf;
        this.bolsista = bolsista;
        this.tipo_vinculo_id = tipo_vinculo_id;
        this.data_nascimento = data_nascimento;
        this.data_ingresso = data_ingresso;
        this.data_membro = data_membro;
        this.data_saida = data_saida;
        this.imagemPerfil = imagemPerfil;
        this.biometriaFacial = biometriaFacial;
    }

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

    public MultipartFile getImagemPerfil() { return imagemPerfil; }
    public void setImagemPerfil(MultipartFile imagemPerfil) { this.imagemPerfil = imagemPerfil; }

    public MultipartFile getBiometriaFacial() { return biometriaFacial; }
    public void setBiometriaFacial(MultipartFile biometriaFacial) { this.biometriaFacial = biometriaFacial; }
}
