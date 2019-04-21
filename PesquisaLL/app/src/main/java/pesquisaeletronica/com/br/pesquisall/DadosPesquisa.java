package pesquisaeletronica.com.br.pesquisall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DadosPesquisa extends AppCompatActivity {

    private TextView nome, zona, secao, titulo, estado, cidade, dep_est, partido, dep_fed, sena, gov, pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pesquisa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentDadosUsuarios = getIntent();
        Bundle extrasPesquisa = intentDadosUsuarios.getExtras();

        nome = (TextView) findViewById(R.id.txtNomeUsuario);
        titulo = (TextView) findViewById(R.id.txtTitulo);
        zona = (TextView) findViewById(R.id.txtZona);
        secao = (TextView) findViewById(R.id.txtSecao);
        cidade = (TextView) findViewById(R.id.txtCidade);
        estado = (TextView) findViewById(R.id.txtEstado);
        dep_est = (TextView) findViewById(R.id.txtDepEst);
        dep_fed = (TextView) findViewById(R.id.txtDepFed);
        sena = (TextView) findViewById(R.id.txtSena);
        gov = (TextView) findViewById(R.id.txtGov);
        partido = (TextView) findViewById(R.id.txtPartido);
        pres = (TextView) findViewById(R.id.txtPresid);

        String nome_user = extrasPesquisa.getString("NOME_COMPLETO");
        String titulo_user = extrasPesquisa.getString("NUMERO_TITULO");
        String zona_user = extrasPesquisa.getString("ZONA_ELEITORAL");
        String secao_user = extrasPesquisa.getString("SECAO_ELEITORAL");
        String cidade_user = extrasPesquisa.getString("CIDADE");
        String estado_user = extrasPesquisa.getString("ESTADO");

        String dep__est_user = extrasPesquisa.getString("DEP_EST");
        String dep_fed_user = extrasPesquisa.getString("DEP_FED");
        String sen_user = extrasPesquisa.getString("SEN");
        String gov_user = extrasPesquisa.getString("GOV");
        String part1 = extrasPesquisa.getString("PARTIDO1");
        String part2 = extrasPesquisa.getString("PARTIDO2");
        String part3 = extrasPesquisa.getString("PARTIDO3");
        String part4 = extrasPesquisa.getString("PARTIDO4");
        String partidos = "";

        if (part1 != ""){
            partidos = partidos + part1;
            partidos = partidos + " ";
        }

        if (part2 != ""){
            partidos = partidos + part2;
            partidos = partidos + " ";
        }

        if (part3 != ""){
            partidos = partidos + part3;
            partidos = partidos + " ";
        }

        if (part4 != ""){
            partidos = partidos + part4;
            partidos = partidos + " ";
        }
        String presidente_user = extrasPesquisa.getString("PRESIDENTE");

        nome.setText(nome_user);
        titulo.setText(titulo_user);
        zona.setText(zona_user);
        secao.setText(secao_user);
        cidade.setText(cidade_user);
        estado.setText(estado_user);
        dep_est.setText(dep__est_user);
        dep_fed.setText(dep_fed_user);
        sena.setText(sen_user);
        gov.setText(gov_user);
        partido.setText(partidos);
        pres.setText(presidente_user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dados_pesquisa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.ir_home:
                Intent intent = new Intent(this, PesquisaEletronica.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
