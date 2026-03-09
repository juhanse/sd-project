public class Localisation {
    private String id;
    private String nom;
    private double latitude;
    private double longitude;
    private double altitude;

    public Localisation(String id, String nom, double latitude, double longitude, double altitude) {
        this.id = id;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getId() {
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
