package br.com.renequeiroz.encurtador.service;

import br.com.renequeiroz.encurtador.dto.MensagemDTO;
import br.com.renequeiroz.encurtador.dto.UrlMappingDTO;
import br.com.renequeiroz.encurtador.enums.Mensagens;
import br.com.renequeiroz.encurtador.exceptions.MensagemGeralException;
import br.com.renequeiroz.encurtador.model.UrlMapping;
import br.com.renequeiroz.encurtador.repository.UrlMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UrlMappingService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final Logger log = LoggerFactory.getLogger(UrlMappingService.class);

    private final UrlMappingRepository repository;

    public UrlMappingService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    private UrlMapping salvarUrl(UrlMapping urlMapping) {
        return repository.save(urlMapping);
    }

    @Transactional
    protected void deletarUrl(UrlMapping urlMapping) {
        repository.delete(urlMapping);
    }


    private String getUrl(String url){
        return baseUrl + url;
    }

    @Transactional
    public UrlMapping encurtarUrl(UrlMappingDTO urlMappingDTO) {
        String urlCurta = gerarUrlCurta(urlMappingDTO.urlOriginal());
        UrlMapping urlExist = repository.findByUrlCurta(urlCurta);
        if (urlExist != null) {
            throw new MensagemGeralException("URL já existe");
        }

        UrlMapping urlMapping = UrlMapping.builder()
                .urlOriginal(urlMappingDTO.urlOriginal())
                .urlCurta(urlCurta)
                .descricao(urlMappingDTO.descricao())
                .dataCadastro(new Date())
                .qtdAcessos(0L)
                .expirationDate(gerarDataParaExpirar(30))
                .build();

        return salvarUrl(urlMapping);
    }

    private String gerarUrlCurta(String urlOriginal) {
        String urlCurtaBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(urlOriginal.getBytes());
        int tamanho = urlCurtaBase64.length();
        String urlCurta = urlCurtaBase64.substring(Math.max(tamanho - 6, 0));
        return getUrl(urlCurta);
    }

    public List<UrlMapping> getListAll() {
        List<UrlMapping> listUrlMappings = repository.findAll();
        List<UrlMapping> urlMappings = new ArrayList<>();

        for (UrlMapping urlMapping : listUrlMappings) {
            UrlMapping novo = UrlMapping.builder()
                    .id(urlMapping.getId())
                    .urlCurta(urlMapping.getUrlCurta())
                    .urlOriginal(urlMapping.getUrlOriginal())
                    .descricao(urlMapping.getDescricao())
                    .qtdAcessos(urlMapping.getQtdAcessos())
                    .dataCadastro(urlMapping.getDataCadastro())
                    .expirationDate(urlMapping.getExpirationDate())
                    .build();
            urlMappings.add(novo);
        }
        return urlMappings;
    }

    public Optional<String> getUrlOriginal(String url) {
        UrlMapping urlCurta = repository.findByUrlCurta(getUrl(url));
        if (urlCurta == null) {
            throw new MensagemGeralException(Mensagens.URL_NAO_ENCONTRADA.getMensagem());
        }
        verificarDataExpiracao(urlCurta);
        setAcessos(urlCurta);
        return Optional.ofNullable(urlCurta).map(UrlMapping::getUrlOriginal);
    }

    private void verificarDataExpiracao(UrlMapping urlMapping) {
        if (urlMapping.getExpirationDate().before(new Date())) {
            deletarUrl(urlMapping);
            throw new MensagemGeralException(Mensagens.URL_EXPIRADA.getMensagem());
        }
    }

    private UrlMapping setAcessos(UrlMapping urlMapping) {
        urlMapping.setQtdAcessos(urlMapping.getQtdAcessos() + 1);
        return salvarUrl(urlMapping);
    }

    private Date gerarDataParaExpirar(int dias) {
        Date dataCadastro = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataCadastro);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public MensagemDTO deleteLink(Long id) {
        UrlMapping urlMapping = repository.findById(id).orElseThrow(() -> new MensagemGeralException(Mensagens.URL_NAO_ENCONTRADA.getMensagem()));
        deletarUrl(urlMapping);
        return new MensagemDTO(HttpStatus.valueOf(200), Mensagens.URL_DELETADA.getMensagem());
    }
}
