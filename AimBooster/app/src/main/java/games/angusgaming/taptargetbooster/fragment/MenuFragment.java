package games.angusgaming.taptargetbooster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;

import java.util.Objects;

import games.angusgaming.taptargetbooster.R;
import games.angusgaming.taptargetbooster.utils.GameType;

import static games.angusgaming.taptargetbooster.utils.GooglePlayServicesConstants.RC_ACHIEVEMENT_UI;
import static games.angusgaming.taptargetbooster.utils.GooglePlayServicesConstants.RC_SIGN_IN;

/**
 * Created by Harry on 4/28/2015.
 */
public class MenuFragment extends GooglePlaySupportedFragment implements View.OnClickListener {


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
        updateGooglePlaySignInState(rootView);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            // start the asynchronous sign in flow
            GoogleSignInClient signInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()),
                    GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
            Intent intent = signInClient.getSignInIntent();
            getActivity().startActivityForResult(intent, RC_SIGN_IN);
        } else if (view.getId() == R.id.achievementsButton) {
            Games.getAchievementsClient(Objects.requireNonNull(getActivity()),
                    Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getActivity())))
                    .getAchievementsIntent().addOnSuccessListener(intent ->
                    getActivity().startActivityForResult(intent, RC_ACHIEVEMENT_UI));
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
                case R.id.trackingButton:
                case R.id.speedButton:
                case R.id.doubleshotButton:
                    Toast.makeText(getActivity(), R.string.coming_soon, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.reactionButton:
                    gameType = GameType.REACTION;
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

    @Override
    public void updateGooglePlaySignInState(View rootView) {
        rootView = rootView != null ? rootView : getView();
        if (rootView != null) {
            if (getActivity() != null && GoogleSignIn.getLastSignedInAccount(getActivity()) != null) {
                rootView.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                rootView.findViewById(R.id.achievementsButton).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.achievementsButton).setVisibility(View.GONE);
            }
        }
    }
}
