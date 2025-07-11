package com.alura.literalura.menu;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.LivroService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private final LivroService livroService;
    private final AutorService autorService;

    public Menu(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    public void exibir() {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        do {
            System.out.println("""
                Escolha o número de sua opção:
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em um determinado idioma
                0 - Sair""");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título: ");
                    String titulo = scanner.nextLine();
                    Livro livro = livroService.buscarLivroApiESalvar(titulo);
                    if (livro != null) {
                        System.out.println(formatarLivro(livro));
                    } else {
                        System.out.println("Livro não encontrado ou erro na requisição.");
                    }
                }

                case 2 -> {
                    List<Livro> livros = livroService.listarTodos();
                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro registrado.");
                    } else {
                        livros.forEach(l -> System.out.println(formatarLivro(l)));
                    }
                }

                case 3 -> {
                    List<Autor> autores = autorService.listarTodosComLivros();
                    if (autores.isEmpty()) {
                        System.out.println("Nenhum autor registrado.");
                    } else {
                        autores.forEach(a -> System.out.println(formatarAutor(a)));
                    }
                }

                case 4 -> {
                    System.out.print("Digite o ano: ");
                    try {
                        int ano = Integer.parseInt(scanner.nextLine());
                        List<Autor> vivos = autorService.listarAutoresVivosNoAnoComLivros(ano);
                        if (vivos.isEmpty()) {
                            System.out.println("Nenhum autor vivo encontrado no ano informado.");
                        } else {
                            vivos.forEach(a -> System.out.println(formatarAutor(a)));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ano inválido! Digite um número inteiro.");
                    }
                }

                case 5 -> {
                    System.out.println("Idiomas disponíveis:\npt - português\nen - inglês\nfr - francês\nes - espanhol");
                    System.out.print("Escolha o código: ");
                    String idioma = scanner.nextLine();
                    List<Livro> livros = livroService.listarPorIdioma(idioma);
                    if (livros.isEmpty()) {
                        System.out.println("Não existe livros nesse idioma no banco de dados.");
                    } else {
                        livros.forEach(l -> System.out.println(formatarLivro(l)));
                    }
                }

                case 0 -> System.out.println("Encerrando...");

                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private String formatarLivro(Livro l) {
        return String.format("""
            ===== Livro =====
            Título: %s
            Autor: %s
            Idioma: %s
            Número de downloads: %d
            =================""",
                l.getTitulo(),
                l.getAutor().getNome(),
                l.getIdioma(),
                l.getNumeroDownloads());
    }

    private String formatarAutor(Autor a) {
        List<String> titulos = a.getLivros().stream()
                .map(Livro::getTitulo)
                .toList();

        String livrosFormatados = titulos.isEmpty()
                ? "Nenhum livro registrado"
                : String.join(", ", titulos);

        return String.format("""
            ===== Autor =====
            Nome: %s
            Ano de nascimento: %s
            Ano de falecimento: %s
            Livros: %s
            =================""",
                a.getNome(),
                a.getAnoNascimento() != null ? a.getAnoNascimento() : "Desconhecido",
                a.getAnoFalecimento() != null ? a.getAnoFalecimento() : "Vivo",
                livrosFormatados);
    }
}