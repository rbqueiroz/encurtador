package br.com.renequeiroz.encurtador.enums;

public enum Mensagens {
    URL_JA_EXISTE("URL já existe"),
    URL_NAO_ENCONTRADA("URL não encontrada"),
    URL_EXPIRADA("URL expirada"),
    URL_NAO_ENCONTRADA_PARA_DELETAR("URL não encontrada para deletar"),
    URL_DELETADA("URL deletada com sucesso");

    private final String mensagem;

    Mensagens(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
