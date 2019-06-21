package experimentos.br.com.avaliaodeexperimentos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExperimentoRepositorio {

    private SQLiteDatabase conexao;

    public ExperimentoRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public boolean alteraExperimento(Experimento experimento){
        try{
            ContentValues experimentoValores = new ContentValues();
            experimentoValores.put("NOME", experimento.getNome());
            experimentoValores.put("DATA_EXPERIMENTO", experimento.getData());
            experimentoValores.put("TOTAL_BLOCOS", experimento.getTotalBlocos());
            experimentoValores.put("AMOSTRAS_POR_BLOCOS", experimento.getTotalAmostras());
            experimentoValores.put("BLOCOS_ADICIONADOS", experimento.getBlocosAdicionados());
            experimentoValores.put("AMOSTRAS_ADICIONADAS", experimento.getAmostrasAdicionadas());

            conexao.update("EXPERIMENTO", experimentoValores,
                    "_id = ? AND PROPRIETARIO = ?", new String[] {Integer.toString(experimento.getId()), experimento.getProprietario()});

            return true;
        } catch (Exception e){
            return false;
        }
    }

    public List<Amostra> amostrasParaArquivo(Experimento experimento){
        List<Amostra> amostras = new ArrayList<Amostra>();
        Amostra amostra = null;

        Cursor cursor = conexao.query("AMOSTRA",
                new String[] {"_id", "ALTURA", "MILIMETRO_COLMO", "ALTURA_INSERCAO", "NUMERO_BLOCO", "PROPRIETARIO"}, "ID_EXPERIMENTO = ? AND PROPRIETARIO = ?",
                new String[] {String.valueOf(experimento.getId()), experimento.getProprietario()},
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Amostra amostra1 = new Amostra();

                amostra1.setId(cursor.getInt(0));//to get id, 0 is the column index
                amostra1.setAltura(cursor.getDouble(1));
                amostra1.setMilimetroColmo(cursor.getInt(2));
                amostra1.setAlturaInsercao(cursor.getDouble(3));
                amostra1.setNumeroBloco(cursor.getInt(4));
                amostra1.setExperimento(experimento);
                amostra1.setProprietario(cursor.getString(5));

                amostras.add(amostra1);

            } while (cursor.moveToNext());
        }
        return amostras;
    }
    public Experimento buscaUltimo(String proprietario){
        Experimento experimento = null;

        Cursor cursor = conexao.query("EXPERIMENTO",
                new String[] {"_id", "NOME", "DATA_EXPERIMENTO", "TOTAL_BLOCOS", "AMOSTRAS_POR_BLOCOS", "BLOCOS_ADICIONADOS", "AMOSTRAS_ADICIONADAS", "PROPRIETARIO"}, "PROPRIETARIO = ?",
                new String[] {proprietario},
                null, null, null);

        if(cursor.moveToLast()){
            experimento = new Experimento();

            //name = cursor.getString(column_index);//to get other values
            experimento.setId(cursor.getInt(0));//to get id, 0 is the column index
            experimento.setNome(cursor.getString(1));
            experimento.setData(cursor.getString(2));
            experimento.setTotalBlocos(cursor.getInt(3));
            experimento.setTotalAmostras(cursor.getInt(4));
            experimento.setBlocosAdicionados(cursor.getInt(5));
            experimento.setAmostrasAdicionadas(cursor.getInt(6));
            experimento.setProprietario(cursor.getString(7));
        }

        return experimento;

    }


    public boolean apagarExperimento(Experimento experimento){
        try{
            conexao.delete("EXPERIMENTO", "_id = ? AND PROPRIETARIO = ?",new String[] {String.valueOf(experimento.getId()), experimento.getProprietario()} );

            conexao.delete("AMOSTRA", "ID_EXPERIMENTO = ? AND PROPRIETARIO = ?", new String[] {String.valueOf(experimento.getId()), experimento.getProprietario()});
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Experimento alterarParaInserirAmostra(Experimento experimento, Amostra amostra){
        Experimento retorno = new Experimento();

            if (experimento.getBlocosAdicionados() <= experimento.getTotalBlocos()){
                if (experimento.getAmostrasAdicionadas() < experimento.getTotalAmostras()) {

                    ContentValues amostraValues = new ContentValues();
                    amostraValues.put("ALTURA", amostra.getAltura());
                    amostraValues.put("MILIMETRO_COLMO", amostra.getMilimetroColmo());
                    amostraValues.put("ALTURA_INSERCAO", amostra.getAlturaInsercao());
                    amostraValues.put("NUMERO_BLOCO", amostra.getExperimento().getBlocosAdicionados());
                    amostraValues.put("ID_EXPERIMENTO", amostra.getExperimento().getId());
                    amostraValues.put("PROPRIETARIO", amostra.getExperimento().getProprietario());

                    conexao.insert("AMOSTRA", null, amostraValues);

                    int amostraAdd = experimento.getAmostrasAdicionadas();

                    ContentValues alteraExperimento = new ContentValues();
                    alteraExperimento.put("AMOSTRAS_ADICIONADAS", (amostraAdd + 1));
                    conexao.update("EXPERIMENTO", alteraExperimento,
                            "_id = ? AND PROPRIETARIO = ?", new String[] {Integer.toString(experimento.getId()), experimento.getProprietario()});

                    experimento.setAmostrasAdicionadas((amostraAdd + 1));
                    retorno = experimento;
                } else {
                    if (experimento.getBlocosAdicionados() < experimento.getTotalBlocos()){
                        ContentValues amostraValues = new ContentValues();
                        amostraValues.put("ALTURA", amostra.getAltura());
                        amostraValues.put("MILIMETRO_COLMO", amostra.getMilimetroColmo());
                        amostraValues.put("ALTURA_INSERCAO", amostra.getAlturaInsercao());
                        amostraValues.put("PROPRIETARIO", amostra.getExperimento().getProprietario());

                        int blocosAdicionados = amostra.getExperimento().getBlocosAdicionados();
                        amostraValues.put("NUMERO_BLOCO", (blocosAdicionados + 1));

                        amostraValues.put("ID_EXPERIMENTO", amostra.getExperimento().getId());

                        conexao.insert("AMOSTRA", null, amostraValues);

                        ContentValues alteraExperimento = new ContentValues();
                        alteraExperimento.put("AMOSTRAS_ADICIONADAS", 1);
                        alteraExperimento.put("BLOCOS_ADICIONADOS", (blocosAdicionados + 1));

                        conexao.update("EXPERIMENTO", alteraExperimento,
                                "_id = ? AND PROPRIETARIO = ?", new String[] {Integer.toString(experimento.getId()), experimento.getProprietario()});
                        experimento.setAmostrasAdicionadas(1);
                        experimento.setBlocosAdicionados((blocosAdicionados + 1));
                        retorno = experimento;
                    } else{
                        return null;
                    }
                }
            } else {
                return null;
            }
        return retorno;
    }

    public List<Experimento> buscarTodosPorProprietario(String proprietario){
        List<Experimento> experimentos = new ArrayList<Experimento>();

        Cursor resultado = conexao.query("EXPERIMENTO",
                new String[] {"_id", "NOME", "DATA_EXPERIMENTO", "TOTAL_BLOCOS", "AMOSTRAS_POR_BLOCOS", "BLOCOS_ADICIONADOS", "AMOSTRAS_ADICIONADAS"}, "PROPRIETARIO = ?",
                new String[] {proprietario},
                null, null, null);

        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            do {
                Experimento experimento = new Experimento();

                experimento.setId(resultado.getInt(resultado.getColumnIndexOrThrow("_id")));
                experimento.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                experimento.setData(resultado.getString(resultado.getColumnIndexOrThrow("DATA_EXPERIMENTO")));
                experimento.setTotalBlocos(resultado.getInt(resultado.getColumnIndexOrThrow("TOTAL_BLOCOS")));
                experimento.setTotalAmostras(resultado.getInt(resultado.getColumnIndexOrThrow("AMOSTRAS_POR_BLOCOS")));
                experimento.setBlocosAdicionados(resultado.getInt(resultado.getColumnIndexOrThrow("BLOCOS_ADICIONADOS")));
                experimento.setAmostrasAdicionadas(resultado.getInt(resultado.getColumnIndexOrThrow("AMOSTRAS_ADICIONADAS")));
                experimento.setProprietario(proprietario);

                experimentos.add(experimento);

            } while (resultado.moveToNext());
        }

        return experimentos;
    }
}
