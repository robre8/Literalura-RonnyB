package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="autores")
public class Autor {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique=true)
    private String nombre;
    private String nacimiento;
    private String defuncion;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Libro> libro = new HashSet<>();
    public Autor(){}
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.nacimiento = datosAutor.nacimiento();
        this.defuncion = datosAutor.defuncion();
    }

    public long getId() { return Id;
    }

    public void setId(long id) { Id = id;
    }

    public String getNombre() { return nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre;
    }

    public String getNacimiento() { return nacimiento;
    }

    public void setNacimiento(String nacimiento) { this.nacimiento = nacimiento;
    }

    public String getDefuncion() { return defuncion;
    }

    public void setDefuncion(String defuncion) { this.defuncion = defuncion;
    }

    public Set<Libro> getLibros() { return libro;
    }

    public void setLibros(Set<Libro> libros) { this.libro = libros;
    }

    @Override
    public String toString() {
        return
                        "autor='" + nombre + '\'' +
                        ", fecha de nacimiento del autor= " + nacimiento +
                        ", fecha de muerte del autor= " + defuncion + '\'';
    }
}
