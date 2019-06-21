package experimentos.br.com.avaliaodeexperimentos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
    }

    public void abrirSobre(View v){

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/lucas000"));

        startActivity(intent);
    }
}
