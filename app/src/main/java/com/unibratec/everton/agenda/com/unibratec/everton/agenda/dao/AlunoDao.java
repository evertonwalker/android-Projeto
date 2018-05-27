package com.unibratec.everton.agenda.com.unibratec.everton.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.unibratec.everton.agenda.com.unibratec.everton.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao extends SQLiteOpenHelper {

    public AlunoDao(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);

    }

    public void inserir(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getAlunosDados(aluno);

        db.insert("Alunos", null, dados);

    }

    @NonNull
    private ContentValues getAlunosDados(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }

    public List<Aluno> buscarAlunos() {

        String sql = "Select * from Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> listaAlunos = new ArrayList<Aluno>();

        while (c.moveToNext()) {

            Aluno aluno = new Aluno();

            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

            listaAlunos.add(aluno);

        }

        return listaAlunos;

    }

    public void deleta(Aluno aluno){

        SQLiteDatabase  db = getWritableDatabase();
         String [] params = {String.valueOf(aluno.getNome())};
         db.delete("Alunos", "nome = ?", params);

    }

    public void altera(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getAlunosDados(aluno);

        String[] params ={aluno.getId().toString()};

        db.update("Alunos", dados, "id = ?", params);
    }

}
