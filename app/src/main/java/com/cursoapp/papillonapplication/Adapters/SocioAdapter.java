package com.cursoapp.papillonapplication.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoapp.papillonapplication.CadSocioFragment;
import com.cursoapp.papillonapplication.CadSocioFragment;
import com.cursoapp.papillonapplication.ConsContrFragment;
import com.cursoapp.papillonapplication.Helpers.SocioHelper;
import com.cursoapp.papillonapplication.Helpers.SocioHelper;
import com.cursoapp.papillonapplication.MainActivity;
import com.cursoapp.papillonapplication.Models.Socio;
import com.cursoapp.papillonapplication.Models.Socio;
import com.cursoapp.papillonapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class SocioAdapter extends RecyclerView.Adapter<SocioAdapter.MyViewHolder> {
    private List<Socio> socios;
    private AlertDialog alerta;
    private FragmentActivity activityAtual;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNome;
        TextView txtCargo;
        ImageButton btnDelSocio;
        ImageButton btnEdiSocio;

        public MyViewHolder(View item) {
            super(item);
            cv = (CardView) item.findViewById(R.id.crd_item_socio);
            txtNome = (TextView) item.findViewById(R.id.txt_item_nome);
            txtCargo = (TextView) item.findViewById(R.id.txt_item_cargo);

            btnDelSocio = (ImageButton) item.findViewById(R.id.btn_deletar_socio);
            btnEdiSocio = (ImageButton) item.findViewById(R.id.btn_editar_socio);
        }
    }

    public SocioAdapter(List<Socio> socios, FragmentActivity activityAtual) {
        this.activityAtual = activityAtual;
        this.socios = socios;
    }

    @Override
    public SocioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_socio, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txtNome.setText(socios.get(position).nome);
        holder.txtCargo.setText(socios.get(position).cargo);
        holder.btnDelSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensagemAlerta(v.getContext(),holder.getAdapterPosition());
            }
        });
        holder.btnEdiSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadSocioFragment cadSocio = new CadSocioFragment();
                cadSocio.socio = socios.get(holder.getAdapterPosition());
                Fragment cadSocioFragment = cadSocio;
                openFragment(cadSocioFragment);
                Toast.makeText(v.getContext(), "ID DO SOCIO: " + socios.get(holder.getAdapterPosition()).id, Toast.LENGTH_LONG).show();
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

        builder.setTitle("Deletar registo");
        builder.setMessage("Deletar registro? Esse registro será deletado permanentemente!");

        builder.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                SocioHelper socioHelper = new SocioHelper(context);
                if(socioHelper.deletarSocio(socios.get(itemPosition).id)){
                    socios.remove(itemPosition);
                    notifyItemRemoved(itemPosition);
                    Toast.makeText(context, "Usuário deletado", Toast.LENGTH_SHORT).show();
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
        return socios.size();
    }
}
