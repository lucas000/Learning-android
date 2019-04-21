package pesquisaeletronica.com.br.pesquisall;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PesquisaEletronica extends AppCompatActivity {

    private Button iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_eletronica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iniciar = (Button) findViewById(R.id.btnComecar);
    }

    public void iniciarPesquisa(View view){
        Intent intent = new Intent(this, DadosUsuarios.class);
        startActivity(intent);
    }

}
