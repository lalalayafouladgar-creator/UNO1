package org.example.uno.model;

public class Account {
    private String password;
    private String username;
    private String displayName;
    private int totalScore;
    private int wins;
    private int gamesPlayed;

     public Account(String username, String password, String displayName) {
          this.username = username;
          this.password = password;
          this.displayName = displayName;
          totalScore = 0;
          wins = 0;
          gamesPlayed = 0;
     }

     public String getPassword() {
          return password;
     }

     public String getUsername() {
          return username;
     }

     public int getGamesPlayed() {
          return gamesPlayed;
     }

     public int getTotalScore() {
          return totalScore;
     }

     public int getWins() {
          return wins;
     }

     public String getDisplayName() {
          return displayName;
     }

     public void setGamesPlayed(int gamesPlayed) {
          this.gamesPlayed = gamesPlayed;
     }

     public void addScore(int score) {
          totalScore += score;
     }

     public void addWin() {
          wins++;
     }

     public void addGamePlayed() {
          gamesPlayed++;
     }
}
