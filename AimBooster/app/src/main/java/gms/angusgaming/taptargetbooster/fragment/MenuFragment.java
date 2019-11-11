package gms.angusgaming.taptargetbooster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Objects;

import gms.angusgaming.taptargetbooster.R;
import gms.angusgaming.taptargetbooster.activity.MainActivity;
import gms.angusgaming.taptargetbooster.utils.GameType;

import static gms.angusgaming.taptargetbooster.utils.GooglePlayServicesConstants.RC_SIGN_IN;

/**
 * Created by Harry on 4/28/2015.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        rootView.findViewById(R.id.sign_in_button).setOnClickListener(this);
        rootView.findViewById(R.id.achievementsButton).setOnClickListener(this);
        rootView.findViewById(R.id.twitchButton).setOnClickListener(this);
        rootView.findViewById(R.id.precisionButton).setOnClickListener(this);
        rootView.findViewById(R.id.timeChallenge).setOnClickListener(this);
        rootView.findViewById(R.id.reactionButton).setOnClickListener(this);
        rootView.findViewById(R.id.doubleshotButton).setOnClickListener(this);
        rootView.findViewById(R.id.speedButton).setOnClickListener(this);
        rootView.findViewById(R.id.trackingButton).setOnClickListener(this);
        if (getActivity() != null && ((MainActivity) getActivity()).getSignedInAccount() != null) {
            rootView.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            rootView.findViewById(R.id.achievementsButton).setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            // start the asynchronous sign in flow
            GoogleSignInClient signInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()),
                    GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
            Intent intent = signInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        } else if (view.getId() == R.id.achievementsButton) {
//            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
//                2);
        } else {
            GameType gameType = null;
            switch (view.getId()) {
                case R.id.twitchButton:
                    gameType = GameType.TWITCH;
                    break;
                case R.id.precisionButton:
                    gameType = GameType.PRECISION;
                    break;
                case R.id.timeChallenge:
                    gameType = GameType.TIME_CHALLENGE;
                    break;
                case R.id.reactionButton:
                    gameType = GameType.REACTION;
                    break;
                case R.id.doubleshotButton:
                    gameType = GameType.DOUBLE_SHOT;
                    break;
                case R.id.speedButton:
                    gameType = GameType.SPEED;
                    break;
                case R.id.trackingButton:
                    gameType = GameType.TRACKING;
                    break;
            }
            if (gameType != null) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment(gameType)).commit();
            } else {
                throw new IllegalStateException("clicked view not on screen!");
            }
        }
    }
}
