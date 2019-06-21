package experimentos.br.com.avaliaodeexperimentos;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ExperimentoAdapter extends RecyclerView.Adapter<ExperimentoAdapter.ViewHolderCliente>  {

    private List<Experimento> dados;

    private ExperimentoRepositorio clienteRepositorio;

    private SQLiteDatabase conexao;
    private BdHelperExperimento dadosOpenHelper;
    private Constantes.Type type;

    public ExperimentoAdapter(List<Experimento> dados){
        this.dados = dados;
    }

    @Override
    public ExperimentoAdapter.ViewHolderCliente onCreateViewHolder(ViewGroup parent, int viewType) {

        //LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_experimentos, parent, false);
        ViewHolderCliente holderCliente = new ViewHolderCliente(view, parent.getContext());

        return holderCliente;
    }

    @Override
    public void onBindViewHolder(ExperimentoAdapter.ViewHolderCliente holder, int position) {
        if (dados != null && (dados.size() > 0)) {
            Experimento experimento = dados.get(position);

            holder.txtNome.setText(experimento.getNome());
            holder.txtData.setText(experimento.getData());
            holder.txtTotalBlocos.setText(experimento.getTotalBlocos()+"");
            holder.txtTotalAmostras.setText(experimento.getTotalAmostras()+"");
            holder.txtBlocosAdicionados.setText(experimento.getBlocosAdicionados()+"");
            holder.txtAmostrasAdicionadas.setText(experimento.getAmostrasAdicionadas()+"");
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderCliente extends RecyclerView.ViewHolder{

        public TextView txtNome;
        public TextView txtData;
        public TextView txtTotalBlocos;
        public TextView txtTotalAmostras;
        public TextView txtBlocosAdicionados;
        public TextView txtAmostrasAdicionadas;


        public ViewHolderCliente(View itemView, final Context context) {
            super(itemView);

            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtData = (TextView) itemView.findViewById(R.id.txtData);
            txtTotalBlocos = (TextView) itemView.findViewById(R.id.txtTotalBlocos);
            //txtTotalBlocos.setGravity(Gravity.LEFT);
            txtTotalAmostras = (TextView) itemView.findViewById(R.id.txtTotalAmostras);
            txtAmostrasAdicionadas = (TextView) itemView.findViewById(R.id.txtAmostrasAdicionadas);
            txtBlocosAdicionados = (TextView) itemView.findViewById(R.id.txtBlocosAdicionados);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final CharSequence[] items = {"Adicionar amostra", "Editar experimento", "Exportar", "Apagar experimento"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Selecione uma opção");
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        @TargetApi(Build.VERSION_CODES.O)
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item){
                                case 0:
                                    if (dados.size() > 0) {

                                        //O metodo getLayoutPosition(), disponibilizado pelo recyclerView,
                                        // mostra a posicao do item clicado do layout
                                        Experimento experimento = dados.get(getLayoutPosition());

                                        Intent it = new Intent(context, AdicionarAmostra.class);

                                        it.putExtra("EXPERIMENTO", experimento);

                                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                                    }
                                    break;
                                case 1:
                                    Experimento experimentoEditar = dados.get(getLayoutPosition());

                                    Intent it = new Intent(context, AlterarDadosExperimento.class);

                                    it.putExtra("EXPERIMENTO", experimentoEditar);

                                    ((AppCompatActivity) context).startActivityForResult(it, 0);
                                    break;
                                case 2:

                                    Experimento experimentoExportar = dados.get(getLayoutPosition());

                                    try {

                                        dadosOpenHelper = new BdHelperExperimento(context);
                                        conexao = dadosOpenHelper.getWritableDatabase();
                                        clienteRepositorio = new ExperimentoRepositorio(conexao);

                                        List<Amostra> amostras = clienteRepositorio.amostrasParaArquivo(experimentoExportar);

                                        //Metodo para escrever dados no arquivo
                                        try{
                                            escrever(experimentoExportar, amostras);
                                            Toast.makeText(context,"Relatório salvo.\nAcesse seus arquivos para localizar!", Toast.LENGTH_LONG).show();

                                        } catch (Exception e){
                                            Toast.makeText(context,"Erro ao salvar relatório!", Toast.LENGTH_LONG).show();

                                        }


                                        /*
                                        Intent intent = new Intent(context, EnviarEmail.class);

                                        intent.putExtra("CAMINHO", caminho);

                                        ((AppCompatActivity) context).startActivityForResult(intent, 0);
                                        */
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    break;

                                    //Acaba caso 2: ou, Exportar
                                case 3:
                                    dadosOpenHelper = new BdHelperExperimento(context);
                                    conexao = dadosOpenHelper.getWritableDatabase();
                                    clienteRepositorio = new ExperimentoRepositorio(conexao);

                                    Experimento experimento = dados.get(getLayoutPosition());
                                    boolean apagou = clienteRepositorio.apagarExperimento(experimento);

                                    if (apagou){
                                        int position = dados.indexOf(experimento);
                                        dados.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, dados.size());
                                        Toast.makeText(context,"Dados do experimento apagado com sucesso!", Toast.LENGTH_LONG).show();
                                    } else{
                                        Toast.makeText(context,"Erro ao apagar dados do experimento!", Toast.LENGTH_LONG).show();
                                    }
                                    break;
                            }
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }
        public void escrever(Experimento experimento, List<Amostra> amostras){
            File caminho = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/AvaliacaoExperimentos");
            if (!caminho.exists())
                caminho.mkdir();

            Calendar cal = Calendar.getInstance();

            File file = new File(caminho.getPath() + "/experimento" + experimento.getNome() + "_" + experimento.getProprietario()+ "_" + cal.get(Calendar.HOUR) + "-" + cal.get(Calendar.MINUTE) + ".csv");

            try {
                FileOutputStream out = new FileOutputStream(file);
                String aux1 = "nome_experimento;" + "data;" + "altura;" + "milimetro_colmo;" + "altura_insercao;" + "numero_bloco" + "\n";
                out.write(aux1.getBytes(), 0, aux1.getBytes().length);

                for (Amostra amostra : amostras){
                    String aux = amostra.getExperimento().getNome() + ";" + amostra.getExperimento().getData()+ ";" +
                            amostra.getAltura() + ";" + amostra.getMilimetroColmo() + ";" + amostra.getAlturaInsercao() + ";" + amostra.getNumeroBloco() + "\n";
                    out.write(aux.getBytes(), 0, aux.getBytes().length);
                }
                //esse método write deve ficar dentro da estrutura de repetição

                //já essa parte de flush e close tem que ficar fora e
                //deve ser executada apenas quando já tiver terminado de gerar to-do o arquivo
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public String escrever(String fileName, List<Amostra> amostras){
            File caminho = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Relatorios");
            if (!caminho.exists())
                caminho.mkdir();

            File file = new File(caminho.getPath()+"/" + fileName + ".csv" );

            try {
                FileOutputStream out = new FileOutputStream(file);
                //aqui você pode adicionar o que desejar salvar usando estruturas de código e tudo mais

                String aux1 = "nome_experimento;" + "data;" + "altura;" + "milimetro_colmo;" + "altura_insercao;" + "numero_bloco" + "\n";
                out.write(aux1.getBytes(), 0, aux1.getBytes().length);

                System.out.println("Tamanho da lista:" + amostras.size());
                for (Amostra amostra : amostras){
                    String aux = amostra.getExperimento().getNome() + ";" + amostra.getExperimento().getData()+ ";" +
                            amostra.getAltura() + ";" + amostra.getMilimetroColmo() + ";" + amostra.getAlturaInsercao() + ";" + amostra.getNumeroBloco() + "\n";
                    out.write(aux.getBytes(), 0, aux.getBytes().length);
                }



                //esse método write deve ficar dentro da estrutura de repetição

                //já essa parte de flush e close tem que ficar fora e
                //deve ser executada apenas quando já tiver terminado de gerar todo o arquivo
                out.flush();
                out.close();

                return file.getAbsolutePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return file.getAbsolutePath();
        }

    }
}

