package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursoapp.papillonapplication.Adapters.SocioAdapter;
import com.cursoapp.papillonapplication.Adapters.SocioAdapter;
import com.cursoapp.papillonapplication.Helpers.SocioHelper;
import com.cursoapp.papillonapplication.Helpers.SocioHelper;

public class ConsSocioFragment extends Fragment {

    private RecyclerView listaSocios;
    private RecyclerView.Adapter adp;
    private RecyclerView.LayoutManager mng;

    public static ConsSocioFragment newInstance() {
        ConsSocioFragment fragment = new ConsSocioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cons_socio, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Consultar socio");
        listaSocios = (RecyclerView) view.findViewById(R.id.rv_lista_socio);
        listaSocios.setHasFixedSize(true);
        mng = new LinearLayoutManager(getContext());
        listaSocios.setLayoutManager(mng);

        SocioHelper socioHelper = new SocioHelper(getContext());
        adp = new SocioAdapter(socioHelper.buscarSocios(), getActivity());
        listaSocios.setAdapter(adp);
        return view;
    }
}

