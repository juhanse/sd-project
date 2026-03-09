public class Localisation {
    private String id;
    private double altitude;
    private double longitude;
    private String nom;
    private double metres;

    public Localisation(String id, double altitude, double longitude, String nom, double metres) {
        this.id = id;
        this.altitude = altitude;
        this.longitude = longitude;
        this.nom = nom;
        this.metres = metres;
    }

    public String getId() {
        return this.id;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNom() {
        return nom;
    }

    public double getMetres() {
        return metres;
    }

    public String toString() {
        return "";
    }
}
