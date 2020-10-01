package fr.umontpellier.iut;

import java.util.ArrayList;

public class Inventaire {
    private int taille;
    private ArrayList<Object> contenu;


    public Inventaire(int taille) {
        this.taille = taille;
        this.contenu = new ArrayList<Object>();
    }

    public void ajouter(Object item){
        contenu.add(item);
    }

    public void retirer(Object item){
        contenu.remove(item);
    }


}
