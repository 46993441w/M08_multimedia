package david.juez.multimedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * A placeholder fragment containing a simple view.
 */
public class FotoActivityFragment extends Fragment {
    private Button bt_hacerfoto;
    private Firebase fotos;
    private String fotopath;
    private String fotoname;

    public FotoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foto, container, false);

        //FIreBase
        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://uf2multimediadavid.firebaseio.com/");

        fotos = ref.child("fotos");

        bt_hacerfoto = (Button) view.findViewById(R.id.button1);

        //Añadimos el Listener Boton
        bt_hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent para llamar a la Camara
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Creamos una carpeta en la memoria del terminal
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "david");
                imagesFolder.mkdirs();
                //añadimos el nombre de la imagen
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                fotoname = "IMG_" + timeStamp + ".jpg";

                File image = new File(imagesFolder, fotoname);
                fotopath = image.getAbsolutePath();
                Uri uriSavedImage = Uri.fromFile(image);
                //Le decimos al Intent que queremos grabar la imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                //Lanzamos la aplicacion de la camara con retorno (forResult)
                startActivityForResult(cameraIntent, 1);
            }
        });
        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
            final Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            AsyncHttpClient client = new AsyncHttpClient();
            //New File
            File file = new File(fotopath);
            RequestParams params = new RequestParams();
            try {
                //"photos" is Name of the field to identify file on server
                params.put("fotoUp", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            client.post(getContext(), "http://fotosapp.16mb.com/upload.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.print("Failed..");
                    getActivity().finish();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.print("Success..");
                    String ruta = new String(responseBody);
                    Foto foto = new Foto();
                    foto.setRuta(ruta);
                    foto.setName(fotoname);
                    foto.setLat(location.getLatitude());
                    foto.setLon(location.getLongitude());
                    Firebase newNota = fotos.push();
                    newNota.setValue(foto);
                    getActivity().finish();
                }
            });
        }
    }
}
