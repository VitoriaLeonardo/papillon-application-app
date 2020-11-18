package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoapp.papillonapplication.Adapters.ContatoAdapter;
import com.cursoapp.papillonapplication.Helpers.ClienteHelper;

public class TelefoneFragment extends Fragment {

    private RecyclerView listaContatos;
    private RecyclerView.Adapter adp;
    private RecyclerView.LayoutManager mng;

    public static TelefoneFragment newInstance() {
        TelefoneFragment fragment = new TelefoneFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_telefone2, container, false);
        listaContatos = (RecyclerView) view.findViewById(R.id.rv_lista_contato);
        listaContatos.setHasFixedSize(true);
        mng = new LinearLayoutManager(getContext());
        listaContatos.setLayoutManager(mng);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Lista de contatos");
        ClienteHelper clienteHelper = new ClienteHelper(getContext());
        adp = new ContatoAdapter(clienteHelper.buscarClientes(), getActivity());
        listaContatos.setAdapter(adp);
        return view;
    }
}
