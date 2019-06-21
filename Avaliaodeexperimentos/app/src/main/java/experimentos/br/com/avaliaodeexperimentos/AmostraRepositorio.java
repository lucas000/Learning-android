package experimentos.br.com.avaliaodeexperimentos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class AmostraRepositorio {
    private SQLiteDatabase conexao;

    public AmostraRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserirAmostra(Amostra amostra, Experimento experimento){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ALTURA", amostra.getAltura());
        contentValues.put("MILIMETRO_COLMO", amostra.getMilimetroColmo());
        contentValues.put("ALTURA_INSERCAO", amostra.getAlturaInsercao());
        contentValues.put("NUMERO_BLOCO", experimento.getBlocosAdicionados());
        contentValues.put("ID_EXPERIMENTO", experimento.getId());

        conexao.insertOrThrow("AMOSTRA", null, contentValues);
    }
}
