package com.alura.literalura.principal;
import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository repository, AutorRepository repositoryAutor) {
        this.repositorio = repository;
        this.autorRepository = repositoryAutor;
    }

    public void muestraElMenu() {
        var opcion = "";
        while (!opcion.equals("0")){

            var menu = """
                    
                          ******* Menu de Literalura *******
                    
                      Seleccione una de las opciones disponibles:
                    
                             1 - Buscar libro por titulo 
                             2 - Listar libros registrados
                             3 - Listar autores registrados
                             4 - Listar autores vivos en un determinado año
                             5 - Listar libros por idioma   
                                    
                             0 - Salir
                    
                    """;
            System.out.println(menu);
            opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    buscarLibro();
                    break;
                case "2":
                    mostrarLibrosBuscados();
                    break;
                case "3":
                    mostrarAutoresBuscados();
                    break;
                case "4":
                    buscarAutoresEntreAños();
                    break;
                case "5":
                    buscarLibrosPorIdioma();
                    break;

                case "0":
                    System.out.println("Cerrando la aplicación. Gracias por utilizar Literalura.");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingresa un número válido.");
            }
        }
    }

    public void buscarLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var buscar = conversor.obtenerDatos(json, ListaCompleta.class);
        Optional<DatosLibro> librosBuscados = buscar.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if (librosBuscados.isPresent()) {
            DatosLibro datosLibro = librosBuscados.get();
            DatosAutor datosAutor = datosLibro.autores().get(0);

            Autor autor = autorRepository.findByNombre(datosAutor.nombre());

            if (autor == null) {
                autor = new Autor(datosAutor);
                autor = autorRepository.save(autor);
            }
            autor = autorRepository.findById(autor.getId()).orElse(null);

            Libro libroExistente = repositorio.findByTituloContainsIgnoreCase(datosLibro.titulo());
            if (libroExistente != null) {
                System.out.println("El libro \"" + datosLibro.titulo() + "\" ya está registrado.");
            } else {
            Libro libro = new Libro(datosLibro, autor);
            libro = repositorio.save(libro);
            System.out.println("El libro \"" + libro + "\" ha sido registrado.");
        }

        } else {
            System.out.println("El libro \"" + nombreLibro + "\" no ha sido encontrado.");
        }

      }

    private void mostrarLibrosBuscados(){
        libros = repositorio.findAll();
        libros.forEach(System.out::println);
    }

    private void mostrarAutoresBuscados(){
        autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }
    private void buscarAutoresEntreAños () {
        System.out.println("Ingrese el año");
        var input = teclado.nextLine();

        try {
            int año = Integer.parseInt(input);
            autores = autorRepository.autoresVivosEnAño(año);
            if (autores == null || autores.isEmpty()) {
                System.out.println("No hay registros de autores vivos en ese año.");
            } else {
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar un año válido.");
        }
    }
        public void buscarLibrosPorIdioma() {
            System.out.println("Ingrese el código de idioma que desea buscar.\nPor ejemplo = [en:Ingles],[es:Español],[fr:Frances],[pt:Portuguez],[de:Aleman],[tl:Tagalo]]  ");
            String idioma = teclado.nextLine();

            List<Libro> librosPorIdioma = repositorio.findByidiomasContainsIgnoreCase(idioma);
            if (librosPorIdioma.isEmpty()) {
                System.out.println("No se encontraron libros en ese idioma.");
            } else {
                System.out.println("Libros encontrados en el idioma \"" + idioma + "\":");
                librosPorIdioma.forEach(System.out::println);
            }
        }
}

