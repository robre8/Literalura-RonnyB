package com.alura.literalura.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="libros")
public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Double numeroDeDescargas;
    @ManyToOne(/*cascade = CascadeType.ALL,*/ fetch = FetchType.EAGER)
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        this.autor = autor;
    }

    public long getId() { return id;
    }

    public void setId(long id) { this.id = id;
    }

    public String getTitulo() { return titulo;
    }

    public void setTitulo(String titulo) { this.titulo = titulo;
    }

    public List<String> getIdiomas() { return idiomas;
    }

    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() { return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) { this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() { return autor;
    }

    public void setAutor(Autor autor) { this.autor = autor;
    }
        @Override
        public String toString () {
            return
                    "titulo='" + titulo + '\'' +
                            ", idioma=" + idiomas +
                            ", numero de descargas=" + numeroDeDescargas +
                            ", autores='" + autor.getNombre() + '\'';
        }
    }
