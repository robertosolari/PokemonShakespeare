package com.demo.PokemonShakespeare.clients.shakespeare.models;

public class Contents {
    private String translated;
    private String text;
    private String translation;

    public Contents() {
    }

    public Contents(String translated) {
        this.translated = translated;
    }

    public Contents(String translated, String text, String translation) {
        this.translated = translated;
        this.text = text;
        this.translation = translation;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
