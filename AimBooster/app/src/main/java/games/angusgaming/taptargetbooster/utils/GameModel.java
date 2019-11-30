package games.angusgaming.taptargetbooster.utils;

import androidx.annotation.Nullable;

public class GameModel {
    private double gameDuration;
    private Integer targetDuration;
    private Integer targetSize;
    private Integer targetsPerSecond;
    private SpawnType spawnType;
    private GameType gameType;

    public GameModel(double gameDuration, Integer targetDuration, Integer targetSize, Integer targetsPerSecond,
                     SpawnType spawnType, GameType gameType) {
        this.gameDuration = gameDuration;
        this.targetDuration = targetDuration;
        this.targetSize = targetSize;
        this.targetsPerSecond = targetsPerSecond;
        this.spawnType = spawnType;
        this.gameType = gameType;
    }

    public double getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Integer gameDuration) {
        this.gameDuration = gameDuration;
    }

    public void setGameDuration(double gameDuration) {
        this.gameDuration = gameDuration;
    }

    public Integer getTargetDuration() {
        return targetDuration;
    }

    public void setTargetDuration(Integer targetDuration) {
        this.targetDuration = targetDuration;
    }

    public Integer getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(Integer targetSize) {
        this.targetSize = targetSize;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getTargetsPerSecond() {
        return targetsPerSecond;
    }

    public SpawnType getSpawnType() {
        return spawnType;
    }

    public void setSpawnType(SpawnType spawnType) {
        this.spawnType = spawnType;
    }

    @Nullable
    public RankLevel getRankAttempt() {
        switch (gameType) {
            case TIME_CHALLENGE:
            case REACTION:
                if (gameDuration == 30)
                    return RankLevel.BRONZE;
                else if (gameDuration == 45)
                    return RankLevel.SILVER;
                else if (gameDuration == 60)
                    return RankLevel.GOLD;
                break;
            case PRECISION:
                if (gameDuration == 60 && targetSize == 60 && targetDuration == 124 &&
                        SpawnType.RANDOM.equals(spawnType))
                    return RankLevel.BRONZE;
                else if (gameDuration == 60 && targetSize == 32 && targetDuration == 79 &&
                        SpawnType.RANDOM.equals(spawnType))
                    return RankLevel.SILVER;
                else if (gameDuration == 60 && targetSize == 14 && targetDuration == 54 &&
                        SpawnType.RANDOM.equals(spawnType))
                    return RankLevel.GOLD;
                break;
            case TWITCH:
                if (gameDuration == 60 && targetSize == 60 && SpawnType.RANDOM.equals(spawnType))
                    return RankLevel.BRONZE;
                else if (gameDuration == 60 && targetSize == 30 && SpawnType.RANDOM.equals(spawnType))
                    return RankLevel.SILVER;
                else if (gameDuration == 60 && targetSize == 15 && SpawnType.RANDOM.equals(spawnType))
                    return RankLevel.GOLD;
                break;
        }
        return null;
    }
}
