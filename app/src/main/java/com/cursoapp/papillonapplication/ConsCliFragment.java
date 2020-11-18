package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoapp.papillonapplication.Adapters.ClienteAdapter;
import com.cursoapp.papillonapplication.Helpers.ClienteHelper;

public class ConsCliFragment extends Fragment {

    private RecyclerView listaClientes;
    private RecyclerView.Adapter adp;
    private RecyclerView.LayoutManager mng;

    public static ConsCliFragment newInstance() {
        ConsCliFragment fragment = new ConsCliFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cons_cli, container, false);
        listaClientes = (RecyclerView) view.findViewById(R.id.rv_lista_cli);
        listaClientes.setHasFixedSize(true);
        mng = new LinearLayoutManager(getContext());
        listaClientes.setLayoutManager(mng);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Consultar cliente");
        ClienteHelper clienteHelper = new ClienteHelper(getContext());
        adp = new ClienteAdapter(clienteHelper.buscarClientes(), getActivity());
        listaClientes.setAdapter(adp);
        return view;
    }

}
