package experimentos.br.com.avaliaodeexperimentos;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


//Nesse classe será mostrada a lista de experimentos
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteDatabase conexao;
    private BdHelperExperimento dadosOpenHelper;

    private String emailLogado;

    private RecyclerView lstDados;
    private ConstraintLayout layoutContentMain;

    private ExperimentoAdapter clienteAdapter;
    private ExperimentoRepositorio clienteRepositorio;

    private FirebaseAuth mAuth;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        criarConexao();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) { // se o usuário estiver logado
            emailLogado = currentUser.getEmail();
            System.out.println("Email logado: " + emailLogado);

            lstDados = (RecyclerView) findViewById(R.id.lstDados);
            layoutContentMain = (ConstraintLayout) findViewById(R.id.layoutContentMain);

            lstDados.setHasFixedSize(true);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            lstDados.setLayoutManager(linearLayoutManager);

            clienteRepositorio = new ExperimentoRepositorio(conexao);

            List<Experimento> dados = clienteRepositorio.buscarTodosPorProprietario(emailLogado);
            clienteAdapter  = new ExperimentoAdapter(dados);

            lstDados.setAdapter(clienteAdapter);

            registerForContextMenu(layoutContentMain);

            Toast.makeText(getApplicationContext(), "Logado com sucesso.", Toast.LENGTH_LONG).show();
        } else { // se o usuário nao estiver logado
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigurarExperimento.class);
                intent.putExtra("proprietario", emailLogado);
                startActivity(intent);
            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(true);

        drawerLayout.closeDrawers();

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_item_one: {
                Intent intent = new Intent(getApplicationContext(), ConfigurarExperimento.class);

                intent.putExtra("proprietario", emailLogado);

                startActivity(intent);

                break;
            }
            case R.id.nav_item_sair: {
                    mAuth.signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                break;
            }
            case R.id.nav_item_como_usar: {
                Intent intent = new Intent(getApplicationContext(), ComoUsar.class);

                startActivity(intent);
                break;
            }
            case R.id.nav_item_sobre: {
                Intent intent = new Intent(getApplicationContext(), Sobre.class);

                startActivity(intent);
                break;
            }
            case R.id.nav_item_alvo: {
                Intent intent = new Intent(getApplicationContext(), Alvo.class);

                startActivity(intent);
                break;
            }
            default: {

                return true;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sair, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mn_sair:

                mAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            String email = currentUser.getEmail();
            List<Experimento> dados = clienteRepositorio.buscarTodosPorProprietario(email);
            clienteAdapter = new ExperimentoAdapter(dados);
            lstDados.setAdapter(clienteAdapter);
            clienteAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0){
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if(currentUser != null){
                String email = currentUser.getEmail();

                List<Experimento> dados = clienteRepositorio.buscarTodosPorProprietario(email);
                clienteAdapter = new ExperimentoAdapter(dados);
                lstDados.setAdapter(clienteAdapter);
            }
        }
    }

    private void criarConexao(){
        try{
            dadosOpenHelper = new BdHelperExperimento(this);

            conexao = dadosOpenHelper.getWritableDatabase();

        } catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
    }
    public void IrConfigurarExperimento(View v){
        Intent intent = new Intent(this, ConfigurarExperimento.class);
        startActivity(intent);
    }

}
