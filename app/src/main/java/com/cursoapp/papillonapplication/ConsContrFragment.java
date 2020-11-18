package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoapp.papillonapplication.Adapters.ContratoAdapter;
import com.cursoapp.papillonapplication.Helpers.ContratoHelper;

public class ConsContrFragment extends Fragment {

    private RecyclerView listaContrato;
    private RecyclerView.Adapter adp;
    private RecyclerView.LayoutManager mng;

    public static ConsContrFragment newInstance() {
        ConsContrFragment fragment = new ConsContrFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cons_contr, container, false);
        listaContrato = (RecyclerView) view.findViewById(R.id.rv_lista_contrato);
        listaContrato.setHasFixedSize(true);
        mng = new LinearLayoutManager(getContext());
        listaContrato.setLayoutManager(mng);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Consultar contrato");
        ContratoHelper contratoHelper = new ContratoHelper(getContext());
        adp = new ContratoAdapter(contratoHelper.buscarContratos(), getActivity());
        listaContrato.setAdapter(adp);
        return view;
    }

}
