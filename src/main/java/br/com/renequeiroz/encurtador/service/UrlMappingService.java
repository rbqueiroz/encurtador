package br.com.renequeiroz.encurtador.service;

import br.com.renequeiroz.encurtador.dto.UrlMappingDTO;
import br.com.renequeiroz.encurtador.exceptions.MensagemGeralException;
import br.com.renequeiroz.encurtador.model.UrlMapping;
import br.com.renequeiroz.encurtador.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class UrlMappingService {

    @Value("${app.base-url}")
    private String baseUrl;

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
        String urlCurta = Base64.getUrlEncoder().withoutPadding().encodeToString(urlOriginal.getBytes()).substring(0, 6);
        return getUrl(urlCurta);
    }

    public Optional<String> getUrlOriginal(String url) {
        UrlMapping urlCurta = repository.findByUrlCurta(getUrl(url));
        if (urlCurta == null) {
            throw new MensagemGeralException("URL não encontrada");
        }
        verificarDataExpiracao(urlCurta);
        setAcessos(urlCurta);
        return Optional.ofNullable(urlCurta).map(UrlMapping::getUrlOriginal);
    }

    private void verificarDataExpiracao(UrlMapping urlMapping) {
        if (urlMapping.getExpirationDate().before(new Date())) {
            deletarUrl(urlMapping);
            throw new MensagemGeralException("URL expirada");
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
}
