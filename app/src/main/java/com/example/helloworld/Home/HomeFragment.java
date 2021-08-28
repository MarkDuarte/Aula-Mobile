package com.example.helloworld.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;


import com.example.helloworld.R;
import com.example.helloworld.Utils.MainfeedListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    View view;
    private Context mContext;
    private ListView mainList;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private  ArrayList<Object> mediaList;
    private MainfeedListAdapter adapter;
    private ProgressBar pb;



    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_home,container,false);
         mainList = view.findViewById(R.id.mainListView);
         pb = view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mediaList = new ArrayList<>();
        myRef = database.getReference();

        addContent();
        adapter = new MainfeedListAdapter(Objects.requireNonNull(getActivity()), R.layout.layout_mainfeed_item_view, mediaList);
        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }


    private void addContent(){

            Query query = myRef.child(mContext.getString(R.string.node_following))
                    .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    mediaList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    }
                }
                @Override
                public void onCancelled( DatabaseError databaseError) { }
            });
    }










}
