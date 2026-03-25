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

    private Deque<Localisation> reconstruireChemin(Map<Long, Long> predecesseurs, long idStart, long idEnd) {
        Deque<Localisation> chemin = new ArrayDeque<>();
        long courant = idEnd;

        // Remonter les prédécesseurs jusqu'au point de départ
        while (courant != idStart) {
            chemin.addFirst(noeuds.get(courant));
            courant = predecesseurs.get(courant);
        }
        chemin.addFirst(noeuds.get(idStart)); // ajouter le noeud de départ

        return chemin;
    }

    public Localisation[] determinerZoneInondee(long[] idsOrigin,double epsilon) {
        Set<Localisation> visites = new HashSet<>();
        Queue<Localisation> file = new LinkedList<>();
        List<Localisation> zone = new ArrayList<>();

        for (long id : idsOrigin) {
            Localisation start = noeuds.get(id);
            if (start != null) {
                file.add(start);
                visites.add(start);
            }
        }

        while (!file.isEmpty()) {
            Localisation courant = file.poll();
            zone.add(courant);

            for (Arc arc : adjacence.get(courant)) {
                Localisation voisin = arc.getArrivee();

                if (!visites.contains(voisin) && voisin.getAltitude() <= courant.getAltitude() + epsilon) {
                    visites.add(voisin);
                    file.add(voisin);
                }
            }
        }

        return zone.toArray(new Localisation[0]);
    }

    public Deque<Localisation> trouverCheminLePlusCourtPourContournerLaZoneInondee(long idOrigin, long idDestination, Localisation[] floodedZone) {
        Set<Long> idsInondes = new HashSet<>();
        for (Localisation loc : floodedZone) {
            idsInondes.add(loc.getId());
        }

        Localisation depart = noeuds.get(idOrigin);
        if (depart == null || idsInondes.contains(idOrigin)) {
            return null;
        }

        Map<Long, Long> predecesseurs = new HashMap<>();
        Queue<Localisation> file = new LinkedList<>();

        predecesseurs.put(idOrigin, idOrigin);
        file.add(depart);

        while (!file.isEmpty()) {
            Localisation courant = file.poll();

            if (courant.getId() == idDestination) {
                return reconstruireChemin(predecesseurs, idOrigin, idDestination);
            }

            List<Arc> arcs = adjacence.get(courant);
            if (arcs == null) continue;

            for (Arc arc : arcs) {
                Localisation voisin = arc.getArrivee();
                long voisinId = voisin.getId();

                if (!predecesseurs.containsKey(voisinId) && !idsInondes.contains(voisinId)) {
                    predecesseurs.put(voisinId, courant.getId());
                    file.add(voisin);
                }
            }
        }

        return null;
    }

    public Map<Localisation,Double> determinerChronologieDeLaCrue(long[] idsOrigin, double vWaterInit, double k) {
        Map<Localisation, Double> tFlood = new LinkedHashMap<>();
        PriorityQueue<double[]> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> s[0]));

        for (long id : idsOrigin) {
            Localisation loc = noeuds.get(id);
            if (loc != null) {
                pq.offer(new double[]{0.0, (double) id, vWaterInit});
            }
        }

        while (!pq.isEmpty()) {
            double[] etat = pq.poll();
            double temps   = etat[0];
            long   nodeId  = (long) etat[1];
            double vWater  = etat[2];

            Localisation courant = noeuds.get(nodeId);
            if (courant == null) {
                continue;
            }

            if (tFlood.containsKey(courant)) {
                continue;
            }

            tFlood.put(courant, temps);

            List<Arc> arcs = adjacence.get(courant);
            if (arcs == null) {
                continue;
            }

            for (Arc arc : arcs) {
                Localisation voisin = arc.getArrivee();
                if (tFlood.containsKey(voisin)) {
                    continue;
                }

                double pente = (courant.getAltitude() - voisin.getAltitude()) / arc.getDistance();
                double vWaterVoisin = vWater + k * pente;

                if (vWaterVoisin <= 0) {
                    continue;
                }

                double poids = arc.getDistance() / vWaterVoisin;
                pq.offer(new double[]{temps + poids, (double) voisin.getId(), vWaterVoisin});
            }
        }

        return tFlood;
    }

    public Deque<Localisation> trouverCheminDEvacuationLePlusCourt(long idOrigin, long idEvacuation, double vVehicule, Map<Localisation,Double> tFlood) {
        Map<Long, Double> tempsMin = new HashMap<>();
        Map<Long, Long> predecesseurs = new HashMap<>();

        PriorityQueue<double[]> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> s[0]));

        Localisation depart = noeuds.get(idOrigin);
        if (depart == null) {
            return null;
        }

        tempsMin.put(idOrigin, 0.0);
        predecesseurs.put(idOrigin, idOrigin);
        pq.offer(new double[]{0.0, (double) idOrigin});

        while (!pq.isEmpty()) {
            double[] etat = pq.poll();
            double temps = etat[0];
            long nodeId = (long) etat[1];

            if (nodeId == idEvacuation) {
                return reconstruireChemin(predecesseurs, idOrigin, idEvacuation);
            }

            if (temps > tempsMin.getOrDefault(nodeId, Double.MAX_VALUE)) {
                continue;
            }

            Localisation courant = noeuds.get(nodeId);
            List<Arc> arcs = adjacence.get(courant);
            if (arcs == null) {
                continue;
            }

            for (Arc arc : arcs) {
                Localisation voisin = arc.getArrivee();
                long voisinId = voisin.getId();
                double tempsArrivee = temps + arc.getDistance() / vVehicule;

                Double tFloodVoisin = tFlood.get(voisin);
                if (tFloodVoisin != null && tempsArrivee > tFloodVoisin) {
                    continue;
                }

                if (tempsArrivee < tempsMin.getOrDefault(voisinId, Double.MAX_VALUE)) {
                    tempsMin.put(voisinId, tempsArrivee);
                    predecesseurs.put(voisinId, nodeId);
                    pq.offer(new double[]{tempsArrivee, (double) voisinId});
                }
            }
        }

        return null;
    }
}
