import java.util.Objects;

public class Localisation {
    private long id;
    private String nom;
    private double latitude;
    private double longitude;
    private double altitude;

    public Localisation(long id, String nom, double latitude, double longitude, double altitude) {
        this.id = id;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localisation that = (Localisation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return this.id;
    }

    public String getNom() {
        return nom;
    }

    public double getlatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public String toString() {
        return "";
    }
}
