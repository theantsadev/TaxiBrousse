package mg.taxi_brousse.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_place")
public class TypePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_type_place;

    @Column(unique = true, nullable = false)
    private String nom;

    public TypePlace() {}

    public TypePlace(String nom) {
        this.nom = nom;
    }

    public int getId_type_place() {
        return id_type_place;
    }

    public void setId_type_place(int id_type_place) {
        this.id_type_place = id_type_place;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
