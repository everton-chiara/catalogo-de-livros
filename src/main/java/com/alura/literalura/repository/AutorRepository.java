package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {


    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.livros")
    List<Autor> buscarAutoresComLivros();


    @Query("""
        SELECT a FROM Autor a LEFT JOIN FETCH a.livros
        WHERE a.anoNascimento <= :ano
        AND (a.anoFalecimento IS NULL OR a.anoFalecimento > :ano)
        """)
    List<Autor> buscarAutoresVivosComLivros(int ano);


    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThan(Integer anoNascimento, Integer anoFalecimento);
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoIsNull(Integer anoNascimento);
}