import java.util.Objects;

public class Arc {
    private String origine;
    private String arrivee;
    private double distance;
    private String nomRue;

    public Arc(String origine, String arrivee, double distance, String nomRue) {
        this.origine = origine;
        this.arrivee = arrivee;
        this.distance = distance;
        this.nomRue = nomRue;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Arc arc = (Arc) o;
        return Objects.equals(origine, arc.origine) && Objects.equals(arrivee, arc.arrivee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origine, arrivee);
    }
}
