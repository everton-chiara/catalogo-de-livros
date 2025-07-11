package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepo;

    public AutorService(AutorRepository autorRepo) {
        this.autorRepo = autorRepo;
    }


    public List<Autor> listarTodosComLivros() {
        return autorRepo.buscarAutoresComLivros();
    }


    public List<Autor> listarAutoresVivosNoAnoComLivros(int ano) {
        return autorRepo.buscarAutoresVivosComLivros(ano);
    }


    public List<Autor> listarTodos() {
        return autorRepo.findAll();
    }

    public List<Autor> listarAutoresVivos(int ano) {
        List<Autor> vivos = autorRepo.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThan(ano, ano);
        vivos.addAll(autorRepo.findByAnoNascimentoLessThanEqualAndAnoFalecimentoIsNull(ano));
        return vivos;
    }
}