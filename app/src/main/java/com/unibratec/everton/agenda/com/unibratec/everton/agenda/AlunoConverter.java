package com.unibratec.everton.agenda.com.unibratec.everton.agenda;

import android.util.Log;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

public class AlunoConverter {

    public String converterParaJson(List<Aluno> alunos){

        JSONStringer js = new JSONStringer();
        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }

            return js.endArray().endObject()
                    .endArray().endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
