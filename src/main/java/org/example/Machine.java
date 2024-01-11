package org.example;

import java.util.List;

public class Machine {

    //ATTRIBUTS
    private String[][] matrix;

    //CONSTRUCTEUR
    public Machine(List<List<String>> generatedStrings) {
        this.matrix = new String[3][3];

        for (int row = 0; row < generatedStrings.size(); row++) {
            for (int col = 0; col < generatedStrings.get(row).size(); col++) {
                this.matrix[row][col] = generatedStrings.get(col).get(row);
            }
        }
    }

    /**
     * Méthode printMatrix()
     * Affichage de la matrix dans la console
     */
    public void printMatrix() {
        for (String[] row : matrix) {
            for (String cell : row) {
                System.out.print(cell + " | ");
            }
            System.out.println();
        }
    }
}
