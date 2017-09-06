package com.example.user.ormliteapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NameAdapter.IMyRecyclerAdaptersCallBack {

    EditText etEntry;
    NameAdapter myRecyclerAdapters = null;
    DatabaseHelper helper;
    List<Person> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        etEntry = (EditText) findViewById(R.id.etentry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(MainActivity.this, layoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        helper = new DatabaseHelper(getApplicationContext());
        myRecyclerAdapters = new NameAdapter(list, this);
        recyclerView.setAdapter(myRecyclerAdapters);
        setDataToAdapter();
    }

    private void setDataToAdapter() {

        list = helper.GetData();
        myRecyclerAdapters.setFeedItemList(list);
        myRecyclerAdapters.notifyDataSetChanged();

    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void adddata(View v) {
        String strName = etEntry.getText().toString().trim();
        if (TextUtils.isEmpty(strName)) {
            showToast("Please Add your Name!!!");
            return;
        }

        Person person = new Person();
        person.setName(strName);

        helper.addData(person);

        showToast("Data Successfully Added");

        setDataToAdapter();

    }

    public void deletedata(View v) {

        list = helper.GetData();

        if (null != list && list.size() > 0) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Delete ?");
            alert.setMessage("Are you sure want to delete All data from Database");

            alert.setButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setButton2("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    helper.deleteAll();
                    etEntry.setText("");
                    showToast("Removed All Data!!!");

                    setDataToAdapter();
                }
            });
            alert.show();
        } else {
            showToast("No data found from the Database");
        }
    }


    @Override
    public void onClick(int position) {

    }
}
