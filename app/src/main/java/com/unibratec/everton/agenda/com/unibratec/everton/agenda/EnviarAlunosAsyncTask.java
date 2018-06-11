package com.unibratec.everton.agenda.com.unibratec.everton.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.dao.AlunoDao;
import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

import java.util.List;

public class EnviarAlunosAsyncTask extends AsyncTask <Object, Object, String> {

    private Context context;
    private ProgressDialog dialog;


    public EnviarAlunosAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
     dialog =  ProgressDialog.show(context, "Aguarde", "Enviando usuários...");
    }

    @Override
    protected String doInBackground(Object[] objects) {

        AlunoDao dao = new AlunoDao(context);
        List<Aluno> alunos = dao.buscarAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();

        String jsonAlunos = conversor.converterParaJson(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(jsonAlunos);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
    }
}
