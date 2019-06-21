package experimentos.br.com.avaliaodeexperimentos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestaurarSenha extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurar_senha);

        mAuth = FirebaseAuth.getInstance();
        edtEmail = (EditText) findViewById(R.id.edtEmailRecuperar);
    }

    public void recuperarSenha(View v){

        final String email = edtEmail.getText().toString();

        if (email.isEmpty()){
            Toast.makeText(RestaurarSenha.this,"Digite o email da conta para recuperar a senha.", Toast.LENGTH_LONG).show();
        } else{
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(null, "Email sent.");
                                Intent it = new Intent(RestaurarSenha.this, LoginActivity.class);
                                it.putExtra("MSG", "Link de recuperação enviado para: " + email);

                                startActivity(it);
                            }
                        }
                    });
        }
    }
}
