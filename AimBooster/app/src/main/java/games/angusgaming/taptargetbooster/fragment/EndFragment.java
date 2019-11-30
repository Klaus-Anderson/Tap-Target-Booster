package games.angusgaming.taptargetbooster.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

import games.angusgaming.taptargetbooster.R;
import games.angusgaming.taptargetbooster.utils.ScoreModel;

public class EndFragment extends Fragment {
    private ScoreModel scoreModel;

    EndFragment(ScoreModel scoreModel) {
        this.scoreModel = scoreModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_end, container, false);

        DecimalFormat df = new DecimalFormat("###.###");

        ((TextView) rootView.findViewById(R.id.accuracyFinal)).setText(String.format("%s", df.format(scoreModel.getScoreActual())));
        ((TextView) rootView.findViewById(R.id.accuracyRate)).setText("%");
        ((TextView) rootView.findViewById(R.id.targetsFinal)).setText(String.format("%s/%s", (int) scoreModel.getSuccess(), (int) scoreModel.getAttempt()));

        ((TextView) rootView.findViewById(R.id.targetRadius)).setText(String.format(Locale.getDefault(), "%d", scoreModel.getTargetPxSize() / 2));

        switch (scoreModel.getGameType()) {
            case TWITCH:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.twitch);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.milliseconds);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null &&
                        getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.twi_b), (long) (scoreModel.getScoreActual() * 1000));
                            break;
                        case SILVER:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.twi_s), (long) (scoreModel.getScoreActual() * 1000));
                            break;
                        case GOLD:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.twi_g), (long) (scoreModel.getScoreActual() * 1000));
                            break;
                    }
                    if (scoreModel.getScoreActual() <= 350) {
                        Games.getAchievementsClient(getActivity(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                .unlock(getString(R.string.itchy));
                    }
                }
                break;
            case DOUBLE_SHOT:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.double_shot);
                break;
            case PRECISION:
                DecimalFormat dfg = new DecimalFormat("###.##");

                ((TextView) rootView.findViewById(R.id.accuracyFinal)).setText(String.format("%s", dfg.format(scoreModel.getScoreActual())));
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.precision);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.pixels);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null &&
                        getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.pre_b), (long) (scoreModel.getScoreActual() * 100));
                            break;
                        case SILVER:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.pre_s), (long) (scoreModel.getScoreActual() * 100));
                            break;
                        case GOLD:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.pre_g), (long) (scoreModel.getScoreActual() * 100));
                            break;
                    }
                    if (scoreModel.getScoreActual() <= 30) {
                        Games.getAchievementsClient(getActivity(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                .unlock(getString(R.string.sniper));
                    }
                }
                break;
            case REACTION:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.reaction);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.milliseconds);
                rootView.findViewById(R.id.targetRadius).setVisibility(View.GONE);
                rootView.findViewById(R.id.targetRadiusTitle).setVisibility(View.GONE);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null &&
                        getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.rea_b), (long) (scoreModel.getScoreActual() * 1000));
                            break;
                        case SILVER:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.rea_s), (long) (scoreModel.getScoreActual() * 1000));
                            break;
                        case GOLD:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.rea_g), (long) (scoreModel.getScoreActual() * 1000));
                            break;
                    }
                    if (scoreModel.getScoreActual() <= 300) {
                        Games.getAchievementsClient(getActivity(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                .unlock(getString(R.string.lightning));
                    }
                }
                break;
            case SPEED:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.speed);
                break;
            case TIME_CHALLENGE:
                double aver = Math.floor(((double) scoreModel.getAttempt() /
                        (scoreModel.getGameDurationMillis() - (scoreModel.getQuitTime()))) * 100000)
                        / 100;
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.time_challenge);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.clicks_per_second);
                ((TextView) rootView.findViewById(R.id.accuracyFinal)).setText(String.format("%s", df.format(aver)));
                ((TextView) rootView.findViewById(R.id.targetsFinal)).setText(String.format("%s", scoreModel.getSuccess()));
                rootView.findViewById(R.id.targetRadius).setVisibility(View.GONE);
                rootView.findViewById(R.id.targetRadiusTitle).setVisibility(View.GONE);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null &&
                        getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.tc_b), (long) (aver * 100L));
                            break;
                        case SILVER:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.tc_s), (long) (aver * 100L));
                            break;
                        case GOLD:
                            Games.getLeaderboardsClient(getActivity(),
                                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                    .submitScore(getString(R.string.tc_g), (long) (aver * 100L));
                            break;
                    }
                    if (aver >= 9) {
                        Games.getAchievementsClient(getActivity(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                .unlock(getString(R.string.on_click_hero));
                    } else if (aver >= 10) {
                        Games.getAchievementsClient(getActivity(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                                .unlock(getString(R.string.ooo_thats_fast));
                    }
                }
                break;
            case TRACKING:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.twitch);
                break;
        }
        return rootView;
    }
}
