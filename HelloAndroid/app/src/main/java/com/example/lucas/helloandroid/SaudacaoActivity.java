package com.example.lucas.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class SaudacaoActivity extends Activity {
    public static final String EXTRA_NOME_USUARIO =
            "helloandroid.EXTRA_NOME_USUARIO";

    public static final String ACAO_EXIBIR_SAUDACAO =
            "helloandroid.ACAO_EXIBIR_SAUDACAO";

    public static final String CATEGORIA_SAUDACAO =
            "helloandroid.CATEGORIA_SAUDACAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saudacao);

        TextView saudacaoTextView = (TextView) findViewById(R.id.saudacaoTextView);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOME_USUARIO)){
            String saudacao = getResources().getString(R.string.saudacao);
            saudacaoTextView.setText(saudacao + " " + intent.getStringExtra(EXTRA_NOME_USUARIO));
        } else {
            saudacaoTextView.setText("O nome do usuário não foi informadao");
        }
    }
}