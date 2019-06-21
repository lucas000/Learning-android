package experimentos.br.com.avaliaodeexperimentos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

//Tela para adicionar uma amostra a um experimento e salvar no BD
public class AdicionarAmostra extends AppCompatActivity {

    private ExperimentoRepositorio clienteRepositorio;

    private SQLiteDatabase conexao;
    private BdHelperExperimento dadosOpenHelper;
    private EditText totalAmostrasBloco, totalBlocos, altura, milimetroColmo, alturaInsercao;
    private Experimento experimento;

    private String proprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_amostra);

        totalAmostrasBloco = (EditText) findViewById(R.id.edtAmostras);

        totalAmostrasBloco.setEnabled(false);
        totalBlocos = (EditText) findViewById(R.id.edtBloco);
        totalBlocos.setEnabled(false);

        altura = (EditText) findViewById(R.id.edtAltura);


        milimetroColmo = (EditText) findViewById(R.id.edtMilimetroComo);
        alturaInsercao = (EditText) findViewById(R.id.edtAlturaInsercao);

        verificaParametro();

    }

    private void verificaParametro(){
        //A classe Bundle que contem os parametros passado na janela
        Bundle bundle = getIntent().getExtras();

        experimento = new Experimento();

        if ((bundle != null) && (bundle.containsKey("EXPERIMENTO"))){
            //ao usar o metodo getSerializable tem que converter para o tipo declarado
            experimento = (Experimento) bundle.getSerializable("EXPERIMENTO");

            proprietario = bundle.getString("proprietario");

            totalAmostrasBloco.setText(experimento.getAmostrasAdicionadas()+"");
            totalBlocos.setText(experimento.getBlocosAdicionados()+"");

        } else{

            experimento = (Experimento) bundle.getSerializable("experimentoDados");
            proprietario = bundle.getString("proprietario");

            totalAmostrasBloco.setText(experimento.getAmostrasAdicionadas() + "");
            totalBlocos.setText(experimento.getBlocosAdicionados()+"");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adicionar_amostra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.idSalvarAmostra:

                Amostra amostra = new Amostra();

                String alturavalida = altura.getText().toString();
                String colmo = milimetroColmo.getText().toString();
                String insercao = alturaInsercao.getText().toString();

                boolean resultadoValidacao = camposPreenchidos(alturavalida, colmo, insercao);

                if (resultadoValidacao == false){
                    Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show();
                    return true;
                } else {

                    try {
                    Double alturaAmostra = Double.parseDouble(altura.getText().toString());
                    Integer milimetroColmoAmostra =  Integer.parseInt(milimetroColmo.getText().toString());
                    Double alturaInsercaoAmostra =  Double.parseDouble(alturaInsercao.getText().toString());

                    //Verifica se há campos vazios


                    if (camposValidos(alturaAmostra, milimetroColmoAmostra, alturaInsercaoAmostra)){
                        amostra.setAltura(alturaAmostra);
                        amostra.setMilimetroColmo(milimetroColmoAmostra);
                        amostra.setAlturaInsercao(alturaInsercaoAmostra);
                        amostra.setExperimento(experimento);
                        amostra.setProprietario(experimento.getProprietario());

                        dadosOpenHelper = new BdHelperExperimento(this);
                        conexao = dadosOpenHelper.getWritableDatabase();
                        clienteRepositorio = new ExperimentoRepositorio(conexao);

                        Experimento retornado = clienteRepositorio.alterarParaInserirAmostra(experimento, amostra);

                        if (retornado != null) {
                            experimento = retornado;

                            totalAmostrasBloco.setText(experimento.getAmostrasAdicionadas() + "");
                            totalBlocos.setText(experimento.getBlocosAdicionados() + "");

                            altura.setText("");
                            milimetroColmo.setText("");
                            alturaInsercao.setText("");

                            altura.requestFocus();

                            Toast.makeText(this, "Amostra salva!", Toast.LENGTH_SHORT).show();

                            return true;
                        } else {
                            Toast.makeText(this, "Todas as amostras adicionadas\n" + "Crie um novo experimento!", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    } else {
                        Toast.makeText(this, "Todas as amostras adicionadas\n" + "Crie um novo experimento!", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                catch(Exception e){
                    Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean camposPreenchidos(String alturaAmostra, String milimetroColmoAmostra, String alturaInsercaoAmostra){
        if (alturaAmostra == null || milimetroColmoAmostra == null || alturaInsercaoAmostra == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean camposValidos(Double alturaAmostra, Integer milimetroColmoAmostra, Double alturaInsercaoAmostra){
        if (alturaAmostra > 0.0 || milimetroColmoAmostra > 0.0 || alturaInsercaoAmostra > 0.0){
            return true;
        } else {
            return false;
        }
    }
}
