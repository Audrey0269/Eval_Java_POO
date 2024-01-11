package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)
    {
        Statistics statistics = new Statistics(50);

        try
        {
            //__________________________________DESERIALISATION_________________________
            Reader reader = new FileReader("src/main/resources/columns.json");

            //Obtention du tableau de tableaux de string
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            //Désérialisation de chaque tableau de string en 3 list de string correspondant aux colonnes
            List<String> column1 = new ArrayList<>();
            List<String> column2 = new ArrayList<>();
            List<String> column3 = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement element = jsonArray.get(i);
                if (element.isJsonArray()) {
                    JsonArray subArray = element.getAsJsonArray();
                    String[] strings = new Gson().fromJson(subArray, String[].class);

                    // Ajouter des strings dans les columns
                    switch (i) {
                        case 0:
                            column1.addAll(List.of(strings));
                            break;
                        case 1:
                            column2.addAll(List.of(strings));
                            break;
                        case 2:
                            column3.addAll(List.of(strings));
                            break;
                    }
                }
            }

            //__________________________________TEXTE JEU_________________________
            System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
            System.out.println("Bienvenue au Casino de Céladopole !");
            System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
            System.out.println(" ");

            //_______________________INITIALISATION DES JETONS_____________________
            int tokens = 50;

            //Tant que l'utilisateur à des jetons, le jeu continu
            while (tokens > 0)
            {
                System.out.println("Jetons possédés : " + tokens);

                //Choix du jeton (1,2 ou 3)
                Scanner scanner = new Scanner(System.in);
                boolean inputValid = false;
                int numberOfTokens = 0;

                while (!inputValid) {
                    System.out.println("Combien de jetons ? (1, 2 ou 3) :");

                    //Saisie de l'utilisateur
                    String userInput = scanner.nextLine();

                    // Vérifiez si la saisie correspond à 1,2 ou 3 (numeric)
                    if (StringUtils.isNumeric(userInput) && (userInput.equals("1") || userInput.equals("2") || userInput.equals("3"))) {
                        // Saisie valide
                        numberOfTokens = Integer.parseInt(userInput);
                        tokens = tokens - Integer.parseInt(userInput);
                        inputValid = true;
                    } else {
                        //Saisie non valide
                        System.out.println("Veuillez saisir une option valide (1, 2 ou 3).");
                    }
                }

                System.out.println("\n-----------------------------------");

                // Instancier ColumnsHandler
                ColumnsHandler columnsHandler = new ColumnsHandler(column1, column2, column3);

                List<List<String>> generatedStrings = columnsHandler.generateRandomStrings();


                //_______________________CREATION DE LA MACHINE_____________________
                Machine machine = new Machine(generatedStrings);
                machine.printMatrix();
                System.out.println(" ");


                //_______________________GAIN_____________________
                int gain = calculateWinnings(generatedStrings, numberOfTokens);
                // Ajout Des gains aux jetons
                tokens += gain;
                System.out.println("Gains : " + gain);

                //_______________________STATISTICS (JSON)_____________________
                // Mise à jour des statistiques
                statistics.incrementGamesPlayed();
                if (gain > 0) {
                    statistics.incrementGamesWon();
                }
                statistics.incrementCoinsSpent(numberOfTokens);
                statistics.updateCoins(tokens);

            }
            // Enregistrez statistiques dans fichier JSON - Uniquement quand la partie est finit (jeton = 0)
            statistics.saveStatisticsToFile("src/main/resources/statistics.json");

            System.out.println("Vous n'avez plus de jetons. Le jeu est terminé.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Méthode calculateWinnings
     * Pour calculer les gains en fonction du nombre de jeton misé
     * (boolean renvoi true si les lignes et diagonales ont un symbole identique)
     * (gain attribué en fonction du jeton choisit par l'utilisateur)
     * @param matrix
     * @param chosenTokens (jeton choisit par l'utilisateur 1,2,3)
     * @return
     */
    private static int calculateWinnings(List<List<String>> matrix, int chosenTokens)
    {
        boolean leftColumnWin = matrix.get(0).get(0).equals(matrix.get(1).get(0)) &&
                matrix.get(1).get(0).equals(matrix.get(2).get(0));

        boolean centerColumnWin = matrix.get(0).get(1).equals(matrix.get(1).get(1)) &&
                matrix.get(1).get(1).equals(matrix.get(2).get(1));

        boolean rightColumnWin = matrix.get(0).get(2).equals(matrix.get(1).get(2)) &&
                matrix.get(1).get(2).equals(matrix.get(2).get(2));

        boolean leftDiagonalWin = matrix.get(0).get(0).equals(matrix.get(1).get(1)) &&
                matrix.get(1).get(1).equals(matrix.get(2).get(2));

        boolean rightDiagonalWin = matrix.get(0).get(2).equals(matrix.get(1).get(1)) &&
                matrix.get(1).get(1).equals(matrix.get(2).get(0));

        //Si l'utilisateur a misé 1 jeton (ligne centrale)
        if (chosenTokens == 1 && centerColumnWin) {
            return calculateWinningsForSymbol(matrix.get(1).get(0));

            //Si l'utilisateur a misé 2 jetons (ligne haut/bas/milieu)
        } else if (chosenTokens == 2 && (leftColumnWin || centerColumnWin || rightColumnWin)) {
            if (leftColumnWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(0));
            } else if (centerColumnWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(1));
            } else if (rightColumnWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(2));
            }
            //Si l'utilisateur a misé 3 jetons (ligne haut/bas/milieu + diagonales)
        } else if (chosenTokens == 3 && (leftColumnWin || centerColumnWin || rightColumnWin || leftDiagonalWin || rightDiagonalWin)) {
            if (leftColumnWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(0));
            } else if (centerColumnWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(1));
            } else if (rightColumnWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(2));
            } else if (leftDiagonalWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(0));
            } else if (rightDiagonalWin) {
                return calculateWinningsForSymbol(matrix.get(0).get(2));
            }
        }
        return 0; //Si pas de gain
    }

    /**
     *Méthode calculateWinningsForSymbol
     *Définit les gains en fonction des symboles
     * @param symbol (String dans la matrix)
     * @return le gain en fonction du symbole
     */
    private static int calculateWinningsForSymbol(String symbol) {
        switch (symbol) {
            case "7":
                return 300;
            case "BAR":
                return 100;
            case "R":
            case "P":
            case "T":
                return 15;
            case "C":
                return 8;
            default:
                return 0;
        }
    }
}