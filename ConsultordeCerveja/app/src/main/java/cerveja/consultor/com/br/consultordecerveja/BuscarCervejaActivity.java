package cerveja.consultor.com.br.consultordecerveja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BuscarCervejaActivity extends AppCompatActivity {
    private Spinner spnCores;
    private Button btnBuscarCervejas;
    private TextView txtSelecioneCor;

    ArrayAdapter<String> adapter;
    ArrayList<String> listItems = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cerveja);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //referenciando elementos do layout
        txtSelecioneCor = (TextView) findViewById(R.id.txtSelecioneCor);
        spnCores = (Spinner) findViewById(R.id.spnCores);
        btnBuscarCervejas = (Button) findViewById(R.id.btnBuscarCervejas);
        listView = (ListView) findViewById(R.id.listv);

        //Populando o spinner
        String[] lsCores = getResources().getStringArray(R.array.listaCervejas);

        //Definindo o layout do adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsCores);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spnCores.setAdapter(spinnerArrayAdapter);
        //Fim do spiner

    }


    public void encontrarCerveja(View v){
        String cor = spnCores.getSelectedItem().toString();//Pega o cor selecionada no spinner
        ExpertCerveja d = new ExpertCerveja();//Instacia a classe que busca as cervejas
        List<String> marcas = d.getMarcas(cor);//chama o metodo que retorna as marcas pela cor selecionada

        //Criando a lista que recebera as marcas
        listItems = new ArrayList<String>();

        //Laço para preencher o vetor para colocar na lista
        for(int i = 0; i < marcas.size(); i++) {
            listItems.add(marcas.get(i));
        }

        //setando adapter para receber a lista de valores
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listItems);

        //definindo o adapter usado na listView
        listView.setAdapter(adapter);

        //ALtera o estado da lista mostrando os novos elementos ao usuario
        adapter.notifyDataSetChanged();
    }
}
