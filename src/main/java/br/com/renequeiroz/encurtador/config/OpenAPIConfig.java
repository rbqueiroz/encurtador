package br.com.renequeiroz.encurtador.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${app.base-url}")
    private String urlServer;

    @Value("${local-server}")
    private String localServer;

    @Bean
    public OpenAPI customOpenAPI() {
        Server productionServer = new Server();
        productionServer.setUrl(urlServer);
        productionServer.setDescription(localServer);

        return new OpenAPI()
                .info(new Info()
                        .title("API Encurtador de URL")
                        .version("v1.0")
                        .description("Documentação da API"))
                .servers(List.of(productionServer));
    }
}
