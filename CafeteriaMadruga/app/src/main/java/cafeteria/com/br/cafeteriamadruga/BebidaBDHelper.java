package cafeteria.com.br.cafeteriamadruga;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cafeteria.com.br.cafeteriamadruga.R;

public class BebidaBDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bdCafeParaEnviar"; // o nome do bd
    private static final int DB_VERSION = 1; // a versão do bd

    // Construtor
    BebidaBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        atualizarBD(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        atualizarBD(db, oldVersion, newVersion);
    }

    private void atualizarBD(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {

            db.execSQL("CREATE TABLE CATEGORIA (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOME TEXT);");

            inserirCategoria(db, "Bebidas");
            inserirCategoria(db, "Comidas");
            inserirCategoria(db, "Mercadorias");

            db.execSQL("CREATE TABLE BEBIDA (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOME TEXT, "
                    + "DESCRICAO TEXT, "
                    + "IMAGEM_RECURSO_ID INTEGER, "
                    + "FAVORITO NUMERIC);");
            inserirBebida(db, "Latte",
                    "Latte é uma bebida de café ...", R.drawable.latte, 0);
            inserirBebida(db, "Cappuccino",
                    "Um cappuccino clássico e ...", R.drawable.cappuccino, 0);
            inserirBebida(db, "Filter",
                    "Café coado do grau torrado ...", R.drawable.filter, 0);
        }


    }


    private static void inserirBebida(SQLiteDatabase db, String nome,
                                      String descricao, int recursoId, int favorito) {
        ContentValues bebidaValores = new ContentValues();
        bebidaValores.put("NOME", nome);
        bebidaValores.put("DESCRICAO", descricao);
        bebidaValores.put("IMAGEM_RECURSO_ID", recursoId);
        bebidaValores.put("FAVORITO", favorito);
        db.insert("BEBIDA", null, bebidaValores);
    }

    private static void inserirCategoria(SQLiteDatabase db, String nome) {
        ContentValues categoriaValores = new ContentValues();
        categoriaValores.put("NOME", nome);
        db.insert("CATEGORIA", null, categoriaValores);
    }
}
