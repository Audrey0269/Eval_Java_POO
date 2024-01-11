package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColumnsHandler
{
    //ATTRIBUTS
    public List<String> column1;
    public List<String> column2;
    public List<String> column3;

    //CONSTRUCTEUR
    public ColumnsHandler(List<String> column1, List<String> column2, List<String> column3)
    {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
    }


    /**
     * Méthode generateRandomStrings
     * Permet d'obtenir 3 strings aléatoires pour les colonnes 1,2,3 (fichier JSON)
     * @return une liste de string
     */
    public List<List<String>> generateRandomStrings() {
        List<List<String>> result = new ArrayList<>();

        result.add(generateRandomStringsForColumn(column1));
        result.add(generateRandomStringsForColumn(column2));
        result.add(generateRandomStringsForColumn(column3));

        return result;
    }


    /**
     * Méthode generateRandomStringsForColumn
     * Permet d'obtenir 3 strings pour une colonne spécifique (param)
     * @param column
     * @return une liste de 3 strings
     */
    private List<String> generateRandomStringsForColumn(List<String> column) {
        List<String> result = new ArrayList<>();
        Random random = new Random();

        int p1Index = random.nextInt(column.size());
        int p2Index = (p1Index + 1) % column.size(); // modulo (%) pour revenir au début
        int p3Index = (p2Index + 1) % column.size();

        // Ajouter des strings à la liste
        result.add(column.get(p1Index));
        result.add(column.get(p2Index));
        result.add(column.get(p3Index));

        // Mise à jour les colonnes
        updateColumnWithRandomValues(column);

        return result;
    }

    /**
     * Méthode updateColumnWithRandomValues
     * Met à jour les 3 string de la matrix
     * @param column
     */
    private void updateColumnWithRandomValues(List<String> column) {
        Random random = new Random();

        // Trouver le p1 actuel dans la colonne
        String currentP1 = column.get(random.nextInt(column.size()));

        // Trouver le nouvel index de p1
        int p1Index = column.indexOf(currentP1);
        int newP1Index = (p1Index + 1) % column.size();

        // Remplacer le p1 actuel par le nouveau p1
        column.set(p1Index, column.get(newP1Index));
    }


    /**
     * Méthode printColumns()
     * Affiche les listes de 3 string (Pour vérifier le résulat obtenu)
     */
    public void printColumns() {
        System.out.println("Column 1: " + column1);
        System.out.println("Column 2: " + column2);
        System.out.println("Column 3: " + column3);
    }

}
