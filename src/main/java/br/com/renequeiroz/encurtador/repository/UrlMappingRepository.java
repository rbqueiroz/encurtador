package br.com.renequeiroz.encurtador.repository;

import br.com.renequeiroz.encurtador.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findByUrlCurta(String urlCurta);
    UrlMapping findByUrlOriginal(String urlOriginal);
}
