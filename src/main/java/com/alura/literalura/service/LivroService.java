package com.alura.literalura.service;

import com.alura.literalura.api.GutendexClient;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepo;
    private final AutorRepository autorRepo;

    public LivroService(LivroRepository livroRepo, AutorRepository autorRepo) {
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
    }

    public Livro buscarLivroApiESalvar(String titulo) {
        try {
            String json = new GutendexClient().buscarJsonPorTitulo(titulo);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode resultados = mapper.readTree(json).get("results");

            if (resultados == null || resultados.isEmpty()) {
                return null;
            }

            JsonNode livroJson = resultados.get(0);

            JsonNode autorJson = livroJson.get("authors");
            if (autorJson == null || autorJson.isEmpty()) {
                return null;
            }

            JsonNode autorInfo = autorJson.get(0);
            String nomeAutor = autorInfo.get("name").asText();
            Integer nascimento = autorInfo.has("birth_year") && !autorInfo.get("birth_year").isNull()
                    ? autorInfo.get("birth_year").asInt()
                    : null;


            Optional<Autor> autorExistente = autorRepo.findAll().stream()
                    .filter(a -> a.getNome().equalsIgnoreCase(nomeAutor)
                            && (a.getAnoNascimento() != null && a.getAnoNascimento().equals(nascimento)))
                    .findFirst();

            Autor autor = autorExistente.orElseGet(() -> {
                Autor novoAutor = new Autor();
                novoAutor.setNome(nomeAutor);
                novoAutor.setAnoNascimento(nascimento);

                if (autorInfo.has("death_year") && !autorInfo.get("death_year").isNull()) {
                    novoAutor.setAnoFalecimento(autorInfo.get("death_year").asInt());
                }
                return autorRepo.save(novoAutor);
            });


            Livro livro = new Livro();
            livro.setTitulo(livroJson.get("title").asText());
            livro.setNumeroDownloads(
                    livroJson.has("download_count") ? livroJson.get("download_count").asInt() : 0);

            JsonNode idiomas = livroJson.get("languages");
            String idioma = (idiomas != null && idiomas.size() > 0)
                    ? idiomas.get(0).asText()
                    : "desconhecido";
            livro.setIdioma(idioma);

            livro.setAutor(autor);

            return livroRepo.save(livro);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar e salvar livro: " + e.getMessage(), e);
        }
    }


    public List<Livro> listarTodos() {
        return livroRepo.findAll();
    }


    public List<Livro> listarPorIdioma(String idioma) {
        return livroRepo.findByIdioma(idioma);
    }
}