package com.literalura.demo.service;

import com.literalura.demo.model.Autor;
import com.literalura.demo.model.Livro;
import com.literalura.demo.repository.AutorRepository;
import com.literalura.demo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void salvarLivro(Livro livro) {
        autorRepository.save(livro.getAutor());
        livroRepository.save(livro);
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    public List<Autor> autoresVivosNoAno(int ano) {
        return autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
    }
}
