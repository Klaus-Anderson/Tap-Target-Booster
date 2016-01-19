package angus.gaming.taptargetbooster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.util.HashMap;

import angus.gaming.taptargetbooster.R;

public class SettingsFragment extends Fragment {

    private String gameType;
    private MainActivity activity;
    private View rootView;
    private String leaderboard;
    private GoogleApiClient mGoogleApiClient;

    public SettingsFragment(String _gameType, MainActivity _activity) {
        gameType=_gameType;
        activity = _activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button startButton,
                bronzeButton, silverButton, goldButton,
                bronzeLeaderButton, silverLeaderButton, goldLeaderButton;

        leaderboard = "none";

        startButton = (Button) rootView.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    // Call a Play Games services API method, for example:
                    if (gameType == "tc" || gameType == "reaction") {
                        if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 60)
                            leaderboard = "bronze";
                        else if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 90)
                            leaderboard = "silver";
                        else if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 150)
                            leaderboard = "gold";
                    } else if (gameType == "precision") {
                        if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 60 &&
                                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress() == 60 &&
                                ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).getProgress() == 124 &&
                                ((CheckBox) rootView.findViewById(R.id.randomBox)).isSelected() &&
                                !((CheckBox) rootView.findViewById(R.id.respawnBox)).isSelected())
                            leaderboard = "bronze";
                        else if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 90 &&
                                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress() == 32 &&
                                ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).getProgress() == 79 &&
                                ((CheckBox) rootView.findViewById(R.id.randomBox)).isSelected() &&
                                !((CheckBox) rootView.findViewById(R.id.respawnBox)).isSelected())
                            leaderboard = "silver";
                        else if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 150 &&
                                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress() == 14 &&
                                ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).getProgress() == 54 &&
                                ((CheckBox) rootView.findViewById(R.id.randomBox)).isSelected() &&
                                !((CheckBox) rootView.findViewById(R.id.respawnBox)).isSelected())
                            leaderboard = "gold";
                    } else if (gameType == "twitch") {
                        if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 60 &&
                                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress() == 60 &&
                                ((CheckBox) rootView.findViewById(R.id.randomBox)).isSelected())
                            leaderboard = "bronze";
                        else if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 90 &&
                                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress() == 30 &&
                                ((CheckBox) rootView.findViewById(R.id.randomBox)).isSelected())
                            leaderboard = "silver";
                        else if (((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress() == 150 &&
                                ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress() == 15 &&
                                ((CheckBox) rootView.findViewById(R.id.randomBox)).isSelected())
                            leaderboard = "gold";
                    } else {
                        leaderboard = "none";
                    }
                }
                HashMap gameMap = new HashMap();
                gameMap.put("td",(double)(((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).getProgress()+1)/10);
                gameMap.put("ts",(double)((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).getProgress()+30);
                gameMap.put("tps",(double)(((SeekBar) rootView.findViewById(R.id.targetPerSecondSeek)).getProgress()+20)/10);
                gameMap.put("gd", (double)(((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).getProgress()+1)*(.5)+14.5);
                gameMap.put("lb", leaderboard);

                double spawnType;

                /**1 = random respawn
                 * 2 = instant respawn
                 * 3 = timed respawn
                 */
                if(((CheckBox)rootView.findViewById(R.id.randomBox)).isChecked())
                    spawnType=1;
                else if(((CheckBox)rootView.findViewById(R.id.respawnBox)).isChecked())
                    spawnType=2;
                else
                    spawnType=0;


                gameMap.put("spawnType",spawnType);
                ((MainActivity) SettingsFragment.this.getActivity()).newGame(gameType,gameMap);
            }
        });

        bronzeButton = (Button) rootView.findViewById(R.id.bronzeButton);
        silverButton = (Button) rootView.findViewById(R.id.silverButton);
        goldButton = (Button) rootView.findViewById(R.id.goldButton);

        mGoogleApiClient = ((MainActivity) getActivity()).getmGoogleApiClient();

        bronzeLeaderButton = (Button) rootView.findViewById(R.id.bronzeLeaderButton);
        silverLeaderButton = (Button) rootView.findViewById(R.id.silverLeaderButton);
        goldLeaderButton = (Button) rootView.findViewById(R.id.goldLeaderButton);

        bronzeLeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    // Call a Play Games services API method, for example:
                    if(gameType.equals("twitch")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity)getActivity()).getmGoogleApiClient(),
                                getString(R.string.twi_b)), 1);
                    }
                    if(gameType.equals("ds")) {
                    }
                    if(gameType.equals("precision")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity)getActivity()).getmGoogleApiClient(),
                                getString(R.string.pre_b)), 1);
                    }
                    if(gameType.equals("reaction")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity)getActivity()).getmGoogleApiClient(),
                                getString(R.string.rea_b)), 1);
                    }
                    if(gameType.equals("tc")){
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity)getActivity()).getmGoogleApiClient(),
                                getString(R.string.tc_b)), 1);
                    }
                    if(gameType.equals("speed")) {
                    }
                    if(gameType.equals("tracking")) {
                    }
                }
            }
        });
        silverLeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    // Call a Play Games services API method, for example:
                    if (gameType.equals("twitch")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.twi_s)), 1);
                    }
                    if (gameType.equals("ds")) {
                    }
                    if (gameType.equals("precision")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.pre_s)), 1);
                    }
                    if (gameType.equals("reaction")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.rea_s)), 1);
                    }
                    if (gameType.equals("tc")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.tc_s)), 1);
                    }
                    if (gameType.equals("speed")) {
                    }
                    if (gameType.equals("tracking")) {
                    }
                }
            }
        });
        goldLeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    // Call a Play Games services API method, for example:
                    if (gameType.equals("twitch")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.twi_g)), 1);
                    }
                    if (gameType.equals("ds")) {
                    }
                    if (gameType.equals("precision")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.pre_g)), 1);
                    }
                    if (gameType.equals("reaction")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.rea_g)), 1);
                    }
                    if (gameType.equals("tc")) {
                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(((MainActivity) getActivity()).getmGoogleApiClient(),
                                getString(R.string.tc_g)), 1);
                    }
                    if (gameType.equals("speed")) {
                    }
                    if (gameType.equals("tracking")) {
                    }
                }
            }
        });


        if(gameType.equals("twitch")) {
            rootView.findViewById(R.id.tdFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);

            ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(60);
            ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(60);
            ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
            bronzeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(60);
                    ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(60);
                    ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
                }
            });
            silverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(90);
                    ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(30);
                    ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
                }
            });
            goldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(150);
                    ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(15);
                    ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
                }
            });
        }
        if(gameType.equals("ds")) {
            rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
        }
        if(gameType.equals("precision")) {
            rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
            ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(60);
            ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(60);
            ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setProgress(124);

            ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
            ((CheckBox) rootView.findViewById(R.id.respawnBox)).setSelected(false);
            bronzeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(60);
                    ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(60);
                    ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setProgress(124);
                    ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
                    ((CheckBox) rootView.findViewById(R.id.respawnBox)).setSelected(false);
                }
            });
            silverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(90);
                    ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(32);
                    ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setProgress(79);
                    ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
                    ((CheckBox) rootView.findViewById(R.id.respawnBox)).setSelected(false);
                }
            });
            goldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(150);
                    ((SeekBar) rootView.findViewById(R.id.targetSizeSeek)).setProgress(14);
                    ((SeekBar) rootView.findViewById(R.id.targetDurationSeek)).setProgress(54);
                    ((CheckBox) rootView.findViewById(R.id.randomBox)).setSelected(true);
                    ((CheckBox) rootView.findViewById(R.id.respawnBox)).setSelected(false);
                }
            });
        }
        if(gameType.equals("reaction")||gameType.equals("tc")) {
            rootView.findViewById(R.id.tpsFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.tdFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.tsFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.randomBox).setVisibility(View.GONE);
            rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
            rootView.findViewById(R.id.checkFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.targetView).setVisibility(View.GONE);


            ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(60);
            bronzeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(60);
                }
            });
            silverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(90);
                }
            });
            goldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SeekBar) rootView.findViewById(R.id.gameDurationSeek)).setProgress(150);
                }
            });
        }
        if(gameType.equals("speed")) {
            rootView.findViewById(R.id.tdFrame).setVisibility(View.GONE);
            rootView.findViewById(R.id.randomBox).setVisibility(View.GONE);
            rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
            rootView.findViewById(R.id.checkFrame).setVisibility(View.GONE);
        }
        if(gameType.equals("tracking")) {
            rootView.findViewById(R.id.respawnBox).setVisibility(View.GONE);
        }
        this.setValues(gameType);

        return rootView;
    }

    private void setValues(String gameType) {

        SeekBar targetSizeSeek = (SeekBar) rootView.findViewById(R.id.targetSizeSeek);
        final TextView targetSizeText = (TextView) rootView.findViewById(R.id.targetSizeText);
        final ImageView target = (ImageView) rootView.findViewById(R.id.targetView);
        targetSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DisplayMetrics displayMetrics = SettingsFragment.this.getActivity().
                        getResources().getDisplayMetrics();
                int px = Math.round((progress+30) * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                targetSizeText.setText((px/2)+"px");
                target.setLayoutParams(new LinearLayout.LayoutParams(px, px));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar gameDurationSeek = (SeekBar) rootView.findViewById(R.id.gameDurationSeek);
        final TextView gameDurationText = (TextView) rootView.findViewById(R.id.gameDurationText);
        gameDurationSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double size = (((double)progress)+1)*(.5)+14.5;
                gameDurationText.setText(size+" sec");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar targetDurationSeek = (SeekBar) rootView.findViewById(R.id.targetDurationSeek);
        final TextView targetDurationText = (TextView) rootView.findViewById(R.id.targetDurationText);
        targetDurationSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                double size = (((double)progress)+1)/100;
                targetDurationText.setText(size+" sec");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar targetPerSecondSeek = (SeekBar) rootView.findViewById(R.id.targetPerSecondSeek);
        final TextView targetPerSecondText = (TextView) rootView.findViewById(R.id.targetPerSecondText);
        targetPerSecondSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double tps = (((double)progress)+20)/10;
                targetPerSecondText.setText(tps+" "+getString(R.string.target_per_second));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final CheckBox randomBox = (CheckBox) rootView.findViewById(R.id.randomBox);
        final CheckBox respawnBox = (CheckBox) rootView.findViewById(R.id.respawnBox);
        randomBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    respawnBox.setChecked(false);
                }
            }
        });
        respawnBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    randomBox.setChecked(false);
                }
            }
        });


        gameDurationSeek.setProgress(30);
        if(gameType.equals("twitch")) {
            targetDurationSeek.setProgress(79);
            targetSizeSeek.setProgress(50);
            randomBox.setChecked(true);
        }
        if(gameType.equals("ds")) {
            targetDurationSeek.setProgress(99);
            targetSizeSeek.setProgress(50);
            randomBox.setChecked(true);
        }
        if(gameType.equals("precision")) {
            targetDurationSeek.setProgress(99);
            targetSizeSeek.setProgress(1);
            targetSizeSeek.setProgress(0);
            randomBox.setChecked(true);
            respawnBox.setChecked(false);
        }
        if(gameType.equals("reaction")) {
        }
        if(gameType.equals("speed")) {
            targetSizeSeek.setProgress(49);
            targetPerSecondSeek.setProgress(80);
        }
        if(gameType.equals("tc")) {
        }
        if(gameType.equals("tracking")) {
            targetDurationSeek.setProgress(49);
            targetSizeSeek.setProgress(50);
            targetPerSecondSeek.setProgress(80);
            randomBox.setChecked(true);
        }
    }
}
