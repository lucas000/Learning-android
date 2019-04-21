package pesquisaeletronica.com.br.pesquisall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class DadosRepresentantes extends AppCompatActivity {

    private Spinner depEstadual, depFederal, senador, governador;
    private String partido1 = "", partido2 = "", partido3 = "", partido4 = "";
    private RadioGroup presidente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_representantes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        depEstadual = (Spinner) findViewById(R.id.spnDepEst);
        depFederal = (Spinner) findViewById(R.id.spnDepFed);
        senador = (Spinner) findViewById(R.id.spnSen);
        governador = (Spinner) findViewById(R.id.spnGov);
        presidente = (RadioGroup) findViewById(R.id.rdGrupoPresidente);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_representantes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.ir_resultados:
                String depEstSelecionado = depEstadual.getSelectedItem().toString();
                String depEst = depEstSelecionado;

                String depFedSelecionado = depFederal.getSelectedItem().toString();
                String depFed = depFedSelecionado;

                String senSelecionado = senador.getSelectedItem().toString();
                String senad = senSelecionado;

                String govSelecionado = governador.getSelectedItem().toString();
                String gov = govSelecionado;

                presidente = findViewById(R.id.rdGrupoPresidente);
                int id = presidente.getCheckedRadioButtonId();

                String candidatoPresidente = "";

                switch (id) {
                    case R.id.rdPresidente1:
                        candidatoPresidente = "Bolsonaro";
                        break;
                    case R.id.rdPresidente2:
                        candidatoPresidente = "Haddad";
                        break;
                }

                Intent intentDadosUsuarios = getIntent();
                Bundle extrasDadosUsuarios = intentDadosUsuarios.getExtras();

                Intent intentDadosRepresentantes = new Intent(this, DadosPesquisa.class);

                boolean verificacao = verificaDados(depEst, depFed, senad, gov, candidatoPresidente);

                if (verificacao == true){
                    Bundle bundleDadosRepresentantes = new Bundle();
                    bundleDadosRepresentantes.putString("DEP_EST", depEst);
                    bundleDadosRepresentantes.putString("DEP_FED", depFed);
                    bundleDadosRepresentantes.putString("SEN", senad);
                    bundleDadosRepresentantes.putString("GOV", gov);
                    bundleDadosRepresentantes.putString("PRESIDENTE", candidatoPresidente);

                    //Partidos
                    bundleDadosRepresentantes.putString("PARTIDO1", partido1);
                    bundleDadosRepresentantes.putString("PARTIDO2", partido2);
                    bundleDadosRepresentantes.putString("PARTIDO3", partido3);
                    bundleDadosRepresentantes.putString("PARTIDO4", partido4);

                    intentDadosRepresentantes.putExtras(extrasDadosUsuarios);
                    intentDadosRepresentantes.putExtras(bundleDadosRepresentantes);
                    startActivity(intentDadosRepresentantes);
                } else{
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean verificaDados(String depfed, String depest, String sena, String gov, String presidente){
        boolean resultado = false;

        if (depfed.equals("Selecionar") || depest.equals("Selecionar") || sena.equals("Selecionar") || gov.equals("Selecionar") ||  presidente.equals("")){
            resultado = false;
        } else {
            resultado = true;
        }

        return resultado;
    }

    public void clicouPartido(View view){
        boolean estado = ((CheckBox) view). isChecked();

        switch (view.getId()) {
            case R.id.checkBoxDEM:
                if (estado) {
                    partido1 = "DEM";
                } else{
                    partido1 = "";
                }
                break;
            case R.id.checkBoxPPS:
                if (estado) {
                    partido2 = "PPS";
                } else{
                    partido2 = "";
                }
                break;

            case R.id.checkBoxPode:
                if (estado) {
                    partido3 = "PODE";
                } else{
                    partido3 = "";
                }
                break;

            case R.id.checkBoxPtb:
                if (estado) {
                    partido4 = "PTB";
                } else{
                    partido4 = "";
                }
                break;
        }
    }
}
