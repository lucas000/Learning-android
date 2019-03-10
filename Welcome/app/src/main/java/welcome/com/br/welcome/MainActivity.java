package welcome.com.br.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = (EditText) findViewById(R.id.edtNome);
        senha = (EditText) findViewById(R.id.edtSenha);

    }

    public void welcomeUser(View v){
        boolean resultado = verificaLogin(usuario.getText().toString(), senha.getText().toString());

        if (resultado == true){

            Intent intent = new Intent(this, Welcome.class);

            String nomeExtra = usuario.getText().toString();
            String senhaExtra = senha.getText().toString();

            intent.putExtra("USUARIO", nomeExtra);
            intent.putExtra("SENHA", senhaExtra);

            startActivity(intent);
        } else{
            usuario.setText("");
            senha.setText("");
            Toast.makeText(this, "Usuário ou senha incorretos, tente novamente!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean verificaLogin(String nome, String senha){
        boolean saida = false;

        if (nome.equals("Lucas") && senha.equals("123")){
            saida = true;
        }

        return saida;
    }
}
