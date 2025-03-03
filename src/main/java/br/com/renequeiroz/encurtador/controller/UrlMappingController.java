package br.com.renequeiroz.encurtador.controller;

import br.com.renequeiroz.encurtador.dto.MensagemDTO;
import br.com.renequeiroz.encurtador.dto.UrlMappingDTO;
import br.com.renequeiroz.encurtador.model.UrlMapping;
import br.com.renequeiroz.encurtador.service.UrlMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class UrlMappingController {

    private final UrlMappingService service;

    public UrlMappingController(UrlMappingService service) {
        this.service = service;
    }

    @PostMapping("/encurtar-url")
    public ResponseEntity<UrlMapping> encurtarUrl(@RequestBody UrlMappingDTO urlMappingDTO) {
        UrlMapping urlMappingResponse = service.encurtarUrl(urlMappingDTO);
        return ResponseEntity.ok(urlMappingResponse);
    }

    @GetMapping("/{urlCurta}")
    public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String urlCurta) {

        return service.getUrlOriginal(urlCurta)
                .map(url -> ResponseEntity.status(302).location(URI.create(url)).build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UrlMapping>> listAll() {
        return ResponseEntity.ok(service.getListAll());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<MensagemDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteLink(id));
    }
}