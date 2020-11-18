package com.cursoapp.papillonapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoapp.papillonapplication.Helpers.ClienteHelper;
import com.cursoapp.papillonapplication.Models.Cliente;

public class CadClienteFragment extends Fragment {
    private EditText nome;
    private EditText cnpj;
    private EditText cep;
    private EditText numero;
    private EditText email;
    private EditText telefone;
    private Button cadastrar;
    public Cliente cliente;

    public static CadClienteFragment newInstance() {
        CadClienteFragment fragment = new CadClienteFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_cliente, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Cadastrar cliente");
        nome = (EditText) view.findViewById(R.id.edt_nome_cli);
        cnpj = (EditText) view.findViewById(R.id.edt_cnpj_cli);
        cep = (EditText) view.findViewById(R.id.edt_cep_cli);
        numero = (EditText) view.findViewById(R.id.edt_n_cli);
        email = (EditText) view.findViewById(R.id.edt_email_cli);
        telefone = (EditText) view.findViewById(R.id.edt_telefone_cli);
        cadastrar = (Button) view.findViewById(R.id.btn_cadastrar_cliente);

        if(cliente != null && cliente.id > 0){
            estaEditando();
        }else {
            cadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cliente = new Cliente(
                            nome.getText().toString(),
                            cnpj.getText().toString(),
                            cep.getText().toString(),
                            numero.getText().toString(),
                            email.getText().toString(),
                            telefone.getText().toString()
                    );

                    if (cadastrarCliente(cliente)) {
                        nome.setText("");
                        cnpj.setText("");
                        cep.setText("");
                        numero.setText("");
                        email.setText("");
                        telefone.setText("");
                    }
                }
            });
        }

        return view;
    }

    private void estaEditando(){
        nome.setText(cliente.nome);
        cnpj.setText(cliente.cnpj);
        cep.setText(cliente.cep);
        numero.setText(cliente.numero);
        email.setText(cliente.email);
        telefone.setText(cliente.telefone);

        cadastrar.setText("Editar");
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteHelper dbCliHelper = new ClienteHelper(getContext());
                cliente.nome = nome.getText().toString();
                cliente.cnpj = cnpj.getText().toString();
                cliente.cep = cep.getText().toString();
                cliente.numero = numero.getText().toString();
                cliente.email = email.getText().toString();
                cliente.telefone = telefone.getText().toString();

                if(dbCliHelper.alterarCliente(cliente)){
                    Toast.makeText(getActivity(), "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Erro ao atualizar!", Toast.LENGTH_LONG).show();
                }
                Fragment consCliFragment = ConsCliFragment.newInstance();
                openFragment(consCliFragment);

            }
        });
    }

    private boolean cadastrarCliente(Cliente cliente){
        ClienteHelper dbCliHelper = new ClienteHelper(getContext());
        SQLiteDatabase db = dbCliHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", cliente.nome);
        values.put("cnpj", cliente.cnpj);
        values.put("cep", cliente.cep);
        values.put("numero", cliente.numero);
        values.put("email", cliente.email);
        values.put("telefone", cliente.telefone);
        long id = db.insert("Cliente", null, values);

        if(id>0){
            Toast.makeText(getActivity(), "Cliente "+cliente.nome+" cadastrado", Toast.LENGTH_LONG).show();
            return true;
        }else{
            return false;
        }
    }
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
