package games.angusgaming.taptargetbooster.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import games.angusgaming.taptargetbooster.R;
import games.angusgaming.taptargetbooster.fragment.GooglePlaySupportedFragment;
import games.angusgaming.taptargetbooster.fragment.MenuFragment;

import static games.angusgaming.taptargetbooster.utils.GooglePlayServicesConstants.RC_SIGN_IN;


public class MainActivity
        extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    private void signInSilently() {
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            // The signed in account is stored in the 'account' variable.
            Fragment displayedFragment = getSupportFragmentManager().getFragments().get(0);
            if (displayedFragment instanceof GooglePlaySupportedFragment) {
                ((GooglePlaySupportedFragment) displayedFragment).updateGooglePlaySignInState(null);
            }
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
            signInClient
                    .silentSignIn()
                    .addOnCompleteListener(
                            this,
                            task -> {
                                Fragment displayedFragment = getSupportFragmentManager().getFragments().get(0);
                                if (displayedFragment instanceof GooglePlaySupportedFragment) {
                                    ((GooglePlaySupportedFragment) displayedFragment).updateGooglePlaySignInState(null);
                                }
                            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInSilently();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (!result.isSuccess()) {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
        Fragment displayedFragment = getSupportFragmentManager().getFragments().get(0);
        if (displayedFragment instanceof GooglePlaySupportedFragment) {
            ((GooglePlaySupportedFragment) displayedFragment).updateGooglePlaySignInState(null);
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