package pesquisaeletronica.com.br.pesquisall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DadosUsuarios extends AppCompatActivity {

    private EditText nomeCompletoUsuario, numeroTituloUsuario, zonaEleitoralUsuario, secaoEleitoralUsuario, cidadeUsuario;
    private Spinner estadoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_leitor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nomeCompletoUsuario = (EditText) findViewById(R.id.edtNome);
        numeroTituloUsuario = (EditText) findViewById(R.id.edtTitulo);
        zonaEleitoralUsuario = (EditText) findViewById(R.id.edtZona);
        secaoEleitoralUsuario = (EditText) findViewById(R.id.edtSecao);
        cidadeUsuario = (EditText) findViewById(R.id.edtCidade);
        estadoUsuario = (Spinner) findViewById(R.id.spnEstado);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.ir_representantes:

                String estadoSelecionado = estadoUsuario.getSelectedItem().toString();

                String nome = nomeCompletoUsuario.getText().toString();
                String titulo = numeroTituloUsuario.getText().toString();
                String zona = zonaEleitoralUsuario.getText().toString();
                String secao = secaoEleitoralUsuario.getText().toString();
                String cidade = cidadeUsuario.getText().toString();
                String estado = estadoSelecionado;

                boolean resultadoVerificacao = verificaDados(nome, titulo, zona, secao, cidade, estado);

                if (resultadoVerificacao == true) {
                    Intent intent = new Intent(this, DadosRepresentantes.class);

                    Bundle bundleDadosUsuarios = new Bundle();
                    bundleDadosUsuarios.putString("NOME_COMPLETO", nome);
                    bundleDadosUsuarios.putString("NUMERO_TITULO", titulo);
                    bundleDadosUsuarios.putString("ZONA_ELEITORAL", zona);
                    bundleDadosUsuarios.putString("SECAO_ELEITORAL", secao);
                    bundleDadosUsuarios.putString("CIDADE", cidade);
                    bundleDadosUsuarios.putString("ESTADO", estado);

                    intent.putExtras(bundleDadosUsuarios);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Existem campos inválidos!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean verificaDados(String nomeuser, String titulo, String zona, String secao, String cidade, String estado){
        boolean resultado = false;


        nomeuser = nomeuser.trim();
        titulo = titulo.trim();
        zona = zona.trim();
        secao = secao.trim();
        cidade = cidade.trim();

        if (nomeuser.isEmpty() || titulo.isEmpty() || zona.isEmpty() || secao.isEmpty() || cidade.isEmpty() || estado.equals("Selecionar")){
            resultado = false;
        } else {
            resultado = true;
        }

        return resultado;
    }
}
