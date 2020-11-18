package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CadastrosFragment extends Fragment {
    private Button cliente;
    private Button socio;
    private  Button contrato;

    public static CadastrosFragment newInstance() {
        CadastrosFragment fragment = new CadastrosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastros, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Cadastros");
        cliente = (Button) view.findViewById(R.id.btn_cad_cli);
        cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment cadCliFragment = CadClienteFragment.newInstance();
                openFragment(cadCliFragment);
            }
        });

        socio = (Button) view.findViewById(R.id.btn_cad_socio);
        socio.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Fragment cadSocioFragment = CadSocioFragment.newInstance();
                openFragment(cadSocioFragment);
            }
        });

        contrato = (Button) view.findViewById(R.id.btn_cad_contr);
        contrato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Fragment cadContrFragment = CadContratoFragment.newInstance();
                openFragment(cadContrFragment);
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
