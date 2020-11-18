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

import com.cursoapp.papillonapplication.Helpers.SocioHelper;
import com.cursoapp.papillonapplication.Models.Socio;

public class CadSocioFragment extends Fragment {
    private EditText nome;
    private EditText cargo;
    private EditText login;
    private EditText senha;
    private Button cadastrar;
    public Socio socio;

    public static CadSocioFragment newInstance() {
        CadSocioFragment fragment = new CadSocioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_socio, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Cadastrar socio");
        nome = (EditText) view.findViewById(R.id.edt_nome_socio);
        cargo = (EditText) view.findViewById(R.id.edt_cargo_socio);
        login = (EditText) view.findViewById(R.id.edt_login_socio);
        senha = (EditText) view.findViewById(R.id.edt_senha_socio);
        cadastrar = (Button) view.findViewById(R.id.btn_cadastrar_socio);

        if(socio != null && socio.id > 0){
            estaEditando();
        }else {
            cadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    socio = new Socio(nome.getText().toString(), cargo.getText().toString(), login.getText().toString(), senha.getText().toString());

                    if (cadastrarSocio(socio)) {
                        nome.setText("");
                        cargo.setText("");
                        login.setText("");
                        senha.setText("");
                    }
                }
            });
        }

        return view;
    }

    private void estaEditando(){
        nome.setText(socio.nome);
        cargo.setText(socio.cargo);
        login.setText(socio.login);
        senha.setText(socio.senha);

        cadastrar.setText("Editar");
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocioHelper dbSocioHelper = new SocioHelper(getContext());
                socio.nome = nome.getText().toString();
                socio.cargo = cargo.getText().toString();
                socio.login = login.getText().toString();
                socio.senha = senha.getText().toString();

                if(dbSocioHelper.alterarSocio(socio)){
                    Toast.makeText(getActivity(), "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Erro ao atualizar!", Toast.LENGTH_LONG).show();
                }
                Fragment consSocioFragment = ConsSocioFragment.newInstance();
                openFragment(consSocioFragment);

            }
        });
    }

    private boolean cadastrarSocio(Socio socio){
        SocioHelper dbSocioHelper = new SocioHelper(getContext());
        SQLiteDatabase db = dbSocioHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", socio.nome);
        values.put("cargo", socio.cargo);
        values.put("login", socio.login);
        values.put("senha", socio.senha);

        long id = db.insert("Socio", null, values);

        if(id>0){
            Toast.makeText(getActivity(), "Socio "+socio.nome+" cadastrado", Toast.LENGTH_LONG).show();
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
