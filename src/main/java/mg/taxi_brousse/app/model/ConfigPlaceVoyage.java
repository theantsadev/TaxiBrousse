package mg.taxi_brousse.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "config_place_voyage", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_voyage", "id_type_place"})
})
public class ConfigPlaceVoyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_config;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    @ManyToOne
    @JoinColumn(name = "id_type_place", nullable = false)
    private TypePlace typePlace;

    @Column(nullable = false)
    private int nombre;

    @Column(nullable = false)
    private double prix;

    public ConfigPlaceVoyage() {}

    public ConfigPlaceVoyage(Voyage voyage, TypePlace typePlace, int nombre, double prix) {
        this.voyage = voyage;
        this.typePlace = typePlace;
        this.nombre = nombre;
        this.prix = prix;
    }

    public int getId_config() {
        return id_config;
    }

    public void setId_config(int id_config) {
        this.id_config = id_config;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
