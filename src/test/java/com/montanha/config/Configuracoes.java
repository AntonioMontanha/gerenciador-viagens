package com.montanha.config;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/test/resources/config/test.properties"})
public interface Configuracoes extends Config {

    @Key("api.base.path")
    String basePath();

    @Key("api.base.uri")
    String baseURI();

    @Key("api.port")
    int port();
}
