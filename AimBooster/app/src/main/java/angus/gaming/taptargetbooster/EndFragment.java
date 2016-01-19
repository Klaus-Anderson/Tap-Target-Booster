package angus.gaming.taptargetbooster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.text.DecimalFormat;
import java.util.HashMap;

import angus.gaming.taptargetbooster.R;

public class EndFragment extends Fragment {
    private HashMap scoreMap;
    private String gameType;
    private GoogleApiClient mGoogleApiClient;

    public EndFragment(String _gameType, HashMap _scoreMap) {
            gameType=_gameType;
            scoreMap=_scoreMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_end, container, false);

        mGoogleApiClient = ((MainActivity) this.getActivity()).getmGoogleApiClient();
        int success = (int)((double) scoreMap.get("success"));
        int attempt = (int)((double) scoreMap.get("attempt"));
        int gameDurationMills = (int)((double) scoreMap.get("gameDurationMillis"));
        int leaderboard = (int)((double) scoreMap.get("lb"));

        double score = (double) scoreMap.get("score");
        double quitTime = (double) scoreMap.get("quitTime");
        double radius = ((double) scoreMap.get("targetRadius"))/2;

        DecimalFormat df = new DecimalFormat("###.###");

        ((TextView)rootView.findViewById(R.id.accuracyFinal)).setText(df.format(score)+"");
        ((TextView)rootView.findViewById(R.id.accuracyRate)).setText("%");
        ((TextView)rootView.findViewById(R.id.targetsFinal)).setText((success)+"/"+(attempt));

        ((TextView)rootView.findViewById(R.id.targetRadius)).setText(radius+"");

        if(gameType.equals("twitch")) {
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.twitch);
            ((TextView)rootView.findViewById(R.id.accuracyRate)).setText("ms");
            if(quitTime==0) {
                if (leaderboard == 1) {
                    //bronze leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.twi_b), ((long)(score*1000)));
                } else if (leaderboard == 2) {
                    //silver leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.twi_s), ((long)(score*1000)));
                } else if (leaderboard == 3) {
                    //gold leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.twi_g), ((long)(score*1000)));
                }
                if (leaderboard!=0 && score<=350){
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.itchy));
                }
            }
        }
        if(gameType.equals("ds")) {
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.double_shot);
        }
        if(gameType.equals("precision")) {
            DecimalFormat dfg = new DecimalFormat("###.##");

            ((TextView)rootView.findViewById(R.id.accuracyFinal)).setText(dfg.format(score)+"");
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.precision);
            ((TextView)rootView.findViewById(R.id.accuracyRate)).setText("px");
            if(quitTime==0) {
                if (leaderboard == 1) {
                    //bronze leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.pre_b), ((long)(score*100)));
                } else if (leaderboard == 2) {
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.pre_s), ((long)(score*100)));
                    //silver leaderboard
                } else if (leaderboard == 3) {
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.pre_g), ((long)(score*100)));
                    //gold leaderboard
                }
                if (leaderboard!=0 && score<=30){
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.sniper));
                }
            }
        }
        if(gameType.equals("reaction")) {
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.reaction);
            ((TextView)rootView.findViewById(R.id.accuracyRate)).setText("ms");
            ((TextView)rootView.findViewById(R.id.targetRadius)).setVisibility(View.GONE);
            ((TextView)rootView.findViewById(R.id.targetRadiusTitle)).setVisibility(View.GONE);
            if(quitTime==0) {
                if (leaderboard == 1) {
                    //bronze leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.rea_b), ((long)(score*1000)));
                } else if (leaderboard == 2) {
                    //silver leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.rea_s), ((long)(score*1000)));
                } else if (leaderboard == 3) {
                    //gold leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.rea_g), ((long)(score*1000)));
                }
                if (leaderboard!=0 && score<=300){
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.lightning));

                }
            }
        }
        if(gameType.equals("speed")) {
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.speed);
        }
        if(gameType.equals("tc")) {
            double aver = Math.floor((attempt/(gameDurationMills-(quitTime))) * 100000) / 100;
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.time_challenge);
            ((TextView)rootView.findViewById(R.id.accuracyRate)).setText("cps");
            ((TextView)rootView.findViewById(R.id.accuracyFinal)).setText(df.format(aver)+"");
            ((TextView)rootView.findViewById(R.id.targetsFinal)).setText((success)+"");
            ((TextView)rootView.findViewById(R.id.targetRadius)).setVisibility(View.GONE);
            ((TextView)rootView.findViewById(R.id.targetRadiusTitle)).setVisibility(View.GONE);
            if(quitTime==0) {
                if (leaderboard == 1) {
                    //bronze leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.tc_b), ((long)(aver*100)));
                } else if (leaderboard == 2) {
                    //silver leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.tc_s), ((long)(aver*100)));
                } else if (leaderboard == 3) {
                    //gold leaderboard
                    Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.tc_g), ((long)(aver*100)));
                }
                if (leaderboard!=0 && aver>=9){
                    Games.Achievements.unlock(mGoogleApiClient,
                            getString(R.string.on_click_hero));
                }
                if (leaderboard!=0 && aver>=10){
                    Games.Achievements.unlock(mGoogleApiClient,
                            getString(R.string.ooo_thats_fast));
                }
            }
        }
        if(gameType.equals("tracking")) {
            ((TextView)rootView.findViewById(R.id.gameType)).setText(R.string.twitch);
        }
        return rootView;
    }
}
