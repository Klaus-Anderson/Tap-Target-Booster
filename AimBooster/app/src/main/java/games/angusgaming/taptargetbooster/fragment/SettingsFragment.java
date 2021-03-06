package games.angusgaming.taptargetbooster.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;

import java.util.Locale;
import java.util.Objects;

import games.angusgaming.taptargetbooster.R;
import games.angusgaming.taptargetbooster.utils.GameModel;
import games.angusgaming.taptargetbooster.utils.GameType;
import games.angusgaming.taptargetbooster.utils.RankLevel;
import games.angusgaming.taptargetbooster.utils.SpawnType;

import static games.angusgaming.taptargetbooster.utils.GooglePlayServicesConstants.RC_LEADERBOARD_UI;

public class SettingsFragment extends GooglePlaySupportedFragment implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private static final Integer TWITCH_BRONZE_GAME_DURATION = 60;
    private static final Integer TWITCH_BRONZE_TARGET_SIZE = 60;
    private GameType gameType;
    private View rootView;

    SettingsFragment(@NonNull GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setOnSeekBarChangeListener(this);
        ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setOnSeekBarChangeListener(this);
        ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setOnSeekBarChangeListener(this);
        ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setOnSeekBarChangeListener(this);
        ((CompoundButton) rootView.findViewById(R.id.randomBox)).setOnCheckedChangeListener(this);
        ((CompoundButton) rootView.findViewById(R.id.respawnBox)).setOnCheckedChangeListener(this);
        rootView.findViewById(R.id.bronzeButton).setOnClickListener(this);
        rootView.findViewById(R.id.silverButton).setOnClickListener(this);
        rootView.findViewById(R.id.goldButton).setOnClickListener(this);
        rootView.findViewById(R.id.startButton).setOnClickListener(this);
        rootView.findViewById(R.id.bronzeLeaderButton).setOnClickListener(this);
        rootView.findViewById(R.id.silverLeaderButton).setOnClickListener(this);
        rootView.findViewById(R.id.goldLeaderButton).setOnClickListener(this);

        updateGooglePlaySignInState(rootView);

        setUpViews();
        adjustGameSetting(RankLevel.BRONZE);

        return rootView;
    }

    private void setUpViews() {
        switch (gameType) {
            case TWITCH:
                rootView.findViewById(R.id.tdFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
                break;
            case DOUBLE_SHOT:
            case PRECISION:
                rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
                break;
            case REACTION:
            case TIME_CHALLENGE:
                rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.tdFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.tsFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.randomBox).setVisibility(View.GONE);
                rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
                rootView.findViewById(R.id.checkFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.targetView).setVisibility(View.GONE);
                break;
            case SPEED:
                rootView.findViewById(R.id.tdFrame).setVisibility(View.GONE);
                rootView.findViewById(R.id.randomBox).setVisibility(View.GONE);
                rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
                rootView.findViewById(R.id.checkFrame).setVisibility(View.GONE);
                break;
            case TRACKING:
                rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
        }
    }

    private void adjustGameSetting(RankLevel rankLevel) {
        switch (gameType) {
            case TWITCH:
                switch (rankLevel) {
                    case BRONZE:
                        adjustGameSetting(TWITCH_BRONZE_GAME_DURATION, null, TWITCH_BRONZE_TARGET_SIZE,
                                null, true, null);
                        return;
                    case SILVER:
                        adjustGameSetting(60, null, 30,
                                null, true, null);
                        return;
                    case GOLD:
                        adjustGameSetting(60, null, 15,
                                null, true, null);
                        return;
                }
                break;
            case DOUBLE_SHOT:
                break;
            case PRECISION:
                switch (rankLevel) {
                    case BRONZE:
                        adjustGameSetting(60, 124, 60,
                                null, true, false);
                        return;
                    case SILVER:
                        adjustGameSetting(60, 79, 32,
                                null, true, false);
                        return;
                    case GOLD:
                        adjustGameSetting(60, 54, 14,
                                null, true, false);
                        return;
                }
                break;
            case REACTION:
            case TIME_CHALLENGE:
                switch (rankLevel) {
                    case BRONZE:
                        adjustGameSetting(30, null, null,
                                null, null, null);
                        return;
                    case SILVER:
                        adjustGameSetting(45, null, null,
                                null, null, null);
                        return;
                    case GOLD:
                        adjustGameSetting(60, null, null,
                                null, null, null);
                        return;
                }
                break;
        }
    }

    private void adjustGameSetting(@Nullable Integer gameDuration, @Nullable Integer targetDuration, @Nullable Integer targetSize,
                                   @Nullable Integer targetsPerSecond, @Nullable Boolean random, @Nullable Boolean respawn) {
        if (gameDuration != null)
            ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(gameDuration);
        if (targetSize != null)
            ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(targetSize);
        if (targetDuration != null)
            ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setProgress(targetDuration);
        if (targetsPerSecond != null)
            ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setProgress(targetsPerSecond);
        if (random != null)
            ((CompoundButton) rootView.findViewById(R.id.randomBox)).setChecked(random);
        if (respawn != null)
            ((CompoundButton) rootView.findViewById(R.id.respawnBox)).setChecked(respawn);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bronzeButton:
                adjustGameSetting(RankLevel.BRONZE);
                break;
            case R.id.silverButton:
                adjustGameSetting(RankLevel.SILVER);
                break;
            case R.id.goldButton:
                adjustGameSetting(RankLevel.GOLD);
                break;
            case R.id.startButton:
                onStartButtonClick();
                break;
            case R.id.bronzeLeaderButton:
                onLeaderBoardClick(RankLevel.BRONZE);
                break;
            case R.id.silverLeaderButton:
                onLeaderBoardClick(RankLevel.SILVER);
                break;
            case R.id.goldLeaderButton:
                onLeaderBoardClick(RankLevel.GOLD);
                break;

        }
    }

    private void onLeaderBoardClick(RankLevel rankLevel) {
        if (getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
            switch (rankLevel) {
                case BRONZE:
                    switch (gameType) {
                        case TWITCH:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.twi_b))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case PRECISION:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.pre_b))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case REACTION:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.rea_b))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case TIME_CHALLENGE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.tc_b))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                    }
                    break;
                case SILVER:
                    switch (gameType) {
                        case TWITCH:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.twi_s))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case PRECISION:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.pre_s))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case REACTION:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.rea_s))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case TIME_CHALLENGE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.tc_s))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                    }
                    break;
                case GOLD:
                    switch (gameType) {
                        case TWITCH:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.twi_g))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case PRECISION:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.pre_g))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case REACTION:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.rea_g))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                        case TIME_CHALLENGE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .getLeaderboardIntent(getString(R.string.tc_g))
                                    .addOnSuccessListener(intent -> startActivityForResult(intent, RC_LEADERBOARD_UI));
                            break;
                    }
                    break;
            }
        }
    }

    private void onStartButtonClick() {
        SpawnType spawnType;

        /* 1 = random respawn
         * 2 = instant respawn
         * 3 = timed respawn
         */
        if (((CheckBox) rootView.findViewById(R.id.randomBox)).isChecked())
            spawnType = SpawnType.RANDOM;
        else if (((CheckBox) rootView.findViewById(R.id.respawnBox)).isChecked())
            spawnType = SpawnType.INSTANT;
        else
            spawnType = SpawnType.TIME;

        GameModel gameModel = new GameModel((((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress()),
                (((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).getProgress()),
                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress(),
                (((SeekBar) rootView.findViewById(R.id.targetPerSecondSeek)).getProgress() + 20) / 10,
                spawnType, gameType);

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new GameFragment(gameModel), "game_tag").commit();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView targetSizeText = rootView.findViewById(R.id.targetSizeText);
        ImageView target = rootView.findViewById(R.id.targetView);
        TextView gameDurationText = rootView.findViewById(R.id.gameDurationText);
        TextView targetDurationText = rootView.findViewById(R.id.targetDurationText);
        final TextView targetPerSecondText = rootView.findViewById(R.id.targetPerSecondText);

        switch (seekBar.getId()) {
            case R.id.targetSizeSeek:
                DisplayMetrics displayMetrics = Objects.requireNonNull(getActivity()).
                        getResources().getDisplayMetrics();
                int px = Math.round((progress + 30) * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                targetSizeText.setText(String.format(Locale.getDefault(), "%dpx", px / 2));
                target.setLayoutParams(new LinearLayout.LayoutParams(px, px));
                break;
            case R.id.gameDurationSeek:
                double size = (((double) progress) + 1) * (.5) + 14.5;
                gameDurationText.setText(String.format("%s sec", size));
                break;
            case R.id.targetDurationSeek:
                size = (((double) progress) + 1) / 100;
                targetDurationText.setText(String.format("%s sec", size));
                break;
            case R.id.targetPerSecondSeek:
                double tps = (((double) progress) + 20) / 10;
                targetPerSecondText.setText(String.format("%s %s", tps, getString(R.string.target_per_second)));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox randomBox = rootView.findViewById(R.id.randomBox);
        CheckBox respawnBox = rootView.findViewById(R.id.respawnBox);
        switch (buttonView.getId()) {
            case R.id.randomBox:
                if (isChecked) {
                    respawnBox.setChecked(false);
                }
                break;
            case R.id.respawnBox:
                if (isChecked) {
                    randomBox.setChecked(false);
                }
                break;
        }
    }

    @Override
    public void updateGooglePlaySignInState(View rootView) {
        rootView = rootView != null ? rootView : getView();
        if (rootView != null) {
            if (getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
                rootView.findViewById(R.id.bronzeLeaderButton).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.silverLeaderButton).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.goldLeaderButton).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.bronzeLeaderButton).setVisibility(View.GONE);
                rootView.findViewById(R.id.silverLeaderButton).setVisibility(View.GONE);
                rootView.findViewById(R.id.goldLeaderButton).setVisibility(View.GONE);
            }

        }
    }
}
