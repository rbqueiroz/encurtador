package br.com.renequeiroz.encurtador.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table
@Entity
public class UrlMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url_original", length = 2048)
    private String urlOriginal;

    @Column(name = "url_curta", length = 200)
    private String urlCurta;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "data_cadastro")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataCadastro;

    @Column(name = "expiration_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate = new Date();

    @Column(name = "qtd_acessos")
    private Long qtdAcessos;

}
