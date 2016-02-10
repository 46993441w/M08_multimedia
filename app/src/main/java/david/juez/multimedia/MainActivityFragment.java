package david.juez.multimedia;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    FirebaseListAdapter mAdapter;
    ListView notaList;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        notaList = (ListView) view.findViewById(R.id.llista);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://uf2multimediadavid.firebaseio.com/");

        Firebase users = ref.child("notes");

        mAdapter = new FirebaseListAdapter<Nota>(getActivity(), Nota.class, android.R.layout.two_line_list_item, users) {
            @Override
            protected void populateView(View view, Nota nota, int position) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(nota.getTitle());
                ((TextView)view.findViewById(android.R.id.text2)).setText(nota.getMessage());
            }
        };
        notaList.setAdapter(mAdapter);



        return view;
    }
}
