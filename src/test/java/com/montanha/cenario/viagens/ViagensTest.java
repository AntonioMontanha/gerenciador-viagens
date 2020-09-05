package com.montanha.cenario.viagens;

import com.montanha.config.Configuracoes;
import com.montanha.factory.UserDataFactory;
import com.montanha.factory.ViagemDataFactory;
import com.montanha.model.Usuario;
import com.montanha.model.Viagem;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.aeonbits.owner.ConfigFactory;

public class ViagensTest {
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

    @Test
    public void testRetornarViagemIndividual() {
        // Mountebank
    }

    // Alure Report
}
