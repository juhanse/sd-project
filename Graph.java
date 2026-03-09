import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Long, Localisation> noeuds;
    private Map<Localisation, List<Arc>> adjacence;

    public Graph(String localisations, String roads)  {
        this.noeuds = new HashMap<>();
        this.adjacence = new HashMap<>();

        loadCSV(localisations, roads);
    }

    public Localisation[] determinerZoneInondee(long[] idsOrigin,double epsilon) {
        //TODO
        return null ;
    }

    public Deque<Localisation> trouverCheminLePlusCourtPourContournerLaZoneInondee(long idOrigin, long idDestination, Localisation[] floodedZone) {
		//TODO
        return null ;
    }

    public Map<Localisation,Double> determinerChronologieDeLaCrue(long[] idsOrigin, double vWaterInit, double k) {
        //TODO
        return null ;
    }

    public Deque<Localisation> trouverCheminDEvacuationLePlusCourt(long idOrigin, long idEvacuation, double vVehicule, Map<Localisation,Double> tFlood) {
        //TODO
		return null ;
    }
}
