package com.unibratec.everton.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.dao.AlunoDao;
import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView alunosListView;

    private void carregarListaAlunos() {

        AlunoDao dao = new AlunoDao(this);
        List<Aluno> listaAlunos = dao.buscarAlunos();

        ArrayAdapter adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, listaAlunos);
        alunosListView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        alunosListView = (ListView) findViewById(R.id.lista_alunos);

        //Bloco de código responsável por mandar paga página para editar o aluno.
        alunosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) alunosListView.getItemAtPosition(position);
                Intent goFormStudent = new Intent(ListaAlunosActivity.this, FormularioAluno.class);
                goFormStudent.putExtra("aluno", aluno);
                startActivity(goFormStudent);

            }
        });

        //Bloco de código responsável por ir para o formulário cadastrar um novo aluno
        Button buttonSave = (Button) findViewById(R.id.adicionar_aluno);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goFormStudent = new Intent(ListaAlunosActivity.this, FormularioAluno.class);
                startActivity(goFormStudent);
            }
        });

        //Registra os alunos no contexto do menu.
        registerForContextMenu(alunosListView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaAlunos();

    }

    //os Contextos dos menus para realizar as determinadas operações
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        //Capturando o usuário daquela posição clicada na tela
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) alunosListView.getItemAtPosition(info.position);

        MenuItem ligarItem = menu.add("Ligar");

        ligarItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Verificação de permissão para poder ligar, 3 atributos,  o contexto que é a própria classe, a permissão ou as permissões e o código que vc da pra essa permissão.

                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent ligarAluno = new Intent(Intent.ACTION_VIEW);
                    ligarAluno.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(ligarAluno);
                }

                return false;
            }
        });


        //Criar o botão enviar sms para o aluno usando novamente intent implicta
        MenuItem smsItem = menu.add("Enviar Sms");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + aluno.getTelefone()));
        smsItem.setIntent(intentSms);

        //Criar o botão para abrir o mapa de acordo com a rua do endereço do aluno:
        MenuItem mapaItem = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        mapaItem.setIntent(intentMapa);

        //Cria o botão visitar site, cria uma intent implicita através da uri que é passado e chama o site do aluno.
        MenuItem visualizar = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if (!site.startsWith("https://")) {
            site = "https://" + site;
        }
        intentSite.setData(Uri.parse(site));
        visualizar.setIntent(intentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(ListaAlunosActivity.this, "O aluno: " + aluno.getNome() + " Foi excluido.", Toast.LENGTH_SHORT).show();

                AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();

                carregarListaAlunos();

                return false;
            }
        });

    }
}
