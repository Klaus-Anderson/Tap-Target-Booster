package gms.angusgaming.taptargetbooster.utils;

public class ScoreModel {

    private double attempt, success, scoreActual, quitTime, gameDurationMillis;
    private int targetPxSize;
    private RankLevel rankAttempt;
    private GameType gameType;

    public ScoreModel(double attempt, double success, double scoreActual, double quitTime,
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

    public double getAttempt() {
        return attempt;
    }

    public void setAttempt(double attempt) {
        this.attempt = attempt;
    }

    public double getSuccess() {
        return success;
    }

    public void setSuccess(double success) {
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
