package com.example.aplicatiemanagementfilme.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplicatiemanagementfilme.MainActivity;
import com.example.aplicatiemanagementfilme.R;
import com.example.aplicatiemanagementfilme.WatchListDetailsActivity;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.database.model.WatchList;
import com.example.aplicatiemanagementfilme.database.service.WatchListService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WatchListFragment extends Fragment {

    public static final String WATCH_LIST_INFORMATION_KEY = "WATCH_LIST_INFORMATION_KEY";
    private static ListView lvWatchList;
    private FloatingActionButton fabAddWatchList;
    private WatchListService watchListService;

    public static List<WatchList> watchListArray = new ArrayList<>();

    public WatchListFragment() {
        // Required empty public constructor
    }

    public static WatchListFragment newInstance() {
        WatchListFragment fragment = new WatchListFragment();
        Bundle args = new Bundle();
        //args.putSerializable("ceva", (Serializable) currentUserAccount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watch_list, container, false);


        // Initializare componente
        initComponents(view);

        // Adaugare functio onClick pe fab
        fabAddWatchList.setOnClickListener(onClickAddWatchListListener(view));

        return view;
    }


    // Functii
    // Initializare componente
    private void initComponents(View view) {
        // Components
        lvWatchList = view.findViewById(R.id.lv_watchList);
        fabAddWatchList = view.findViewById(R.id.fab_add_watchList);

        // Watch List service
        watchListService = new WatchListService(view.getContext());

        // Adaugare adapter
        addListViewAdapter();

        // Preluare watch list array din db
        getWatchListArrayFromDb();

        // Adaugare eveniment click pe obiectele din lv
        lvWatchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), WatchListDetailsActivity.class);
                intent.putExtra(WATCH_LIST_INFORMATION_KEY, (Serializable) watchListArray.get(position));
                startActivity(intent);
            }
        });
    }

    // onClick pentru adaugare watch list
    private View.OnClickListener onClickAddWatchListListener(View view) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAddWatchList(view);
            }
        };
    }


    // Adaugare adapter
    private void addListViewAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                watchListArray);
        lvWatchList.setAdapter(adapter);
    }

    // Notificare schimbare adapter
    public static void notifyInternalAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvWatchList.getAdapter();
        adapter.notifyDataSetChanged();
    }


    // Alert dialog pt adaugare watch list
    private void alertDialogAddWatchList(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Watch list name:");

        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", onClickDialogAddNewWL(input));

        builder.setNegativeButton("Cancel", onClickDialogCancelAddNewWL());

        builder.show();
    }

    // on click CANCEL dialog anulare adaugare nou wl
    private DialogInterface.OnClickListener onClickDialogCancelAddNewWL() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
    }

    // on click OK dialog adaugare nou wl
    private DialogInterface.OnClickListener onClickDialogAddNewWL(EditText input) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String watchListName = input.getText().toString();
                long userAccountId = MainActivity.currentUserAccount.getId();
                WatchList watchList = new WatchList(watchListName, userAccountId);

                watchListService.insert(watchList, callbackAddWLtoDb());
            }
        };
    }

    // Adaugare watchlist in db
    private Callback<WatchList> callbackAddWLtoDb() {
        return new Callback<WatchList>() {
            @Override
            public void runResultOnUiThread(WatchList result) {
                Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
                watchListArray.add(result);
                notifyInternalAdapter();
            }
        };
    }


    // Preluare watch list array din db
    private void getWatchListArrayFromDb() {
        watchListService.getWatchListsByUserAccountId(MainActivity.currentUserAccount.getId(), callbackGetAllWLfromDB());
    }

    // Extragere toate wl din db
    private Callback<List<WatchList>> callbackGetAllWLfromDB() {
        return new Callback<List<WatchList>>() {
            @Override
            public void runResultOnUiThread(List<WatchList> result) {
                watchListArray.clear();
                watchListArray.addAll(result);
                notifyInternalAdapter();
            }
        };
    }

}