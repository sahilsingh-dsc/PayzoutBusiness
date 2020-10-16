package com.payzout.business.portfolio;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.payzout.business.R;

import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {

    private Context context;
    private List<Portfolio> portfolioList;

    public PortfolioAdapter(Context context, List<Portfolio> portfolioList) {
        this.context = context;
        this.portfolioList = portfolioList;
    }

    @NonNull
    @Override
    public PortfolioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioAdapter.ViewHolder holder, int position) {
        Portfolio portfolio = portfolioList.get(position);
        holder.tvInvestmentDate.setText(portfolio.getP_date());
        holder.tvInvestmentClass.setText(portfolio.getP_class());
        String invested_balance = context.getResources().getString(R.string.rupee)+" "+portfolio.getP_amount();
        holder.tvInvestedBalance.setText(invested_balance);
        String profit_balance = context.getResources().getString(R.string.rupee)+" "+portfolio.getP_profit();
        holder.tvProfitBalance.setText(profit_balance);
        String interest_rate = portfolio.getP_interest()+context.getResources().getString(R.string.percent);
        holder.tvInterestRate.setText(interest_rate);
        String locking_period = portfolio.getP_locking()+" "+context.getString(R.string.days);
        holder.tvLockingPeriod.setText(locking_period);

        holder.tvWithdrawFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Withdraw Funds")
                        .setMessage("Dear user you can only withdraw fund between of 1st to 5th of every month.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return portfolioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lvOrder;
        private TextView tvInvestmentDate;
        private TextView tvInvestmentClass;
        private TextView tvInvestedBalance;
        private TextView tvProfitBalance;
        private TextView tvInterestRate;
        private TextView tvLockingPeriod;
        TextView tvWithdrawFunds;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lvOrder = itemView.findViewById(R.id.lvOrder);
            tvInvestmentDate = itemView.findViewById(R.id.tvInvestmentDate);
            tvInvestmentClass = itemView.findViewById(R.id.tvInvestmentClass);
            tvInvestedBalance = itemView.findViewById(R.id.tvInvestedBalance);
            tvProfitBalance = itemView.findViewById(R.id.tvProfitBalance);
            tvInterestRate = itemView.findViewById(R.id.tvInterestRate);
            tvLockingPeriod = itemView.findViewById(R.id.tvLockingPeriod);
            tvWithdrawFunds = itemView.findViewById(R.id.tvWithdrawFunds);
        }

    }
}
