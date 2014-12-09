package com.example.georgi.expenselist;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.georgi.expenselist.data.ExpenseDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgi on 21.11.2014 г..
 */
public class ExpensesListFragment extends Fragment implements ExpensesAdapter.DeleteExpenseHandler {
    ExpenseDbHelper expenseDbHelper;

    private EditText newDescriptionTextView;
    private EditText newPriceTextView;

    private ExpensesAdapter mExpensesAdapter;
    private ListView listView;

    private static final String deleteWhereClause = ExpenseDbHelper.ExpenseTable.ID.toString() + " = ?";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        expenseDbHelper = new ExpenseDbHelper(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

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

                ExpenseInsertTask insertTask = new ExpenseInsertTask();
                insertTask.execute(newExpense);

                newDescriptionTextView.setText("");
                newPriceTextView.setText("");
            }
        });

        listView = (ListView) view.findViewById(R.id.list_view);

        AllExpensesTask allExpensesTask = new AllExpensesTask();
        allExpensesTask.execute();
        return view;
    }

    private boolean isDeleted = false;

    @Override
    public boolean deleteExpense(Expense expense) {
        DeleteExpenseTask deleteExpenseTask = new DeleteExpenseTask();
        deleteExpenseTask.execute(expense);

        return isDeleted;
    }


    private class ExpenseInsertTask extends AsyncTask<Expense, Void, Expense> {

        @Override
        protected Expense doInBackground(Expense... params) {
            Expense expense = params[0];

            SQLiteDatabase database = expenseDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ExpenseDbHelper.ExpenseTable.DESCRIPTION.toString(), expense.getDescription());
            values.put(ExpenseDbHelper.ExpenseTable.PRICE.toString(), expense.getPrice());

            long resultId = database.insert(ExpenseDbHelper.ExpenseTable.class.getSimpleName(), null, values);

            if (resultId != -1) {
                expense.setId(resultId);
                return expense;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Expense expense) {
            if (expense != null) {
                mExpensesAdapter.add(expense);
            }
        }
    }

    private class AllExpensesTask extends AsyncTask<Void, Void, List<Expense>> {

        @Override
        protected List<Expense> doInBackground(Void... params) {
            List<Expense> resultList = new ArrayList<Expense>();

            SQLiteDatabase database = expenseDbHelper.getReadableDatabase();

            Cursor cursor = database.query(ExpenseDbHelper.ExpenseTable.class.getSimpleName(), null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(ExpenseDbHelper.ExpenseTable.ID.ordinal());
                String description = cursor.getString(ExpenseDbHelper.ExpenseTable.DESCRIPTION.ordinal());
                double price = cursor.getDouble(ExpenseDbHelper.ExpenseTable.PRICE.ordinal());

                Expense expense = new Expense(id, description, price);
                resultList.add(expense);
            }

            return resultList;
        }

        @Override
        protected void onPostExecute(List<Expense> expenses) {
            mExpensesAdapter = new ExpensesAdapter(ExpensesListFragment.this, expenses);
            listView.setAdapter(mExpensesAdapter);
            mExpensesAdapter.notifyDataSetChanged();
        }
    }


    private class DeleteExpenseTask extends AsyncTask<Expense, Void, Expense>{

        @Override
        protected Expense doInBackground(Expense... params) {
            Expense expense = params[0];

            SQLiteDatabase database = expenseDbHelper.getWritableDatabase();

            int deleted = database.delete(ExpenseDbHelper.ExpenseTable.class.getSimpleName(), deleteWhereClause, new String[]{expense.getId() + ""});

            if (deleted == 1){
                return expense;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Expense expense) {
            if (expense != null) {
                mExpensesAdapter.remove(expense);
            }
        }
    }
}
