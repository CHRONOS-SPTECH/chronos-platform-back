package chronos.tech.application.service;

import chronos.tech.application.dto.request.PessoaRegistroRequestDTO;
import chronos.tech.application.dto.request.PessoaRequestDTO;
import chronos.tech.application.dto.response.PessoaDetalhadaResponseDTO;
import chronos.tech.application.dto.response.PessoaResponseDTO;
import chronos.tech.application.dto.response.TipoVinculoResponseDTO;
import chronos.tech.application.mapper.PessoaMapper;
import chronos.tech.application.mapper.PessoaRegistroMapper;
import chronos.tech.application.port.in.PessoaUseCase;
import chronos.tech.application.port.out.CryptoPort;
import chronos.tech.application.port.out.FileStoragePort;
import chronos.tech.domain.model.classes.Pessoa;
import chronos.tech.domain.port.PessoaRepository;
import chronos.tech.infrastructure.utils.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService implements PessoaUseCase {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;
    private final PessoaRegistroMapper registroMapper;
    private final CryptoPort cryptoPort;
    private final FileStoragePort fileStoragePort;

    @Override
    public List<PessoaDetalhadaResponseDTO> getAllPersonsDetails() {

        return repository.findAll()
                .stream()
                .map(this::toDetalhadaResponse)
                .toList();
    }

    @Override
    public PessoaDetalhadaResponseDTO getPersonsDetailsId(Long id) {

        return repository.findById(id).map(
                        this::toDetalhadaResponse)
                .orElseThrow(() -> new RuntimeException("Não foi possível achar"));

    }

    @Override
    public PessoaResponseDTO createPessoa(PessoaRequestDTO requestPessoaDto) {
        Pessoa pessoa = mapper.toModel(requestPessoaDto);
        Pessoa pessoaSave = repository.save(pessoa);
        return mapper.toResponse(pessoaSave);

    }


    @Override
    public PessoaResponseDTO createPessoaComBiometria(PessoaRegistroRequestDTO requestDto) {
        Pessoa pessoa = registroMapper.toModel(requestDto);

        // 1. Trata a imagem em Base64, converte para bytes e envia ao S3
        if (requestDto.getImagemPerfilBase64() != null && !requestDto.getImagemPerfilBase64().isEmpty()) {
            try {
                // Remove o prefixo data:image/...;base64, se houver
                String base64Limpo = requestDto.getImagemPerfilBase64().replaceAll("^data:image/[^;]+;base64,", "");
                byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Limpo);

                // Cria um MultipartFile em memória para o adapter do S3 consumir
                MultipartFile multipartFile = new CustomMultipartFile(imageBytes, "foto-biometrica.jpg", "image/jpeg");

                String imagemUrl = fileStoragePort.uploadFile(multipartFile, "perfil-imagens");
                pessoa.setUrlFotoPerfil(imagemUrl);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar a imagem de perfil: " + e.getMessage(), e);
            }
        }

        // 2. Trata o vetor biométrico, converte em JSON string e criptografa
        if (requestDto.getVetorBiometrico() != null && !requestDto.getVetorBiometrico().isEmpty()) {
            try {
                // Transforma a lista de pontos faciais em uma string JSON normal
                String vetorJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(requestDto.getVetorBiometrico());

                // Criptografa a string JSON diretamente
                String vetorCriptografado = cryptoPort.encrypt(vetorJson);
                pessoa.setBiometriaFacial(vetorCriptografado);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar o vetor biométrico: " + e.getMessage(), e);
            }
        }

        Pessoa pessoaSalva = repository.save(pessoa);
        return mapper.toResponse(pessoaSalva);
    }

    @Override
    public PessoaResponseDTO pegarPorId(Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível achar"));

        return mapper.toResponse(pessoa);

    }

    @Override
    public List<PessoaResponseDTO> getAllPersons() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaAtualizado) {
        Pessoa pessoaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com o ID: " + id));

        mapper.updateFromDto(pessoaAtualizado, pessoaExistente);
        Pessoa pessoaAtualizada = repository.save(pessoaExistente);
        return mapper.toResponse(pessoaAtualizada);
    }

    @Override
    public void deletePessoa(Long id) {
        repository.deleteById(id);
    }

    private PessoaDetalhadaResponseDTO toDetalhadaResponse(Pessoa pessoa) {

        TipoVinculoResponseDTO vinculoDTO =
                new TipoVinculoResponseDTO(
                        pessoa.getTipoVinculo().getIdTipoVinculo(),
                        pessoa.getTipoVinculo().getNome_vinculo(),
                        pessoa.getTipoVinculo().getDescricao()

                );

        return new PessoaDetalhadaResponseDTO(
                pessoa.getIdPessoa(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.getGenero(),
                pessoa.getCpf(),
                pessoa.getBolsista(),
                pessoa.getUrlFotoPerfil(),
                pessoa.getDataNascimento(),
                pessoa.getDataIngresso(),
                pessoa.getDataMembro(),
                pessoa.getDataSaida(),
                vinculoDTO
        );
    }
}
