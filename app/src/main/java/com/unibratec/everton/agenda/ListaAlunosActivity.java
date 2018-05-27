package com.unibratec.everton.agenda;

import android.content.Intent;
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

    private void carregarListaAlunos(){

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

        alunosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno)  alunosListView.getItemAtPosition(position);
                Intent goFormStudent = new Intent(ListaAlunosActivity.this, FormularioAluno.class);
                goFormStudent.putExtra("aluno", aluno);
                startActivity(goFormStudent);

            }
        });



        Button buttonSave = (Button) findViewById(R.id.adicionar_aluno);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goFormStudent = new Intent(ListaAlunosActivity.this, FormularioAluno.class);
                startActivity(goFormStudent);
            }
        });

        registerForContextMenu(alunosListView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaAlunos();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
       MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) alunosListView.getItemAtPosition(info.position);
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
