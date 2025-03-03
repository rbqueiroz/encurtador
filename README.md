# Encurtador de Links
## Descrição
Este projeto é um encurtador de links, que tem como objetivo encurtar links grandes para links menores, facilitando a sua utilização.
## Funcionalidades
- Encurtar links via API
- Redirecionar links encurtados via API
- Listar links encurtados via API
- Deletar links via API
- Estatísticas de acesso aos links encurtados via API

## Tecnologias
- Java 17
- Spring Boot
- JPA
- H2 Database
- Swagger
- Lombok

## Como rodar
Para rodar o projeto, basta executar o comando `mvn spring-boot:run` na raiz do projeto. O projeto estará disponível em `http://localhost:8080`.

## Documentação
A documentação da API está disponível em `http://localhost:8080/swagger-ui.html`.

## Exemplo de uso
### Encurtar link
Para encurtar um link, basta fazer uma requisição POST para `http://localhost:8080/encurtar-url` com o seguinte corpo:
```json
{
  "urlOriginal" : "https://www.google.com/search?sca_esv=9a193cf2d9170f88&rlz=1C1CHBD_enBR1112BR1112&sxsrf=ADLYWILWnAUY3XUZ_Md40eUtYWxH9GT54g:1736629657807&q=img&udm=2&fbs=AEQNm0B-n-O0Tl8kahCw8A1QEw8Mict6JzQlWiIWxntiM9v91yTdNMM8xiGhoawYbNd1fihFlhcM5iZxkxWpvXM_7MtxDj88GJ9eiAAhVkgF-e17fGH2Kb1O8JVBkb_JCrgYSsPNm99pqtSdWcg0N3-o_A6AXusIHEnNnP_vGteGsF44iSeyPYGtri9ayuVIKIyA4L_XSZQNhhS4IjFrMqaegME0cTF8rg&sa=X&ved=2ahUKEwiesfDOye6KAxU0LLkGHbe0F8QQtKgLegQIEhAB&biw=1894&bih=1011&dpr=0.9#vhid=CtwY8PRdZ4CA8M&vssid=mosaic",
  "descricao" : "url referente a imagens"
}
```
A resposta será um JSON com o link encurtado:
```json
{    
    "id": 1,
    "urlOriginal": "https://www.google.com/search?sca_esv=9a193cf2d9170f88&rlz=1C1CHBD_enBR1112BR1112&sxsrf=ADLYWILWnAUY3XUZ_Md40eUtYWxH9GT54g:1736629657807&q=img&udm=2&fbs=AEQNm0B-n-O0Tl8kahCw8A1QEw8Mict6JzQlWiIWxntiM9v91yTdNMM8xiGhoawYbNd1fihFlhcM5iZxkxWpvXM_7MtxDj88GJ9eiAAhVkgF-e17fGH2Kb1O8JVBkb_JCrgYSsPNm99pqtSdWcg0N3-o_A6AXusIHEnNnP_vGteGsF44iSeyPYGtri9ayuVIKIyA4L_XSZQNhhS4IjFrMqaegME0cTF8rg&sa=X&ved=2ahUKEwiesfDOye6KAxU0LLkGHbe0F8QQtKgLegQIEhAB&biw=1894&bih=1011&dpr=0.9#vhid=CtwY8PRdZ4CA8M&vssid=mosaic",
    "urlCurta": "http://localhost:8080/aHR0cH",
    "descricao": "url referente a imagens",
    "dataCadastro": "2025-01-15",
    "expirationDate": "2025-02-14",
    "qtdAcessos": 0
}
```

### Redirecionar link
Para redirecionar um link encurtado, basta fazer uma requisição GET para `http://localhost:8080/aHR0cH`. O redirecionamento será feito para a URL original.

### Listar links encurtados
Para listar os links encurtados, basta fazer uma requisição GET para `http://localhost:8080/listar`. A resposta será uma lista de links encurtados.

