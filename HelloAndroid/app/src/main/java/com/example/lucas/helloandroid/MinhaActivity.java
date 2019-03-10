package com.example.lucas.helloandroid;

import android.app.Activity;
import android.os.Bundle;

public class MinhaActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        //A activity está sendo criada
    }

    @Override
    protected void onStart(){
        super.onStart();
        //A activity está prestes a se tornar visível
    }

    @Override
    protected void onResume(){
        super.onResume();
        //A activity está visivel
    }

    @Override
    protected void onPause(){
        super.onPause();
        //A activity está recebendo o foco. Esta activity ficara pausada.
    }

    @Override
    protected void onStop(){
        super.onStop();
        //A activity não está mais visivel mas permanece em memoria
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //A activity esta prestes a ser destruída(removida da memoria).
    }
}
