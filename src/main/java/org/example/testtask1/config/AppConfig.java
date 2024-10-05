package org.example.testtask1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${paste.api.url}")
    private String pasteApiUrl;

    public String getPasteApiUrl() {
        return pasteApiUrl;
    }
}