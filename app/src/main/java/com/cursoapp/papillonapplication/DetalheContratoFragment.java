package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cursoapp.papillonapplication.Models.Contrato;

public class DetalheContratoFragment extends Fragment {
    private TextView servico;
    private TextView dataPedido;
    private TextView dataEntrega;
    private TextView situacao;
    private TextView comentario;
    private TextView nomeSocio;
    private TextView cargoSocio;
    private TextView nomeCliente;
    private TextView cnpjCliente;
    private TextView telefoneCliente;

    public Contrato contrato;
    public DetalheContratoFragment(Contrato contrato){
        this.contrato = contrato;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_contrato, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Detalhes do contrato");
         servico = (TextView) view.findViewById(R.id.txt_detalhe_servico);
         dataPedido = (TextView) view.findViewById(R.id.txt_detalhe_pedido);
         dataEntrega = (TextView) view.findViewById(R.id.txt_detalhe_entrega);
         situacao = (TextView) view.findViewById(R.id.txt_detalhe_situacao);
         comentario = (TextView) view.findViewById(R.id.txt_detalhe_coment);
         nomeSocio = (TextView) view.findViewById(R.id.txt_detalhe_socio);
         cargoSocio = (TextView) view.findViewById(R.id.txt_detalhe_cargo);
         nomeCliente = (TextView) view.findViewById(R.id.txt_detalhe_cli);
         cnpjCliente = (TextView) view.findViewById(R.id.txt_detalhe_cnpj);
         telefoneCliente = (TextView) view.findViewById(R.id.txt_detalhe_tel);

        servico.setText(contrato.servico);
        dataPedido.setText(contrato.dataPedido);
        dataEntrega.setText(contrato.dataEntrega);
        situacao.setText(contrato.situacao);
        comentario.setText(contrato.comentario);
        nomeSocio.setText(contrato.socio.nome);
        cargoSocio.setText(contrato.socio.cargo);
        nomeCliente.setText(contrato.cliente.nome);
        cnpjCliente.setText(contrato.cliente.cnpj);
        telefoneCliente.setText(contrato.cliente.telefone);

        return view;
    }
}
