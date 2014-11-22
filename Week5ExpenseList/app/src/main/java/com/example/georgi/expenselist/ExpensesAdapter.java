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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgi on 21.11.2014 г..
 */
public class ExpensesAdapter extends BaseAdapter {

    private List<Expense> expenses;

    private static final class ViewHolder{
        private TextView descriptionTextView;
        private TextView priceTextView;
        private ImageView deleteImageView;
    }

    public ExpensesAdapter() {
        this.expenses = new ArrayList<Expense>();
        this.expenses.add(new Expense("internet", 10));
        this.expenses.add(new Expense("phone", 15));
        this.expenses.add(new Expense("rent", 40));
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

    private void remove(int position){
        this.expenses.remove(position);
        notifyDataSetChanged();
    }

    public void add(Expense expense){
        this.expenses.add(0, expense);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout = null;

        if (convertView != null) {
            layout = (LinearLayout) convertView;
        } else {
            layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);

            TextView description = (TextView) layout.findViewById(R.id.description);
            TextView price = (TextView) layout.findViewById(R.id.price);
            ImageView imageView = (ImageView) layout.findViewById(R.id.image);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.descriptionTextView = description;
            viewHolder.priceTextView = price;
            viewHolder.deleteImageView = imageView;

            viewHolder.deleteImageView.setTag(position);
            layout.setTag(viewHolder);

            viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    AlertDialog dialog = builder.setTitle("Confirm Delete for item at position " + v.getTag()).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (Integer) v.getTag();
                            ExpensesAdapter.this.remove(position);
                        }
                    }).setNegativeButton("Cancel", null).create();

                    dialog.show();
                }
            });
        }

        Expense expense = expenses.get(position);
        ViewHolder holder = (ViewHolder) layout.getTag();
        holder.descriptionTextView.setText(expense.getDescription());
        holder.priceTextView.setText(expense.getPrice().toString());

        return layout;
    }
}
