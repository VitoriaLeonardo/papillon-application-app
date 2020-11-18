package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ConsultasFragment extends Fragment {
    public Button cliente;
    public Button socio;
    public Button contrato;

    public static ConsultasFragment newInstance() {
        ConsultasFragment fragment = new ConsultasFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultas, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Consultas");
        cliente = (Button) view.findViewById(R.id.btn_cons_cli);
        cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment consCliFragment = ConsCliFragment.newInstance();
                openFragment(consCliFragment);
            }
        });

        socio = (Button) view.findViewById(R.id.btn_cons_socio);
        socio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment consSocioFragment = ConsSocioFragment.newInstance();
                openFragment(consSocioFragment);
            }
        });

        contrato = (Button) view.findViewById(R.id.btn_cons_contra);
        contrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment consContrFragment = ConsContrFragment.newInstance();
                openFragment(consContrFragment);
            }
        });
        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
