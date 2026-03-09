public class Localisation {
    private String id;
    private String altitude;
    private String longitude;
    private String nom;
    private double metres;

    public Localisation(String id, String altitude, String longitude, String nom, double metres) {
        this.id = id;
        this.altitude = altitude;
        this.longitude = longitude;
        this.nom = nom;
        this.metres = metres;
    }

    public String getId() {
        return this.id;
    }

    public String getAltitude() {
        return this.altitude;
    }

    public String getLongitude() {
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
