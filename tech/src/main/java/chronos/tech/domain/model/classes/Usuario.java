package chronos.tech.domain.model.classes;

import java.time.LocalDateTime;

public class Usuario {

    private Long id_usuario;
    private Pessoa id_pessoa;
    private PerfilAcesso id_perfil;
    private String email_login;
    private String senha_hash;
    private Boolean status_ativo;
    private LocalDateTime data_criacao;

    public Usuario() {
    }

    public Usuario(Long id_usuario, Pessoa id_pessoa, PerfilAcesso id_perfil, String email_login, String senha_hash, Boolean status_ativo, LocalDateTime data_criacao) {
        this.id_usuario = id_usuario;
        this.id_pessoa = id_pessoa;
        this.id_perfil = id_perfil;
        this.email_login = email_login;
        this.senha_hash = senha_hash;
        this.status_ativo = status_ativo;
        this.data_criacao = data_criacao;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Pessoa getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Pessoa id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public PerfilAcesso getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(PerfilAcesso id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getEmail_login() {
        return email_login;
    }

    public void setEmail_login(String email_login) {
        this.email_login = email_login;
    }

    public String getSenha_hash() {
        return senha_hash;
    }

    public void setSenha_hash(String senha_hash) {
        this.senha_hash = senha_hash;
    }

    public Boolean getStatus_ativo() {
        return status_ativo;
    }

    public void setStatus_ativo(Boolean status_ativo) {
        this.status_ativo = status_ativo;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }
}
