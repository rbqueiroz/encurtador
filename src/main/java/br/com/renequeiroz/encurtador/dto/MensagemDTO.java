package br.com.renequeiroz.encurtador.dto;

import org.springframework.http.HttpStatus;

public record MensagemDTO(
        HttpStatus status,
        String mensagem
) {
}
