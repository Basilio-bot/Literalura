package com.literalura.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String buscarLivro(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
        return restTemplate.getForObject(url, String.class);
    }
}
