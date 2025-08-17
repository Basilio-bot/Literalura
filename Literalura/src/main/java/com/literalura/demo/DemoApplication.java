package com.literalura.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.demo.model.Autor;
import com.literalura.demo.model.Livro;
import com.literalura.demo.service.CatalogoService;
import com.literalura.demo.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private GutendexService gutendexService;

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Buscar livro pelo título (API)");
            System.out.println("2 - Listar livros");
            System.out.println("3 - Listar autores");
            System.out.println("4 - Autores vivos em determinado ano");
            System.out.println("5 - Livros por idioma");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1 -> buscarLivro();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> autoresPorAno();
                case 5 -> livrosPorIdioma();
                case 0 -> {
                    System.out.println("Encerrando programa...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivro() {
        try {
            System.out.print("Digite o título do livro: ");
            String titulo = scanner.nextLine();
            String json = gutendexService.buscarLivro(titulo);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            if (root.get("results").isEmpty()) {
                System.out.println("Nenhum livro encontrado!");
                return;
            }

            JsonNode bookNode = root.get("results").get(0);

            String tituloLivro = bookNode.get("title").asText();
            String idioma = bookNode.get("languages").get(0).asText();
            int downloads = bookNode.get("download_count").asInt();

            JsonNode autorNode = bookNode.get("authors").get(0);
            String nomeAutor = autorNode.get("name").asText();
            Integer nascimento = autorNode.get("birth_year").isNull() ? null : autorNode.get("birth_year").asInt();
            Integer falecimento = autorNode.get("death_year").isNull() ? null : autorNode.get("death_year").asInt();

            Autor autor = new Autor();
            autor.setNome(nomeAutor);
            autor.setAnoNascimento(nascimento);
            autor.setAnoFalecimento(falecimento);

            Livro livro = new Livro();
            livro.setTitulo(tituloLivro);
            livro.setIdioma(idioma);
            livro.setDownloads(downloads);
            livro.setAutor(autor);

            catalogoService.salvarLivro(livro);
            System.out.println("✅ Livro salvo: " + tituloLivro + " - " + nomeAutor);

        } catch (Exception e) {
            System.out.println("❌ Erro ao buscar livro: " + e.getMessage());
        }
    }

    private void listarLivros() {
        var livros = catalogoService.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livros.forEach(l ->
                    System.out.println(l.getTitulo() + " (" + l.getIdioma() + ") - " + l.getAutor().getNome()));
        }
    }

    private void listarAutores() {
        var autores = catalogoService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            autores.forEach(a -> {
                System.out.println(a.getNome()
                        + " (Nasc.: " + a.getAnoNascimento()
                        + " - Falec.: " + a.getAnoFalecimento() + ")");
            });
        }
    }

    private void autoresPorAno() {
        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        var autores = catalogoService.autoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo no ano " + ano);
        } else {
            autores.forEach(a -> System.out.println(a.getNome()));
        }
    }

    private void livrosPorIdioma() {
        System.out.print("Digite o idioma (pt, en, es, fr): ");
        String idioma = scanner.nextLine();

        var livros = catalogoService.listarLivrosPorIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
        } else {
            livros.forEach(l -> System.out.println(l.getTitulo() + " - " + l.getAutor().getNome()));
        }
    }
}
