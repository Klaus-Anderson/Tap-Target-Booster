package angus.gaming.taptargetbooster.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.Locale;

import angus.gaming.taptargetbooster.R;
import angus.gaming.taptargetbooster.utils.ScoreModel;

public class EndFragment extends Fragment {
    private ScoreModel scoreModel;
//    private GoogleApiClient mGoogleApiClient;

    EndFragment(ScoreModel scoreModel) {
        this.scoreModel = scoreModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_end, container, false);

//        mGoogleApiClient = ((MainActivity) this.getActivity()).getmGoogleApiClient();
//        int attempt = (int) ((double) scoreMap.get("attempt"));
//        int gameDurationMills = (int) ((double) scoreMap.get("gameDurationMillis"));
//        int leaderboard = (int) ((double) scoreMap.get("lb"));
//
//        double score = (double) scoreMap.get("score");
//        double quitTime = (double) scoreMap.get("quitTime");
//        double radius = ((double) scoreMap.get("targetRadius")) / 2;

        DecimalFormat df = new DecimalFormat("###.###");

        ((TextView) rootView.findViewById(R.id.accuracyFinal)).setText(String.format("%s", df.format(scoreModel.getScoreActual())));
        ((TextView) rootView.findViewById(R.id.accuracyRate)).setText("%");
        ((TextView) rootView.findViewById(R.id.targetsFinal)).setText(String.format("%s/%s", scoreModel.getSuccess(), scoreModel.getAttempt()));

        ((TextView) rootView.findViewById(R.id.targetRadius)).setText(String.format(Locale.getDefault(), "%d", scoreModel.getTargetPxSize() / 2));

        switch (scoreModel.getGameType()) {
            case TWITCH:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.twitch);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.milliseconds);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.twi_b), ((long)(score*1000)));
                            break;
                        case SILVER:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.twi_b), ((long)(score*1000)));
                            break;
                        case GOLD:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.twi_g), ((long)(score*1000)));
                            break;
                    }
                    if (scoreModel.getScoreActual() <= 350) {
//                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.itchy));
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
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.pre_b), ((long)(score*100)));
                            break;
                        case SILVER:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.pre_s), ((long)(score*100)));
                            break;
                        case GOLD:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.pre_g), ((long)(score*100)));
                            break;
                    }
                    if (scoreModel.getScoreActual() <= 30) {
//                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.sniper));
                    }
                }
                break;
            case REACTION:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.reaction);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.milliseconds);
                rootView.findViewById(R.id.targetRadius).setVisibility(View.GONE);
                rootView.findViewById(R.id.targetRadiusTitle).setVisibility(View.GONE);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.rea_b), ((long)(score*1000)));
                            break;
                        case SILVER:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.rea_s), ((long)(score*1000)));
                            break;
                        case GOLD:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.rea_g), ((long)(score*1000)));
                            break;
                    }
                    if (scoreModel.getScoreActual() <= 300) {
//                        Games.Achievements.unlock(mGoogleApiClient, getString(R.string.lightning));
                    }
                }
                break;
            case SPEED:
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.speed);
                break;
            case TIME_CHALLENGE:
                double aver = Math.floor((scoreModel.getAttempt() /
                        (scoreModel.getGameDurationMillis() - (scoreModel.getQuitTime()))) * 100000)
                        / 100;
                ((TextView) rootView.findViewById(R.id.gameType)).setText(R.string.time_challenge);
                ((TextView) rootView.findViewById(R.id.accuracyRate)).setText(R.string.clicks_per_second);
                ((TextView) rootView.findViewById(R.id.accuracyFinal)).setText(String.format("%s", df.format(aver)));
                ((TextView) rootView.findViewById(R.id.targetsFinal)).setText(String.format("%s", scoreModel.getSuccess()));
                rootView.findViewById(R.id.targetRadius).setVisibility(View.GONE);
                rootView.findViewById(R.id.targetRadiusTitle).setVisibility(View.GONE);
                if (scoreModel.getQuitTime() == 0 && scoreModel.getRankAttempt() != null) {
                    switch (scoreModel.getRankAttempt()) {
                        case BRONZE:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.tc_b), ((long)(aver*100)));
                            break;
                        case SILVER:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.tc_s), ((long)(aver*100)));
                            break;
                        case GOLD:
//                            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.tc_g), ((long)(aver*100)));
                            break;
                    }
                    if (aver >= 9) {
//                        Games.Achievements.unlock(mGoogleApiClient,
//                                getString(R.string.on_click_hero));
                    } else if (aver >= 10) {
//                        Games.Achievements.unlock(mGoogleApiClient,
//                                getString(R.string.ooo_thats_fast));
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
