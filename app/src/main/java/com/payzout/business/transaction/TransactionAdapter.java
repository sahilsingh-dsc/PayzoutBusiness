package com.payzout.business.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.payzout.business.R;
import com.payzout.business.utils.Constant;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private Context context;
    private List<Transaction_Response> transactionResponseList;

    public TransactionAdapter(Context context, List<Transaction_Response> transactionResponseList) {
        this.context = context;
        this.transactionResponseList = transactionResponseList;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Transaction_Response transactionResponse = transactionResponseList.get(position);
        String amount = context.getResources().getString(R.string.rupee) + " " + transactionResponse.getAmount();
        holder.tvAmount.setText(amount);
        String datetime = transactionResponse.getDate() + " ~ " + transactionResponse.getTime();
        holder.tvDateTime.setText(datetime);
        holder.tvRemark.setText(transactionResponse.getRemark());

        if (transactionResponse.getStatus().equals("0")) {
            holder.tvStatus.setText(Constant.TXN_FAILED);
        } else if (transactionResponse.getStatus().equals("1")) {
            holder.tvStatus.setText(Constant.TXN_PENDING);
        } else if (transactionResponse.getStatus().equals("2")) {
            holder.tvStatus.setText(Constant.TXN_PROCESSING);
        } else if (transactionResponse.getStatus().equals("3")) {
            holder.tvStatus.setText(Constant.TXN_SUCCESS);
        }
    }

    @Override
    public int getItemCount() {
        return transactionResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAmount;
        private TextView tvRemark;
        private TextView tvDateTime;
        private TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);

        }
    }
}
