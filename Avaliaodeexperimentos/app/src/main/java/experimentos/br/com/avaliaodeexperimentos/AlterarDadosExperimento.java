package experimentos.br.com.avaliaodeexperimentos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlterarDadosExperimento extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private BdHelperExperimento dadosOpenHelper;
    private Experimento experimento;

    private ExperimentoRepositorio clienteRepositorio;

    private EditText edtNome, edtData, edtBlocos, edtAmostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados_experimento);

        edtNome = (EditText) findViewById(R.id.edtAlterarNome);
        edtData = (EditText) findViewById(R.id.edtAlterarData);
        edtBlocos = (EditText) findViewById(R.id.edtAlterarBlocs);
        edtAmostrar = (EditText) findViewById(R.id.edtAlterarAmostra);

        verificaParametro();
    }

    private void verificaParametro(){
        Bundle bundle = getIntent().getExtras();

        experimento = new Experimento();

        if ((bundle != null) && (bundle.containsKey("EXPERIMENTO"))){
            experimento = (Experimento) bundle.getSerializable("EXPERIMENTO");

            edtNome.setText(experimento.getNome());
            edtData.setText(experimento.getData());
            edtBlocos.setText(experimento.getTotalBlocos()+"");
            edtAmostrar.setText(experimento.getTotalAmostras()+"");
        }
    }

    public void alterar(View v){

        try {
            dadosOpenHelper = new BdHelperExperimento(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            clienteRepositorio = new ExperimentoRepositorio(conexao);

            String nome = edtNome.getText().toString();
            String data = edtData.getText().toString();

            int totalBlocosExperimento = Integer.parseInt(edtBlocos.getText().toString());
            int amostrarPorBlocosExperimento = Integer.parseInt(edtAmostrar.getText().toString());

            experimento.setNome(nome);
            experimento.setData(data);
            experimento.setTotalAmostras(amostrarPorBlocosExperimento);
            experimento.setTotalBlocos(totalBlocosExperimento);

            boolean retornou = clienteRepositorio.alteraExperimento(experimento);

            if (retornou == true){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(null, "Entrada inválidas!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            System.out.println("Deu ruim");
        }

    }
}
