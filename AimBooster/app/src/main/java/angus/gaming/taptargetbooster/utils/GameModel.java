package angus.gaming.taptargetbooster.utils;

import android.widget.SeekBar;

import androidx.annotation.Nullable;

import angus.gaming.taptargetbooster.R;

public class GameModel {
    private Integer gameDuration, targetDuration, targetSize, targetsPerSecond;
    private Boolean random, respawn;
    private GameType gameType;

    public GameModel(Integer gameDuration, Integer targetDuration, Integer targetSize, Integer targetsPerSecond,
                     Boolean random, Boolean respawn, GameType gameType) {
        this.gameDuration = gameDuration;
        this.targetDuration = targetDuration;
        this.targetSize = targetSize;
        this.targetsPerSecond = targetsPerSecond;
        this.random = random;
        this.respawn = respawn;
        this.gameType = gameType;
    }

    public Integer getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Integer gameDuration) {
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

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public Boolean getRespawn() {
        return respawn;
    }

    public void setRespawn(Boolean respawn) {
        this.respawn = respawn;
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

    public void setTargetsPerSecond(Integer targetsPerSecond) {
        this.targetsPerSecond = targetsPerSecond;
    }

    @Nullable
    public RankLevel getRankAttempt(){
        switch (gameType) {
            case TIME_CHALLENGE:
            case REACTION:
                if (gameDuration == 30)
                    return RankLevel.BRONZE;
                else if (gameDuration == 45)
                    return RankLevel.SILVER;
                else if (gameDuration == 150)
                    return RankLevel.GOLD;
                break;
            case PRECISION:
                if (gameDuration == 60 && targetSize == 60 && targetDuration == 124 &&
                        random && !respawn)
                    return RankLevel.BRONZE;
                else if (gameDuration == 90 && targetSize == 32 && targetDuration == 79 &&
                        random && !respawn)
                    return RankLevel.SILVER;
                else if (gameDuration == 150 && targetSize == 14 && targetDuration == 54 &&
                        random && respawn)
                    return RankLevel.GOLD;
                break;
            case TWITCH:
                if (gameDuration == 60 && targetSize == 60 && random)
                    return RankLevel.BRONZE;
                else if (gameDuration == 90 && targetSize == 30 && random)
                    return RankLevel.SILVER;
                else if (gameDuration == 150 && targetSize == 15 && random)
                    return RankLevel.GOLD;
                break;
        }
        return null;
    }
}
