package com.example.listpersonalizada.BancoDeDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EstacionamentoDao {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public EstacionamentoDao(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserirPlaca(Estacionamento estacionamento) {
        ContentValues values = new ContentValues();
        values.put("placa", estacionamento.getPlacaCarro());
        values.put("dataEntrada",estacionamento.getDataEntrada());
        values.put("horaEntrada",estacionamento.getHoraEntrada());
        return banco.insert("estacionamento",null, values);
    }

    public ArrayList<Estacionamento> obterTodos() {
        ArrayList<Estacionamento> estacionamentos = new ArrayList<>();
        Cursor cursor = banco.query("estacionamento", new String[]{"placa", "dataEntrada", "horaEntrada"},
                null,null,null,null,null);
        while (cursor.moveToNext()) {
            Estacionamento e = new Estacionamento();
            e.setPlacaCarro(cursor.getString(0));
            e.setDataEntrada(cursor.getString(1));
            e.setHoraEntrada(cursor.getString(2));
            estacionamentos.add(e);
        }
        return estacionamentos;
    }

    public void excluir(Estacionamento e) {
        banco.delete("estacionamento", "placa = ?", new String[] {e.getPlacaCarro().toString()});
    }
}
