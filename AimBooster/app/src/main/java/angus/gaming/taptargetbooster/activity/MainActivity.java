package angus.gaming.taptargetbooster.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import angus.gaming.taptargetbooster.R;
import angus.gaming.taptargetbooster.fragment.MenuFragment;

import static angus.gaming.taptargetbooster.utils.GooglePlayServicesConstants.RC_SIGN_IN;


public class MainActivity
        extends FragmentActivity {
//        implements GoogleApiClient.ConnectionCallbacks,
//            GoogleApiClient.OnConnectionFailedListener  {

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
//                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
//                .build();
//        mGoogleApiClient.connect();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mGoogleApiClient.disconnect();
    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//        findViewById(R.id.achievementsButton).setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//        findViewById(R.id.achievementsButton).setVisibility(View.GONE);
//        // Attempt to reconnect
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//        if (mResolvingConnectionFailure) {
//            // already resolving
//            return;
//        }
//
//        // if the sign-in button was clicked or if auto sign-in is enabled,
//        // launch the sign-in flow
//        if (mSignInClicked || mAutoStartSignInFlow) {
//            mAutoStartSignInFlow = false;
//            mSignInClicked = false;
//            mResolvingConnectionFailure = true;
//
//            // Attempt to resolve the connection failure using BaseGameUtils.
//            // The R.string.signin_other_error value should reference a generic
//            // error string in your strings.xml file, such as "There was
//            // an issue with sign-in, please try again later."
//            if (!BaseGameUtils.resolveConnectionFailure(this,
//                    mGoogleApiClient, connectionResult,
//                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
//                mResolvingConnectionFailure = false;
//            }
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().getFragments();
        if (getSupportFragmentManager().getFragments().size() > 0 &&
                !(getSupportFragmentManager().getFragments().get(0) instanceof MenuFragment)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuFragment()).commit();
        } else {
            super.onBackPressed();
        }
    }
}