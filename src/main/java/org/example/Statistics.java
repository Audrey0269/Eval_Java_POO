package org.example;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class Statistics
{
    //ATTRIBUTS
    private int coins;
    private int gamesPlayed;
    private int gamesWon;
    private int coinsSpent;

    //CONSTRUCTEUR
    public Statistics(int coins) {
        this.coins = coins;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.coinsSpent = 0;
    }

    public void updateCoins(int tokens) {
        coins = tokens;
    }
    public void incrementGamesPlayed() {
        gamesPlayed++;
    }
    public void incrementGamesWon() {
        gamesWon++;
    }
    public void incrementCoinsSpent(int tokens) {
        coinsSpent += tokens;
    }


    /**
     * Méthode saveStatisticsToFile
     * Enregistrer les statistics dans un fichier JSON
     * @param filename (chemin du fichier JSON)
     */
    public void saveStatisticsToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GETTERS
    public int getCoins() {
        return coins;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public int getGamesWon() {
        return gamesWon;
    }
    public int getCoinsSpent() {
        return coinsSpent;
    }
}
