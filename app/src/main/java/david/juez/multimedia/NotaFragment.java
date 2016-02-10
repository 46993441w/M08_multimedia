package david.juez.multimedia;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;


public class NotaFragment extends Fragment {

    private EditText etTitle;
    private EditText etDescription;
    private Firebase users;

    public NotaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nota, container, false);

        //FIreBase
        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://uf2multimediadavid.firebaseio.com/");

        users = ref.child("notes");

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etDescription = (EditText) view.findViewById(R.id.etDescription);


        return view;
    }

    public void addNota(){
        if(!etTitle.getText().equals("") && !etDescription.getText().equals("")) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            Nota nota = new Nota();
            nota.setTitle(etTitle.getText().toString());
            nota.setMessage(etDescription.getText().toString());
            nota.setLat(location.getLatitude());
            nota.setLon(location.getLongitude());
            Firebase newNota = users.push();
            newNota.setValue(nota);
            getActivity().finish();
        } else if(etTitle.getText().equals("")){
            etTitle.requestFocus();
        } else {
            etDescription.requestFocus();
        }
    }

    public void finish(){
        getActivity().finish();
    }

}
