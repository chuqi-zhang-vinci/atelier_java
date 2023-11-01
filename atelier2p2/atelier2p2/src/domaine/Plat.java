package domaine;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class Plat {
    public enum Difficulte {
        X, XX, XXX, XXXX, XXXXX;

        public String toString(){
            String texte = "";
            int ordinal = ordinal() + 1;
            for (int i = 0; i < ordinal; i++) {
                texte += "*";
            }
            return texte;
        }
    }

    public enum Cout {
        $, $$, $$$, $$$$, $$$$$;

        public String toString(){
            String texte = "";
            int ordinal = ordinal() + 1;
            for (int i = 0; i < ordinal; i++) {
                texte += "€";
            }
            return texte;
        }
    }

    public enum Type {
        ENTREE, PLAT, DESSERT;
    }

    private String nom;
    private int nbPersonnes;
    private Difficulte niveauDeDifficulte;
    private Cout cout;
    private Duration dureeEnMinutes;

    private ArrayList<Instruction> listeRecette;
    private HashSet<IngredientQuantifie> ensembleIngredient;

    private Type type;
    public Plat (String nom, int nbPersonnes, Difficulte niveauDifficulte, Cout cout, Type type){
        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDifficulte;
        this.dureeEnMinutes = Duration.ofMinutes(0);
        this.cout = cout;
        this.listeRecette = new ArrayList<>();
        this.ensembleIngredient = new HashSet<>();
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }
    public Type getType(){
        return this.type;
    }

    public void insererInstruction(int position, Instruction instruction){
        if(position < 1 || position > this.listeRecette.size())
            throw new IllegalArgumentException("problème de taille");
        listeRecette.add(position-1, instruction);
        this.dureeEnMinutes = this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }
    public void ajouterInstruction(Instruction instruction){
        listeRecette.add(instruction);
        this.dureeEnMinutes = this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    }
    public Instruction remplacerInstruction(int position, Instruction instruction){
        Instruction victime = listeRecette.get(position-1);
        this.dureeEnMinutes = this.dureeEnMinutes.minus(victime.getDureeEnMinutes());
        this.dureeEnMinutes = this.dureeEnMinutes.plus(instruction.getDureeEnMinutes());
        listeRecette.remove(position-1);
        listeRecette.add(position-1, instruction);
        return victime;
    }
    public Instruction supprimerInstruction (int position){
        Instruction victime = listeRecette.get(position-1);
        listeRecette.remove(position-1);
        this.dureeEnMinutes = this.dureeEnMinutes.minus(victime.getDureeEnMinutes());
        return victime;
    }

    public Iterator<Instruction> instructions(){
       return Collections.unmodifiableList(listeRecette).iterator();
    }

    public boolean ajouterIngredient(Ingredient ingredient, int quantite, Unite unite){
        IngredientQuantifie ajoutIngredient = new IngredientQuantifie(ingredient, quantite, unite);
        if(ensembleIngredient.contains(ajoutIngredient))
            return false;
        ensembleIngredient.add(ajoutIngredient);
        return true;
    }
    public boolean ajouterIngredient(Ingredient ingredient, int quantite){
        IngredientQuantifie ajoutIngredient = new IngredientQuantifie(ingredient, quantite, Unite.NEANT);
        if(ensembleIngredient.contains(ajoutIngredient))
            return false;
        ensembleIngredient.add(ajoutIngredient);
        return true;
    }
    public boolean modifierIngredient(Ingredient ingredient, int quantite, Unite unite){
        for (IngredientQuantifie elem : ensembleIngredient) {
            if(elem.getIngredient().equals(ingredient)){
                elem.setQuantite(quantite);
                elem.setUnite(unite);
                return true;
            }
        }
        return false;
    }
    public boolean supprimerIngredient(Ingredient ingredient){
        for (IngredientQuantifie elem : ensembleIngredient) {
            if(elem.getIngredient().equals(ingredient)){
                ensembleIngredient.remove(elem);
                return true;
            }
        }
        return false;
    }
    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient){
        for (IngredientQuantifie elem:ensembleIngredient) {
            if(elem.getIngredient().equals(ingredient))
                return elem;
        }
        return null;
    }

    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ensembleIngredient) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (Instruction instruction : this.listeRecette) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }
}
