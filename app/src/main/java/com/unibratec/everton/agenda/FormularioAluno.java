package com.unibratec.everton.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.dao.AlunoDao;
import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

public class FormularioAluno extends AppCompatActivity {

    private FormularioAlunoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        helper = new FormularioAlunoHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(aluno != null){
            helper.preencherFormulario(aluno);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_formulario_ok:
            Aluno aluno = helper.getAluno();
            Toast.makeText(FormularioAluno.this, "Aluno : " + aluno.getNome() +" Salvo com Sucesso." , Toast.LENGTH_SHORT).show();

            AlunoDao dao = new AlunoDao(this);

            if(aluno.getId() != null){
                dao.altera(aluno);
            } else {
                dao.inserir(aluno);
            }

            dao.close();

            finish();
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
