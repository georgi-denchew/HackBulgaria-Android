package com.example.georgi.expenselist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Georgi on 21.11.2014 г..
 */
public class ExpensesListFragment extends Fragment {

    private EditText newDescriptionTextView;
    private EditText newPriceTextView;

    private ExpensesAdapter mExpensesAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        newDescriptionTextView = (EditText) getActivity().findViewById(R.id.new_description);
        newPriceTextView = (EditText) getActivity().findViewById(R.id.new_price);

        Button addButton = (Button) getActivity().findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String description = newDescriptionTextView.getText().toString();
                Double price = Double.parseDouble(newPriceTextView.getText().toString());
                Expense newExpense = new Expense(description, price);

                mExpensesAdapter.add(newExpense);
                newDescriptionTextView.setText("");
                newPriceTextView.setText("");
            }
        });

        mExpensesAdapter = new ExpensesAdapter();
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(mExpensesAdapter);

        return view;
    }

}
