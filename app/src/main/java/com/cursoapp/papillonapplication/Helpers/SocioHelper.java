package com.cursoapp.papillonapplication.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cursoapp.papillonapplication.Models.Socio;
import com.cursoapp.papillonapplication.Models.Socio;

import java.util.ArrayList;
import java.util.List;

public class SocioHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_android.db";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    private static final String[] SQL_CREATE_TABLE = {
            "CREATE TABLE Socio (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "cargo TEXT," +
                    "login TEXT," +
                    "senha TEXT)",
            "CREATE TABLE Cliente (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT," +
                    "cnpj TEXT," +
                    "cep TEXT," +
                    "numero TEXT," +
                    "email TEXT," +
                    "telefone TEXT)",
            "CREATE TABLE Contrato (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "servico TEXT," +
                    "dataPedido TEXT," +
                    "dataEntrega TEXT," +
                    "situacao TEXT," +
                    "comentario TEXT," +
                    "idSocio INTEGER," +
                    "idCli INTEGER, FOREIGN KEY(idCli) REFERENCES Cliente(id)," +
                    "FOREIGN KEY(idSocio) REFERENCES Socio(id))"
    };

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS Socio";
    public SocioHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = this.getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i=0; i < SQL_CREATE_TABLE.length; i++) {
            db.execSQL(SQL_CREATE_TABLE[i]);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public List<Socio> buscarSocios(){
        List<Socio> socios = new ArrayList<Socio>();
        String[] colunas = {
                "id", "nome", "cargo", "login", "senha"
        };
        Cursor cursor =  db.query("Socio", colunas, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            Socio socio = new Socio(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            socio.id = Integer.parseInt(cursor.getString(0));
            socios.add(socio);
        }
        return socios;
    }

    public boolean deletarSocio(int id){
        String where = "id = ?";
        String[] whereArgs = { Integer.toString(id) };
        int qtdDeletada = db.delete("Socio", where, whereArgs);

        return qtdDeletada > 0;
    }

    public boolean alterarSocio(Socio socio){
        ContentValues valores = new ContentValues();
        valores.put("nome", socio.nome);
        valores.put("cargo", socio.cargo);
        valores.put("login", socio.login);
        valores.put("senha", socio.senha);

        String where = "id = ?";
        String[] whereArgs = {
                Integer.toString(socio.id)
        };

        int qtdAlterada = db.update(
                "Socio",
                valores, where, whereArgs
        );

        return qtdAlterada > 0;
    }
}