package com.unibratec.everton.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

import java.util.Random;

public class FormularioAlunoHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;
    private Aluno aluno;
    private ImageView campoFoto;

    public FormularioAlunoHelper (FormularioAluno activity){

        campoNome = activity.findViewById(R.id.aluno_nome);
        campoEndereco = activity.findViewById(R.id.aluno_endereco);
        campoTelefone = activity.findViewById(R.id.aluno_telefone);
        campoSite = activity.findViewById(R.id.aluno_site);
        campoNota = activity.findViewById(R.id.aluno_nota);
        campoFoto = (ImageView) activity.findViewById(R.id.usuario_foto);
        aluno = new Aluno();

    }

    public Aluno getAluno(){

        Aluno aluno = new Aluno();

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencherFormulario(Aluno aluno){

        this.campoNome.setText(aluno.getNome());
        this.campoEndereco.setText(aluno.getEndereco());
        this.campoTelefone.setText(aluno.getTelefone());
        this.campoSite.setText(aluno.getSite());
        this.campoNota.setProgress(aluno.getNota().intValue());
        carregaFoto(aluno.getCaminhoFoto());
        this.aluno = aluno;

    }

    public void carregaFoto(String caminhoFoto) {

        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 700, 700, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }
    }
}
