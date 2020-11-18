package com.cursoapp.papillonapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoapp.papillonapplication.CadContratoFragment;
import com.cursoapp.papillonapplication.DetalheContratoFragment;
import com.cursoapp.papillonapplication.Helpers.ContratoHelper;
import com.cursoapp.papillonapplication.Models.Contrato;
import com.cursoapp.papillonapplication.R;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.MyViewHolder> {
    private List<Contrato> contratos;
    private AlertDialog alerta;
    private FragmentActivity activityAtual;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtServico;
        TextView txtNomeCli;
        ImageButton btnDelContrato;
        ImageButton btnEdiContrato;

        public MyViewHolder(View item) {
            super(item);
            cv = (CardView) item.findViewById(R.id.crd_item_contrato);
            txtServico = (TextView) item.findViewById(R.id.txt_item_servico);
            txtNomeCli = (TextView) item.findViewById(R.id.txt_item_nome_cliente);

            btnDelContrato = (ImageButton) item.findViewById(R.id.btn_deletar_contrato);
            btnEdiContrato = (ImageButton) item.findViewById(R.id.btn_editar_contrato);
        }
    }

    public ContratoAdapter(List<Contrato> contratos, FragmentActivity activityAtual) {
        this.activityAtual = activityAtual;
        this.contratos = contratos;
    }

    @Override
    public ContratoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contrato, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txtServico.setText(contratos.get(position).servico);
        holder.txtNomeCli.setText(contratos.get(position).cliente.nome);
        holder.btnDelContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensagemAlerta(v.getContext(),holder.getAdapterPosition());
            }
        });
        holder.btnEdiContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadContratoFragment cadContrato = new CadContratoFragment();
                cadContrato.contrato = contratos.get(holder.getAdapterPosition());
                Fragment cadContratoFragment = cadContrato;
                openFragment(cadContratoFragment);
                Toast.makeText(v.getContext(), "ID DO CLIENTE: " + contratos.get(holder.getAdapterPosition()).id, Toast.LENGTH_LONG).show();
            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetalheContratoFragment detalhe = new DetalheContratoFragment(contratos.get(holder.getAdapterPosition()));
                openFragment(detalhe);
            }
        });
    }
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = activityAtual.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void mensagemAlerta(final Context context, final int itemPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Deletar contrato");
        builder.setMessage("Deletar registro? Esse registro será deletado permanentemente!");

        builder.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ContratoHelper contratoHelper = new ContratoHelper(context);
                if(contratoHelper.deletarContrato(contratos.get(itemPosition).id)){
                    contratos.remove(itemPosition);
                    notifyItemRemoved(itemPosition);
                    Toast.makeText(context, "Registro deletado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }
}