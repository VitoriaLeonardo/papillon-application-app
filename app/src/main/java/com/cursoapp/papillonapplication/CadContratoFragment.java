package com.cursoapp.papillonapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cursoapp.papillonapplication.Helpers.ClienteHelper;
import com.cursoapp.papillonapplication.Helpers.ContratoHelper;
import com.cursoapp.papillonapplication.Helpers.SocioHelper;
import com.cursoapp.papillonapplication.Models.Cliente;
import com.cursoapp.papillonapplication.Models.Contrato;
import com.cursoapp.papillonapplication.Models.Socio;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class CadContratoFragment extends Fragment {
    ArrayList<String> items=new ArrayList<>();
    List<Cliente> clientes;
    List<Socio> socios;
    SpinnerDialog spinnerDialog;
    Cliente clienteSelecionado;
    Socio socioSelecionado;

    public Contrato contrato;
    boolean seSocio;

    private ImageButton buscarCliente;
    private  ImageButton buscarSocio;
    private Button cadContrato;
    private EditText nomeCliente;
    private EditText nomeSocio;

    private EditText servico;
    private EditText dataPedido;
    private EditText dataEntrega;
    private EditText situacao;
    private EditText comentario;


    public static CadContratoFragment newInstance() {
        CadContratoFragment fragment = new CadContratoFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_contrato, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Cadastrar contrato");
        //Buscando os edtText
        servico = (EditText)view.findViewById(R.id.edt_servico_contrato);
        dataPedido = (EditText)view.findViewById(R.id.edt_pedido_contrato);
        dataEntrega = (EditText)view.findViewById(R.id.edt_entrega_contrato);
        situacao = (EditText)view.findViewById(R.id.edt_sit_contrato);
        comentario = (EditText)view.findViewById(R.id.edt_coment_contrato);

        buscarSocio = (ImageButton)view.findViewById(R.id.btn_buscar_socio);
        nomeSocio = (EditText)view.findViewById(R.id.edt_nome_socio_contrato);
        cadContrato = (Button)view.findViewById(R.id.btn_cadastrar_contrato);
        nomeSocio.setEnabled(false);

        buscarCliente = (ImageButton)view.findViewById(R.id.btn_buscar_cliente);
        nomeCliente = (EditText)view.findViewById(R.id.edt_nome_cliente);
        nomeCliente.setEnabled(false);

        ClienteHelper clienteHelper = new ClienteHelper(getContext());
        clientes = clienteHelper.buscarClientes();

        SocioHelper socioHelper = new SocioHelper(getContext());
        socios = socioHelper.buscarSocios();


        //spinnerDialog=new SpinnerDialog(getActivity(),items,"Selecione uma das opções!","Close Button Text");// With No Animation
        spinnerDialog=new SpinnerDialog(getActivity(),items,"Selecione uma das opções!",R.style.DialogAnimations_SmileWindow,"Close Button Text");// With 	Animation

        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default



        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                if(seSocio){
                    socioSelecionado = socios.get(position);
                    nomeSocio.setText(socios.get(position).nome);
                }else {
                    clienteSelecionado = clientes.get(position);
                    nomeCliente.setText(clientes.get(position).nome);
                }

            }
        });

        buscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seSocio = false;
                items.clear();
                for(int i = 0; clientes.size() > i; i++){
                    items.add(clientes.get(i).nome);
                }

                spinnerDialog.showSpinerDialog();
            }
        });
        buscarSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                seSocio = true;
                for(int i = 0; socios.size() > i; i++){
                    items.add(socios.get(i).nome);
                }
                spinnerDialog.showSpinerDialog();
            }
        });
        if (contrato!=null && contrato.id > 0){
            estaEditando();
        }else {

            cadContrato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContratoHelper contratoHelper = new ContratoHelper(getContext());
                    if(validaGalera()) {
                        contrato = new Contrato(
                                servico.getText().toString(),
                                dataPedido.getText().toString(),
                                dataEntrega.getText().toString(),
                                situacao.getText().toString(),
                                comentario.getText().toString(),
                                clienteSelecionado, socioSelecionado
                        );

                        if (contratoHelper.cadastrarContrato(contrato)) {
                            servico.setText("");
                            dataPedido.setText("");
                            dataEntrega.setText("");
                            situacao.setText("");
                            comentario.setText("");
                            nomeCliente.setText("");
                            nomeSocio.setText("");
                            Toast.makeText(getActivity(), "Criado com sucesso!", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), "Erro ao criar!", Toast.LENGTH_LONG).show();
                        }
                    }else{

                        Toast.makeText(getActivity(), "Você precisa selecionar um sócio e um cliente!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return view;
    }

    public boolean validaGalera(){
        if(socioSelecionado != null && clienteSelecionado != null) return  true;
        return false;
    }

    private void estaEditando(){
        nomeCliente.setText(contrato.cliente.nome);
        servico.setText(contrato.servico);
        nomeSocio.setText(contrato.socio.nome);
        dataPedido.setText(contrato.dataPedido);
        dataEntrega.setText(contrato.dataEntrega);
        situacao.setText(contrato.situacao);
        comentario.setText(contrato.comentario);
        clienteSelecionado = contrato.cliente;
        socioSelecionado = contrato.socio;

        cadContrato.setText("Editar");
        cadContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContratoHelper dbContratoHelper = new ContratoHelper(getContext());
                contrato.cliente = clienteSelecionado;
                contrato.servico = servico.getText().toString();
                contrato.socio = socioSelecionado;
                contrato.dataPedido = dataPedido.getText().toString();
                contrato.dataEntrega = dataEntrega.getText().toString();
                contrato.situacao = situacao.getText().toString();
                contrato.comentario = comentario.getText().toString();

                if(dbContratoHelper.alterarContrato(contrato)){
                    Toast.makeText(getActivity(), "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Erro ao atualizar!", Toast.LENGTH_LONG).show();
                }
                Fragment consContrFragment = ConsContrFragment.newInstance();
                openFragment(consContrFragment);

            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}