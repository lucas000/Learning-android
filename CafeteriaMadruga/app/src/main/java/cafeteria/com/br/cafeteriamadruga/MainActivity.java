package cafeteria.com.br.cafeteriamadruga;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor, categoriasCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                        // recupera a opção do item clicado
                        switch (position) {
                            case 0: // Item na posição 0 é bebida
                                Intent intent = new Intent(MainActivity.this,CategoriaBebidasActivity.class);
                                startActivity(intent);
                                break;
                            case 1: // Item na posição 1 é comida
                                Toast.makeText(MainActivity.this,"Ainda não servimos comida!",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2: // Item na posição 2 é mercearia
                                Toast.makeText(MainActivity.this,"Ainda não temos produtos na mercearia!",
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
        //Adiciona o listener para o listView
        ListView listView = (ListView) findViewById(R.id.list_options);

        try { // vou buscar no BD quais bebidas sao favoritas
            SQLiteOpenHelper categoriaBDHelper = new BebidaBDHelper(this);
            db = categoriaBDHelper.getReadableDatabase();
            categoriasCursor = db.query("CATEGORIA",
                    new String[] { "_id", "NOME"}, null,
                    null, null, null, null);

            CursorAdapter categoriasAdapter = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    categoriasCursor,
                    new String[]{"NOME"},
                    new int[]{android.R.id.text1}, 0);
            listView.setAdapter(categoriasAdapter);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }

        listView.setOnItemClickListener(itemClickListener);

        //Bebidas
        ListView listaFavoritos = (ListView)findViewById(R.id.lista_favoritos);
        try { // vou buscar no BD quais bebidas sao favoritas
            SQLiteOpenHelper bebidaBDHelper = new BebidaBDHelper(this);
            db = bebidaBDHelper.getReadableDatabase();
            favoritesCursor = db.query("BEBIDA",
                    new String[] { "_id", "NOME"}, "FAVORITO = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NOME"},
                    new int[]{android.R.id.text1}, 0);
            listaFavoritos.setAdapter(favoriteAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }
//Navega para BebidaActivity se uma bebida FAVORITA é clicada
// Vou diretamente para a bebida, sem passar pela tela intermediária
        listaFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BebidaActivity.class);
                Bundle params = new Bundle();
                params.putInt("id", (int)id);
                intent.putExtras(params);
                startActivity(intent);
            }
        });

    }

    public void onRestart() {
        super.onRestart();
        try {
            BebidaBDHelper bebidaBDHelper = new BebidaBDHelper(this);
            db = bebidaBDHelper.getReadableDatabase();
            Cursor newCursor = db.query("BEBIDA",
                    new String[] { "_id", "NOME"},
                    "FAVORITO = 1",
                    null, null, null, null);
            ListView listaFavoritos = (ListView)findViewById(R.id.lista_favoritos);
            CursorAdapter adapter = (CursorAdapter) listaFavoritos.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Banco de dados indisponível", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }

}
