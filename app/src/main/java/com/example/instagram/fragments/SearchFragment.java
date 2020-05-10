package com.example.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.Adapter.TagAdapter;
import com.example.instagram.Adapter.UserAdapter;
import com.example.instagram.Model.User;
import com.example.instagram.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> myUsers;
    private UserAdapter userAdapter;

    private RecyclerView recyclerViewTags;
    private List<String> myHashtags;
    private List<String> myHashtagsCount;
    private TagAdapter tagAdapter;

    private SocialAutoCompleteTextView searchBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewTags=view.findViewById(R.id.recyclerViewTags);
        recyclerViewTags.setHasFixedSize(true);
        recyclerViewTags.setLayoutManager(new LinearLayoutManager(getContext()));

        myHashtags=new ArrayList<>();
        myHashtagsCount= new ArrayList<>();
        tagAdapter = new TagAdapter(getContext(),myHashtags,myHashtagsCount);
        recyclerViewTags.setAdapter(tagAdapter);


        myUsers = new ArrayList<>();
        userAdapter= new UserAdapter(getContext(),myUsers,true);
        recyclerView.setAdapter(userAdapter);

        searchBar=view.findViewById(R.id.search_bar);

        readUsers();
        readTags();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;
    }

    private void readTags() {

        FirebaseDatabase.getInstance().getReference().child("Hashtags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myHashtags.clear();
                myHashtagsCount.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    myHashtags.add(snapshot.getKey());
                    myHashtagsCount.add(snapshot.getChildrenCount()+"");
                }
                tagAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MyUsers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(TextUtils.isEmpty(searchBar.getText().toString())){
                    myUsers.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        myUsers.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchUser(String s){
        Query query = FirebaseDatabase.getInstance().getReference().child("MyUsers").orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    myUsers.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void filter(String text){
        List<String> mySearchTags = new ArrayList<>();
        List<String> mySearchTagsCount = new ArrayList<>();

        for(String s : myHashtags){
            if(s.toLowerCase().contains(text.toLowerCase())){
                mySearchTags.add(s);
                mySearchTagsCount.add(myHashtagsCount.get(myHashtags.indexOf(s)));
            }
        }

        tagAdapter.filter(mySearchTags,mySearchTagsCount);


    }
}
