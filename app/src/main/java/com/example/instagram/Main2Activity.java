package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.instagram.fragments.HomeFragment;
import com.example.instagram.fragments.NotificationFragment;
import com.example.instagram.fragments.ProfileFragment;
import com.example.instagram.fragments.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Context;

public class Main2Activity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView=findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectorFragment = new HomeFragment();
                        break;
                    case R.id.nav_search:
                        selectorFragment = new SearchFragment();
                        break;
                    case R.id.nav_add:
                        selectorFragment = null;
                        startActivity(new Intent(Main2Activity.this,PostActivity.class));
                        break;
                    case R.id.nav_heart:
                        selectorFragment = new NotificationFragment();
                        break;
                    case R.id.nav_profile:
                        selectorFragment = new ProfileFragment();
                        //getSharedPreferences("PROFILE",MODE_PRIVATE).edit().remove("profileId").apply();//here
                        break;

                }
                if(selectorFragment!= null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selectorFragment).commit();
                }
                return true;
            }
        });

        Bundle intent = getIntent().getExtras();
        if(intent != null){
            String profileId = intent.getString("publisherId");
            getSharedPreferences("PROFILE",MODE_PRIVATE).edit().putString("profileId",profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();

        }

    }
}
    /*
    private Button logout;
    private EditText nameET;
    private Button addBtn;
    private ListView listView;
    private long numOfChildren;
    private Uri imageUri;
    private static final int IMAGE_REQUEST =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        logout=(Button)findViewById(R.id.logoutBtn);
        nameET=(EditText)findViewById(R.id.nameTxt);
        addBtn=(Button)findViewById(R.id.addBtn);
        listView=(ListView)findViewById(R.id.listView);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Main2Activity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
                //finish();
            }
        });
       // FirebaseDatabase.getInstance().getReference().child("Testing").child("TestingChild").setValue("TestValue");
        /*
        HashMap<String , Object> map = new HashMap<>();
        map.put("Name","Ayman");
        map.put("Email","aymanshabana@outlook.com");
        FirebaseDatabase.getInstance().getReference().child("Testing").child("Testing hashmap").updateChildren(map);


        HashMap<String , Object> map = new HashMap<>();
        map.put("n1","Jeff");
        map.put("n2","Annie");
        map.put("n3","Britta");
        map.put("n4","Abed");
        FirebaseDatabase.getInstance().getReference().child("Community Characters").updateChildren(map);


        ArrayList<HashMap<String,Object>> listOfMaps = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("Name", "Ayman");
        map.put("Email", "aymanshabana@outlook.com");
        listOfMaps.add(map);
        map = new HashMap<>();
        map.put("Name", "Ashraf");
        map.put("Email", "ashraf@outlook.com");
        listOfMaps.add(map);
        map = new HashMap<>();
        map.put("Name", "Adham");
        map.put("Email", "Adham@outlook.com");
        listOfMaps.add(map);
        for(int i=0;i<3;i++) {
            FirebaseDatabase.getInstance().getReference().child("Information").child("Branch"+(i+1)).updateChildren(listOfMaps.get(i));
        }

         */
    /*
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String textName=nameET.getText().toString();
                if(textName.isEmpty()){
                    Toast.makeText(Main2Activity.this, "No name entered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Community Characters").child("n"+(numOfChildren+1)).setValue(textName);
                }


                openImage();
                //uploadImage();
            }
        });
        */

        /*
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Community Characters");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numOfChildren=dataSnapshot.getChildrenCount();
                list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         */
        /*
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numOfChildren=dataSnapshot.getChildrenCount();
                list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Information info = snapshot.getValue(Information.class);
                    String txt = info.getName()+" : "+info.getEmail();
                    list.add(txt);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        /*
        Map<String,Object> city = new HashMap<String, Object>();
        city.put("name","Heliopolis");
        city.put("state","Cairo");
        city.put("country","Egypt");
        firestore.collection("Cities").document("MEG").set(city)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Main2Activity.this, "Values added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Map<String,Object> data = new HashMap<String, Object>();
        data.put("capital",false);
        firestore.collection("Cities").document("MEG").set(data, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Main2Activity.this, "Values merged successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        Map<String,Object> data = new HashMap<String, Object>();
        data.put("name","Tokyo");
        data.put("capital","Japan");
        firestore.collection("Cities").add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Main2Activity.this, "Values added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

         */
        //DocumentReference documentReference= FirebaseFirestore.getInstance().collection("Cities").document("MEG");
        //documentReference.update("capital",true);
        /*
        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("countries").document("eg");
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Log.i("Document", documentSnapshot.getData().toString());
                    }
                    else
                    {
                        Log.i("Document", "No data");
                    }
                }
            }
        });


        FirebaseFirestore.getInstance().collection("countries").whereEqualTo("thirdworld",false)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Log.i("Document",doc.getId()+"->"+doc.getData());
                    }
                }

            }
        });


        FirebaseFirestore.getInstance().collection("countries").document("eg")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    }
                });

         */
/*
    }

    private void openImage() {
        Intent i = new Intent();
        i.setType("image/");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,IMAGE_REQUEST);
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        if(imageUri != null){
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=uri.toString();
                            Log.i("DownloadURL",url);
                            pd.dismiss();
                            Toast.makeText(Main2Activity.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            uploadImage();
        }
    }
}
*/