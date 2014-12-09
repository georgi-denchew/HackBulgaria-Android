package com.example.georgi.expenselist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgi on 21.11.2014 г..
 */
public class ExpensesAdapter extends BaseAdapter {

    public interface DeleteExpenseHandler{
        public boolean deleteExpense(Expense expense);
    }

    private static final class ViewHolder {
        private TextView descriptionTextView;
        private TextView priceTextView;
        private ImageView deleteImageView;
    }

    private DeleteExpenseHandler deleteExpenseHandler;

    private List<Expense> expenses;

    public ExpensesAdapter(DeleteExpenseHandler deleteExpenseHandler, List<Expense> expenses) {
        this.deleteExpenseHandler = deleteExpenseHandler;
        this.expenses = expenses;
    }

    @Override
    public int getCount() {
        return this.expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return this.expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(Expense expense) {

        this.expenses.remove(expense);
        notifyDataSetChanged();
    }

    public void add(Expense expense) {
        this.expenses.add(0, expense);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout = null;

        if (convertView != null) {
            layout = (LinearLayout) convertView;
        } else {
            layout = (LinearLayout)
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);

            TextView description = (TextView) layout.findViewById(R.id.description);
            TextView price = (TextView) layout.findViewById(R.id.price);
            ImageView imageView = (ImageView) layout.findViewById(R.id.image);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.descriptionTextView = description;
            viewHolder.priceTextView = price;
            viewHolder.deleteImageView = imageView;

            layout.setTag(viewHolder);

            viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    AlertDialog dialog = builder.setTitle("Confirm Delete for item at position " + v.getTag()).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Expense expense = (Expense) v.getTag();

                            ExpensesAdapter.this.deleteExpenseHandler.deleteExpense(expense);
                        }
                    }).setNegativeButton("Cancel", null).create();

                    dialog.show();
                }
            });
        }

        Expense expense = expenses.get(position);
        ViewHolder holder = (ViewHolder) layout.getTag();
        holder.descriptionTextView.setText(expense.getDescription());
        holder.deleteImageView.setTag(expense);

        holder.priceTextView.setText(expense.getPrice() + "");

        return layout;
    }
}
