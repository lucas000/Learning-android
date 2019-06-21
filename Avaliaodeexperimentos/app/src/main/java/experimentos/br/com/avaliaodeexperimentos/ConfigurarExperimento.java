package experimentos.br.com.avaliaodeexperimentos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

//Classe para configurar entradas de um experimento
public class ConfigurarExperimento extends AppCompatActivity {

    private ExperimentoRepositorio clienteRepositorio;

    private SQLiteDatabase conexao;
    private BdHelperExperimento dadosOpenHelper;

    private String proprietario;

    private EditText totalBlocos, amostrasPorBloco, nomeExperimento, dataExperimento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_experimento);

        totalBlocos = (EditText) findViewById(R.id.edtTotalBlocos);
        amostrasPorBloco = (EditText) findViewById(R.id.edtAmostrasPorBlocos);
        nomeExperimento = (EditText) findViewById(R.id.edtNomeExperimento);
        dataExperimento = (EditText) findViewById(R.id.edtAlterarData);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataExperimento.setText(sdf.format(new Date()));

        Bundle bundle = getIntent().getExtras();

        proprietario = bundle.getString("proprietario");

    }

    public void iniciarExperimento(View v){

        String nome = nomeExperimento.getText().toString();
        String data = dataExperimento.getText().toString();
        if (nome.isEmpty() || data.isEmpty()){
            Toast toast = Toast.makeText(ConfigurarExperimento.this,
                    "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT);
            toast.show();
        } else{

            try{
                int totalBlocosExperimento = Integer.parseInt(totalBlocos.getText().toString());
                int amostrarPorBlocosExperimento = Integer.parseInt(amostrasPorBloco.getText().toString());
                Intent intent = new Intent(this, AdicionarAmostra.class);

                //Bundle para ser usado na outro tela
                Bundle bundleExperimento = new Bundle();
                bundleExperimento.putInt("totalBlocos", totalBlocosExperimento);
                bundleExperimento.putInt("amostrasPorBlocos", amostrarPorBlocosExperimento);
                intent.putExtras(bundleExperimento);

                Experimento experimento = new Experimento();

                experimento.setNome(nome);
                experimento.setData(data);
                experimento.setTotalAmostras(amostrarPorBlocosExperimento);
                experimento.setTotalBlocos(totalBlocosExperimento);
                experimento.setAmostrasAdicionadas(0);
                experimento.setBlocosAdicionados(1);

                experimento.setProprietario(proprietario);

                //Campo de texto

                //Adicionando o objeto Experimento ao bundle
                dadosOpenHelper = new BdHelperExperimento(this);
                conexao = dadosOpenHelper.getWritableDatabase();
                clienteRepositorio = new ExperimentoRepositorio(conexao);

                SQLiteOpenHelper experimentoHelper = new BdHelperExperimento(
                        ConfigurarExperimento.this);

                ContentValues experimentoValores = new ContentValues();
                experimentoValores.put("NOME", experimento.getNome());
                experimentoValores.put("DATA_EXPERIMENTO", experimento.getData());
                experimentoValores.put("TOTAL_BLOCOS", experimento.getTotalBlocos());
                experimentoValores.put("AMOSTRAS_POR_BLOCOS", experimento.getTotalAmostras());
                experimentoValores.put("BLOCOS_ADICIONADOS", experimento.getBlocosAdicionados());
                experimentoValores.put("AMOSTRAS_ADICIONADAS", experimento.getAmostrasAdicionadas());
                experimentoValores.put("PROPRIETARIO", experimento.getProprietario());

                try {
                    SQLiteDatabase db = experimentoHelper.getWritableDatabase();

                    db.insert("EXPERIMENTO", null, experimentoValores);
                    System.out.println("Experimento inserido: " + experimento.toString());
                    db.close();

                    dadosOpenHelper = new BdHelperExperimento(this);
                    conexao = dadosOpenHelper.getWritableDatabase();
                    clienteRepositorio = new ExperimentoRepositorio(conexao);

                    Experimento e = clienteRepositorio.buscaUltimo(proprietario);

                    if (e != null){
                        intent.putExtra("experimentoDados", e);
                        startActivity(intent);
                    } else{
                        Toast.makeText(null, "Erro ao salvar experimento!", Toast.LENGTH_LONG).show();
                    }
                } catch(SQLiteException e) {
                    Toast toast = Toast.makeText(ConfigurarExperimento.this,
                            "Banco de dados indisponível", Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (Exception e){
                System.out.println("Excecao: " + e);
                Toast toast = Toast.makeText(ConfigurarExperimento.this,
                        "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
