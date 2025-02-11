package com.demo.PokemonShakespeare.clients.shakespeare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.relational.core.sql.In;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShakespeareTranslation {
    @JsonProperty("success")
    private Success success;
    @JsonProperty("contents")
    private Contents contents;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }
}


