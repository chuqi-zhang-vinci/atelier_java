package domaine;

import java.util.*;

public class Livre {
    private HashMap<Plat.Type, SortedSet<Plat>> plats;


    public Livre(){
        this.plats = new HashMap<Plat.Type, SortedSet<Plat>>();
    }

    /**
     * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans.
     * Il faut ajouter correctement le plat en fonction de son type.
     * @param plat le plat à ajouter
     * @return true si le plat a été ajouté, false sinon.
     */
    public boolean ajouterPlat(Plat plat) {
        if(!plats.containsKey(plat.getType())){
            TreeSet<Plat> platVal = new TreeSet<>();
            platVal.add(plat);
            plats.put(plat.getType(), platVal);
            return true;
        }
        if(plats.containsKey(plat.getType())){
            if(plats.get(plat.getType()).contains(plat))
                return false;
        }
        plats.get(plat.getType()).add(plat);
        return true;
    }
    /**
     * Supprime un plat du livre, s'il est dedans
     * Si le plat supprimé est le dernier de ce type de plat, il faut supprimer
     ce type de
     * plat de la Map.
     * @param plat le plat à supprimer
     * @return true si le plat a été supprimé, false sinon.
     */
    public boolean supprimerPlat (Plat plat) {
        if (plats.containsKey(plat.getType())){
            if(plats.get(plat).contains(plat)){
                plats.get(plat).remove(plat);
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String texte = "ENTREE\n" + "====\n";
        for (Plat.Type elem : plats.keySet())
            for (Plat i : plats.values())

        }


    }
}
