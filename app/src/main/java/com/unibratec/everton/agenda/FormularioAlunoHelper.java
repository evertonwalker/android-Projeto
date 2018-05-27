package com.unibratec.everton.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

public class FormularioAlunoHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;
    private Aluno aluno;

    public FormularioAlunoHelper (FormularioAluno activity){

        campoNome = activity.findViewById(R.id.aluno_nome);
        campoEndereco = activity.findViewById(R.id.aluno_endereco);
        campoTelefone = activity.findViewById(R.id.aluno_telefone);
        campoSite = activity.findViewById(R.id.aluno_site);
        campoNota = activity.findViewById(R.id.aluno_nota);
        aluno = new Aluno();

    }

    public Aluno getAluno(){

        Aluno aluno = new Aluno();

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));

        return aluno;
    }

    public void preencherFormulario(Aluno aluno){

        this.campoNome.setText(aluno.getNome());
        this.campoEndereco.setText(aluno.getEndereco());
        this.campoTelefone.setText(aluno.getTelefone());
        this.campoSite.setText(aluno.getSite());
        this.campoNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;



    }

}
