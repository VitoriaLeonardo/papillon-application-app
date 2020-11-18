package com.cursoapp.papillonapplication.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cursoapp.papillonapplication.Models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteHelper extends SQLiteOpenHelper {
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

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS Cliente";
    public ClienteHelper(Context context) {
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

    public List<Cliente> buscarClientes(){
        List<Cliente> clientes = new ArrayList<Cliente>();
        String[] colunas = {
                "id", "nome", "cnpj", "cep", "numero", "email", "telefone"
        };
        Cursor cursor =  db.query("Cliente", colunas, null, null, null, null, "id DESC");
        while(cursor.moveToNext()){
            Cliente cliente = new Cliente(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            cliente.id = Integer.parseInt(cursor.getString(0));
            clientes.add(cliente);
        }
        return clientes;
    }

    public boolean deletarCliente(int id){
        String where = "id = ?";
        String[] whereArgs = { Integer.toString(id) };
        int qtdDeletada = db.delete("Cliente", where, whereArgs);

        return qtdDeletada > 0;
    }

    public boolean alterarCliente(Cliente cliente){
        ContentValues valores = new ContentValues();
        valores.put("nome", cliente.nome);
        valores.put("cnpj", cliente.cnpj);
        valores.put("cep", cliente.cep);
        valores.put("numero", cliente.numero);
        valores.put("email", cliente.email);
        valores.put("telefone", cliente.telefone);

        String where = "id = ?";
        String[] whereArgs = {
                Integer.toString(cliente.id)
        };

        int qtdAlterada = db.update(
                "Cliente",
                valores, where, whereArgs
        );

        return qtdAlterada > 0;
    }
}
