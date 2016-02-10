package david.juez.multimedia;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NotaActivity extends AppCompatActivity {

    FragmentManager fm;
    NotaFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();
        fragment = (NotaFragment) fm.findFragmentById(R.id.listFragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.addNota();
            }
        });
        FloatingActionButton contra = (FloatingActionButton) findViewById(R.id.contra);
        contra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.finish();
            }
        });
    }

}
