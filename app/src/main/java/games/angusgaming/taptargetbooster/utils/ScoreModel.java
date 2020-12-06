package games.angusgaming.taptargetbooster.utils;

public class ScoreModel {

    private double scoreActual, quitTime, gameDurationMillis;
    private int attempt, success, targetPxSize;
    private RankLevel rankAttempt;
    private GameType gameType;

    public ScoreModel(int attempt, int success, double scoreActual, double quitTime,
                      double gameDurationMillis, int targetPxSize, GameType gameType, RankLevel rankAttempt) {
        this.attempt = attempt;
        this.success = success;
        this.scoreActual = scoreActual;
        this.quitTime = quitTime;
        this.gameDurationMillis = gameDurationMillis;
        this.targetPxSize = targetPxSize;
        this.gameType = gameType;
        this.rankAttempt = rankAttempt;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public double getScoreActual() {
        return scoreActual;
    }

    public void setScoreActual(double scoreActual) {
        this.scoreActual = scoreActual;
    }

    public double getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(double quitTime) {
        this.quitTime = quitTime;
    }

    public double getGameDurationMillis() {
        return gameDurationMillis;
    }

    public void setGameDurationMillis(double gameDurationMillis) {
        this.gameDurationMillis = gameDurationMillis;
    }

    public int getTargetPxSize() {
        return targetPxSize;
    }

    public void setTargetPxSize(int targetPxSize) {
        this.targetPxSize = targetPxSize;
    }

    public RankLevel getRankAttempt() {
        return rankAttempt;
    }

    public void setRankAttempt(RankLevel rankAttempt) {
        this.rankAttempt = rankAttempt;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
