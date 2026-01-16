package mg.taxi_brousse.app.dto;

public class ConfigPlaceDTO {
    private int id_type_place;
    private String nom;
    private int nombre;
    private double prix;

    public ConfigPlaceDTO(int id_type_place, String nom, int nombre, double prix) {
        this.id_type_place = id_type_place;
        this.nom = nom;
        this.nombre = nombre;
        this.prix = prix;
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
