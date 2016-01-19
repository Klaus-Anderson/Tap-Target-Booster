package angus.gaming.taptargetbooster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import angus.gaming.taptargetbooster.R;

/**
 * Created by Harry on 4/28/2015.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        if(((MainActivity)getActivity()).getmGoogleApiClient().isConnected()){
            rootView.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            rootView.findViewById(R.id.achievementsButton).setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}
