package angus.gaming.taptargetbooster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import angus.gaming.taptargetbooster.R;
import angus.harry.example.games.basegameutils.BaseGameUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;


public class MainActivity
        extends ActionBarActivity
        implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener  {
    private int level;
    private GameFragment gf;

    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        level=0;
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        mGoogleApiClient.connect();

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MenuFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void twitchSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment("twitch", this)).commit();
    }

    public void doubleShotSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingsFragment("ds", this)).commit();
    }

    public void timeChallengeSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingsFragment("tc", this)).commit();
    }

    public void reactionSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingsFragment("reaction", this)).commit();
    }

    public void precisionSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingsFragment("precision", this)).commit();
    }

    public void speedSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingsFragment("speed", this)).commit();
    }

    public void trackingSettings(View v) {
        level++;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new SettingsFragment("tracking",this)).commit();
    }

    public void achievementsClick(View v) {
        startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                2);
    }

    @Override
    public void onBackPressed(){
        if(level==0)
            MainActivity.super.onBackPressed();
        else if (level==2) {
            ((GameFragment) getSupportFragmentManager().findFragmentByTag("game_tag")).getCdTimer().cancel();
            ((GameFragment) getSupportFragmentManager().findFragmentByTag("game_tag")).gameOver();
        }
        else{
            level = 0;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MenuFragment()).commit();
        }
    }

    public void newGame(String gameType, HashMap gameMap){
        level=2;
        gf = new GameFragment(gameType,gameMap);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, gf, "game_tag").commit();

    }

    public void newScore(String gameType, HashMap scoreMap) {
        level=1;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new EndFragment(gameType, scoreMap)).commit();
    }

    public void newMenu(){
        level=0;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MenuFragment()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.achievementsButton).setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        findViewById(R.id.achievementsButton).setVisibility(View.GONE);
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }
}