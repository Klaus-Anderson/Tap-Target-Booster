package games.angusgaming.taptargetbooster.fragment;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class GooglePlaySupportedFragment extends Fragment {
    public abstract void updateGooglePlaySignInState(@Nullable View rootView);
}
