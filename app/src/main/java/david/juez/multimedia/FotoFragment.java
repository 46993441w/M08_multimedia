package david.juez.multimedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class FotoFragment extends Fragment {
    FirebaseListAdapter mAdapter;
    ListView fotoList;

    public FotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        fotoList = (ListView) view.findViewById(R.id.llista);

        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://uf2multimediadavid.firebaseio.com/");

        Firebase fotos = ref.child("fotos");

        mAdapter = new FirebaseListAdapter<Foto>(getActivity(), Foto.class, R.layout.foto_row, fotos) {
            @Override
            protected void populateView(View view, Foto foto, int position) {
                ((TextView)view.findViewById(R.id.tvRuta)).setText(foto.getRuta());
                //Creamos un bitmap con la imagen recientemente
                //almacenada en la memoria
                Bitmap bMap = BitmapFactory.decodeFile(foto.getRuta());
                //Añadimos el bitmap al imageView para mostrarlo por pantalla
                ((ImageView)view.findViewById(R.id.iFoto)).setImageBitmap(bMap);
            }
        };
        fotoList.setAdapter(mAdapter);



        return view;
    }
}
