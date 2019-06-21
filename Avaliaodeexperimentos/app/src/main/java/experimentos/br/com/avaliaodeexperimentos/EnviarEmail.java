package experimentos.br.com.avaliaodeexperimentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class EnviarEmail extends AppCompatActivity {

    private EditText destinatario;
    private String caminho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_email);

        destinatario = (EditText) findViewById(R.id.edtDestinario);

        verificaParametro();
    }

    private void verificaParametro(){
        //A classe Bundle que contem os parametros passado na janela
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("CAMINHO"))){
            //ao usar o metodo getSerializable tem que converter para o tipo declarado
            caminho =  bundle.getString("CAMINHO");
            System.out.println("Caminho para ser arquivo: " + caminho);
        }
    }

    public void enviaEmail(View v){
        String email =  destinatario.getText().toString();

        File file = new File(caminho);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Test single attachment");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lucasdejesus000@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, "Mail with an attachment");

        intent.setType("text/plain");

        startActivity(Intent.createChooser(intent, "Send mail"));
        /*
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        String message="Olá o relatório do seu experimento.";
        intent.putExtra(Intent.EXTRA_SUBJECT, "Arquivo experimento");
        intent.setData(Uri.parse("file://" + caminho);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setData(Uri.parse("mailfrom:" + "lucasdejesus000@gmail.com"));
        intent.setData(Uri.parse("mailto:" + email));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
        //startActivity(emailIntent);
        }
}
