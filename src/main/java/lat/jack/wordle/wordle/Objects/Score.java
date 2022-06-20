package lat.jack.wordle.wordle.Objects;

import java.util.HashMap;

public class Score extends User {

    private int userRankingID;
    private int userScore;
    private int userGamesPlayed;

    private int gamesWon;
    private int gamesLossed;

    private int winStreak;
    private int maxStreak;

    private HashMap<Integer, Integer> guessDistribution;

    public Score() {
        this.userID = 0;
        this.userName = "";
        this.userRankingID = 0;
        this.userScore = 0;
        this.userGamesPlayed = 0;
    }

    public Score(int userID, String userName, int userRankingID, int userScore, int userGamesPlayed) {
        this.userID = userID;
        this.userName = userName;
        this.userRankingID = userRankingID;
        this.userScore = userScore;
        this.userGamesPlayed = userGamesPlayed;
    }

    public Score(int userID, String userName, int userRankingID, int userScore, int userGamesPlayed, int gamesWon, int gamesLossed, int winStreak, int maxStreak, HashMap<Integer, Integer> guessDistribution) {
        this.userID = userID;
        this.userName = userName;
        this.userRankingID = userRankingID;
        this.userScore = userScore;
        this.userGamesPlayed = userGamesPlayed;

        this.gamesWon = gamesWon;
        this.gamesLossed = gamesLossed;
        this.winStreak = winStreak;
        this.maxStreak = maxStreak;

        this.guessDistribution = guessDistribution;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserRankingID() {
        return userRankingID;
    }

    public void setUserRankingID(int userRankingID) {
        this.userRankingID = userRankingID;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserGamesPlayed() {
        return userGamesPlayed;
    }

    public void setUserGamesPlayed(int userGamesPlayed) {
        this.userGamesPlayed = userGamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLossed() {
        return gamesLossed;
    }

    public void setGamesLossed(int gamesLossed) {
        this.gamesLossed = gamesLossed;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(int maxStreak) {
        this.maxStreak = maxStreak;
    }

    public HashMap<Integer, Integer> getGuessDistribution() {
        return guessDistribution;
    }

    public void setGuessDistribution(HashMap<Integer, Integer> guessDistribution) {
        this.guessDistribution = guessDistribution;
    }
}
