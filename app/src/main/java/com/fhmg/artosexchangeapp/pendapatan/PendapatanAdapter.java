package com.fhmg.artosexchangeapp.pendapatan;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fhmg.artosexchangeapp.R;
import com.fhmg.artosexchangeapp.utils.database.TblPendapatan;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendapatanAdapter extends
        RecyclerView.Adapter<PendapatanAdapter.ViewHolder2> {

    private static final String TAG = PendapatanAdapter.class.getSimpleName();

    private List<TblPendapatan> list2;
    private PendapatanAdapterCallback mAdapterCallback2;

    public PendapatanAdapter(List<TblPendapatan> list2, PendapatanAdapterCallback adapterCallback2) {
        this.list2 = list2;
        this.mAdapterCallback2 = adapterCallback2;
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pendapatan,
                parent, false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder2 holder, int position) {
        TblPendapatan item = list2.get(position);

        String pendapatan = item.getPendapatan();
        int nominal = item.getNominal();
        String date = item.getTanggal();

        holder.tvPendapatan.setText(pendapatan);
        holder.tvDate.setText(date);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.tvNominal2.setText(formatRupiah.format(nominal));
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public void clear() {
        int size = this.list2.size();
        this.list2.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPendapatan)
        TextView tvPendapatan;
        @BindView(R.id.tvNominal2)
        TextView tvNominal2;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.ivDelete2)
        ImageView ivDelete2;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivDelete2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallback2.onDelete(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mAdapterCallback2.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface PendapatanAdapterCallback {
        void onLongClick(int position);
        void onDelete(int position);
    }
}