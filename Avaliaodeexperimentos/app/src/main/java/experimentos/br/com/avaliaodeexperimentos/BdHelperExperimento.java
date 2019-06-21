package experimentos.br.com.avaliaodeexperimentos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Classe para criar e manter o BD
public class BdHelperExperimento extends SQLiteOpenHelper {

    private static final String DB_NAME = "bdemmm"; // o nome do bd
    private static final int DB_VERSION = 1;
    //Adicionar extensão e tabelas aqui

    BdHelperExperimento(Context context) {
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

            db.execSQL("CREATE TABLE EXPERIMENTO (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NOME TEXT, "
                    + "DATA_EXPERIMENTO TEXT, "
                    + "TOTAL_BLOCOS INTEGER, "
                    + "AMOSTRAS_POR_BLOCOS INTEGER, "
                    + "BLOCOS_ADICIONADOS INTEGER, "
                    + "AMOSTRAS_ADICIONADAS INTEGER, "
                    + "PROPRIETARIO TEXT); ");


            db.execSQL("CREATE TABLE AMOSTRA (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "ALTURA REAL, "
                    + "MILIMETRO_COLMO TEXT, "
                    + "ALTURA_INSERCAO REAL, "
                    + "NUMERO_BLOCO INTEGER, "
                    + "PROPRIETARIO TEXT, "
                    + "ID_EXPERIMENTO INTEGER); ");
        }
    }

}
