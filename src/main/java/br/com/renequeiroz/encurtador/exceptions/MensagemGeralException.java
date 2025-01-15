package br.com.renequeiroz.encurtador.exceptions;

public class MensagemGeralException extends BusinessException {

    private static final String mensagemPadrao = "Erro ao processar a requisição";

    public MensagemGeralException() {
        super(mensagemPadrao);
    }

    public MensagemGeralException(String message) {
        super(message);
    }

    public MensagemGeralException(String message, Throwable cause) {
        super(message, cause);
    }

    public MensagemGeralException(Throwable cause) {
        super(mensagemPadrao, cause);
    }
}