package com.montanha.gerenciador.viagens;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ViagensTest {
    @Test
    public void  testDadoUmAdministradorQuandoCadastroViagensEntaoObtenhoStatusCode201() {
        //Given When Then - Unit TestName
        // Configurar o caminho comum de acesso a minha API REST
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api/v1";

        // Login na API REST com administrador
        String token = given()
                .body("{\n" +
                        "\t\"email\":\"admin@email.com\", \n" +
                        "\t\"senha\": \"654321\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/auth")
            .then()
                .log().all()
                .extract()
                    .path("data.token");
        System.out.println("Token: " + token);
        // Cadastrar a viagem
        given()
                .header("Authorization", token)
                .body("{\n" +
                        "  \"acompanhante\": \"Luciano\",\n" +
                        "  \"dataPartida\": \"2021-01-31\",\n" +
                        "  \"dataRetorno\": \"2021-02-28\",\n" +
                        "  \"localDeDestino\": \"Manaus\",\n" +
                        "  \"regiao\": \"Norte\"\n" +
                        "}")
                .contentType(ContentType.JSON)
        .when()
                .post("/viagens")
        .then()
                .log().all()
                .assertThat()
                    .statusCode(201);
    }
}
