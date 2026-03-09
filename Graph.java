import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<Long, Localisation> noeuds;
    private Map<Localisation, List<Arc>> adjacence;

    public Graph(String localisations, String roads)  {
        this.noeuds = new HashMap<>();
        this.adjacence = new HashMap<>();

        loadCSV(localisations, roads);
    }

    private void loadCSV(String fichierNoeuds, String fichierArcs) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichierNoeuds))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.trim().isEmpty() || ligne.startsWith("id")) continue;

                String[] valeurs = ligne.split(",");
                long id = Long.parseLong(valeurs[0]);
                String nom = valeurs[1];
                double latitude = Double.parseDouble(valeurs[2]);
                double longitude = Double.parseDouble(valeurs[3]);
                double altitude = Double.parseDouble(valeurs[4]);

                Localisation noeud = new Localisation(id, nom, latitude, longitude, altitude);

                this.noeuds.put(id, noeud);
                this.adjacence.put(noeud, new ArrayList<>());
            }
        } catch (IOException e) {
            System.err.println("Erreur (noeuds) : " + e.getMessage());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichierArcs))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.trim().isEmpty() || ligne.startsWith("source")) continue;

                String[] valeurs = ligne.split(",");
                long idOrigine = Long.parseLong(valeurs[0]);
                long idDestination = Long.parseLong(valeurs[1]);
                double distance = Double.parseDouble(valeurs[2]);
                String nomRue = valeurs[3];

                Localisation origine = this.noeuds.get(idOrigine);
                Localisation destination = this.noeuds.get(idDestination);

                if (origine != null && destination != null) {
                    Arc nouvelArc = new Arc(origine, destination, distance, nomRue);
                    this.adjacence.get(origine).add(nouvelArc);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur (arcs) : " + e.getMessage());
        }
    }

    public Localisation[] determinerZoneInondee(long[] idsOrigin,double epsilon) {
        //TODO
        Set<Localisation> visites = new HashSet<>();
        Queue<Localisation> file = new LinkedList<>();
        List<Localisation> zone = new ArrayList<>();

        for(long id : idsOrigin){
            Localisation start = noeuds.get(id);
            if(start != null){
                file.add(start);
                visites.add(start);
            }
        }

        while(!file.isEmpty()){

            Localisation courant = file.poll();
            zone.add(courant);

            for(Arc arc : adjacence.get(courant)){

                Localisation voisin = arc.getArrivee();

                if(!visites.contains(voisin) &&
                        voisin.getAltitude() <= courant.getAltitude() + epsilon){

                    visites.add(voisin);
                    file.add(voisin);
                }
            }
        }

        return zone.toArray(new Localisation[0]);
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
