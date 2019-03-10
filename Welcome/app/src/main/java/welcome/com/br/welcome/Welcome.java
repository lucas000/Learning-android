package welcome.com.br.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Welcome extends Activity {

    private TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome = (TextView) findViewById(R.id.textView5);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("USUARIO");

        welcome.setText(nome);
    }
}
