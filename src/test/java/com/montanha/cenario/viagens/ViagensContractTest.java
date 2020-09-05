package com.montanha.cenario.viagens;

import com.montanha.config.Configuracoes;
import com.montanha.factory.UserDataFactory;
import com.montanha.factory.ViagemDataFactory;
import com.montanha.model.Usuario;
import com.montanha.model.Viagem;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ViagensContractTest {
    private UserDataFactory userDataFactory;
    private ViagemDataFactory viagemDataFactory;

    @BeforeEach
    public void setUp() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();

        userDataFactory = new UserDataFactory();
        viagemDataFactory = new ViagemDataFactory();
    }

    @Test
    public void testViagemValidaPodeSerCadastrada() {
        Usuario aValidAdmin = userDataFactory.aValidAdmin();

        String token = given()
            .contentType(ContentType.JSON)
            .body(aValidAdmin)
        .when()
            .post("/v1/auth")
        .then()
            .extract()
                .path("data.token");

        Viagem viagem = viagemDataFactory.criaUmaViagemValida();

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", token)
            .body(viagem)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("data.localDeDestino", equalTo("Osasco"));
    }
}
