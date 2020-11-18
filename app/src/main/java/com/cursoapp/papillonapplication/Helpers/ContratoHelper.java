package com.cursoapp.papillonapplication.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cursoapp.papillonapplication.Models.Cliente;
import com.cursoapp.papillonapplication.Models.Contrato;
import com.cursoapp.papillonapplication.Models.Socio;

import java.util.ArrayList;
import java.util.List;

public class ContratoHelper extends SQLiteOpenHelper {
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
        private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS Contrato";
        public ContratoHelper(Context context) {
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

        public List<Contrato> buscarContratos(){
            List<Contrato> contratos = new ArrayList<Contrato>();
            String query = "SELECT CO.id as idContrato, CL.id as idCliente, S.id as idSocio," +
                    "CO.servico as servico, CO.dataPedido as dataPedido, " +
                    "CO.dataEntrega as dataEntrega, CO.situacao as situacao, " +
                    "CO.comentario as comentario, S.nome as nomeSocio, S.cargo as cargoSocio, " +
                    "CL.nome as nomeCliente, CL.cnpj as cnpjCliente, CL.telefone as telefoneCliente " +
                    "FROM Contrato CO " +
                    "INNER JOIN Socio S ON CO.idSocio = S.id " +
                    "INNER JOIN Cliente CL ON CO.idCli = CL.id";

            Cursor cursor =  db.rawQuery(query, null);

            while(cursor.moveToNext()){
                Cliente cliente = new Cliente(
                        cursor.getInt(cursor.getColumnIndex("idCliente")),
                        cursor.getString(cursor.getColumnIndex("nomeCliente")),
                        cursor.getString(cursor.getColumnIndex("cnpjCliente")),
                        cursor.getString(cursor.getColumnIndex("telefoneCliente"))
                );

                Socio socio = new Socio(
                        cursor.getInt(cursor.getColumnIndex("idSocio")),
                        cursor.getString(cursor.getColumnIndex("nomeSocio")),
                        cursor.getString(cursor.getColumnIndex("cargoSocio"))
                );

                Contrato contrato = new Contrato(
                        cursor.getInt(cursor.getColumnIndex("idContrato")),
                        cursor.getString(cursor.getColumnIndex("servico")),
                        cursor.getString(cursor.getColumnIndex("dataPedido")),
                        cursor.getString(cursor.getColumnIndex("dataEntrega")),
                        cursor.getString(cursor.getColumnIndex("situacao")),
                        cursor.getString(cursor.getColumnIndex("comentario")),
                        cliente, socio
                );
                contratos.add(contrato);
            }

            return contratos;
        }

        public boolean deletarContrato(int id){
            String where = "id = ?";
            String[] whereArgs = { Integer.toString(id) };
            int qtdDeletada = db.delete("Contrato", where, whereArgs);

            if(qtdDeletada > 0){
                return true;
            }
            return false;
        }

        public boolean alterarContrato(Contrato contrato){
            ContentValues valores = new ContentValues();
            valores.put("servico", contrato.servico);
            valores.put("dataPedido", contrato.dataPedido);
            valores.put("dataEntrega", contrato.dataEntrega);
            valores.put("situacao", contrato.situacao);
            valores.put("comentario", contrato.comentario);
            valores.put("idCli", contrato.cliente.id);
            valores.put("idSocio", contrato.socio.id);

            String where = "id = ?";
            String[] whereArgs = {
                    Integer.toString(contrato.id)
            };

            int qtdAlterada = db.update(
                    "Contrato",
                    valores, where, whereArgs
            );

            if(qtdAlterada >0){
                return true;
            }
            else{
                return false;
            }
        }

    public boolean cadastrarContrato(Contrato contrato){
        ContentValues valores = new ContentValues();
        valores.put("servico", contrato.servico);
        valores.put("dataPedido", contrato.dataPedido);
        valores.put("dataEntrega", contrato.dataEntrega);
        valores.put("situacao", contrato.situacao);
        valores.put("comentario", contrato.comentario);
        valores.put("idSocio", contrato.socio.id);
        valores.put("idCli", contrato.cliente.id);
        long id = db.insert("Contrato", null, valores);

        if(id>0){
            return true;
        }else{
            return false;
        }
    }
    }
