package com.gnd.calificaprofesores;

/** Aqui presentamos el buscador de universidades **/

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.balysv.materialmenu.MaterialMenuView;
import com.gnd.calificaprofesores.MenuManager.MenuManager;
import com.gnd.calificaprofesores.NetworkHandler.GotUserExtraDataListener;
import com.gnd.calificaprofesores.NetworkHandler.SentUniDataListener;
import com.gnd.calificaprofesores.NetworkHandler.UserDataManager;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraData;
import com.gnd.calificaprofesores.NetworkHandler.UserExtraDataInstance;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.GotUniListener;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.SearchUniHandler;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.Adapter;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NoInfoData;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static androidx.test.InstrumentationRegistry.getContext;

/** activity_select_uni.xml **/

public class ActivitySelectUni extends AppCompatActivity {
    private TextInputEditText mUniInput;
    private RecyclerView mResultList;

    private SearchUniHandler searchUniHandler;
    private Adapter adapter;
    RecyclerView recyclerView;

    private List<UniData> ShownDataListed;
    private ProgressWheel progressWheel;
    private ImageView sadIcon;
    private UserDataManager userDataManager;
    private MenuManager menuManager;

    private static final String TAG = "Hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_uni);


        Intent intent = getIntent();
        ShownDataListed = new ArrayList<>();
        searchUniHandler = new SearchUniHandler();
        adapter = new Adapter();
        userDataManager = new UserDataManager("");

        /*** Cargamos widgets **/
        mUniInput = findViewById(R.id.courseInput2);
        recyclerView = findViewById(R.id.ResultList);

        sadIcon = findViewById(R.id.SadFace);
        progressWheel = findViewById(R.id.LoadingIcon);
        sadIcon.bringToFront();
        progressWheel.bringToFront();

        /** Recycler burocracia **/
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /** Menu manager **/
        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout));

        /** Eventos **/
        mUniInput.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View view,int keyCode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d(TAG, "Hello world");
                    SetLoading();
                    String searchText = mUniInput.getText().toString();

                    firebaseUniSearch(searchText);
                    return true;
                }
                return false;
            }
        });

        searchUniHandler.AddOnGetUniListener(new GotUniListener() {
            @Override
            public void onGotUni(Set<UniData> data) {
                if (data.isEmpty()){
                    SetLoaded();
                    adapter.clear();
                    adapter.AddElement(new NoInfoData(
                            "INSTITUCIÓN NO ENCONTRADA",
                            "AGREGAR INSTITUCIÓN",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            ActivitySelectUni.this,
                                            ActivityAddUni.class
                                    );
                                    startActivity(intent);
                                }
                            }
                    ));
                }else{
                    SetLoaded();
                    adapter.clear();
                    for (final UniData item : data) {
                        item.SetClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SetLoading();

                                selectUni(item.GetId(), item.GetUniShortName(), item.GetUniShownName());

                            }
                        });
                        item.SetType(18);
                        adapter.AddElement(item);
                    }
                }
                /// Convertimos el set a list



                adapter.notifyDataSetChanged();
            }
        });

        if (!intent.getBooleanExtra("forceSelect",false)) {
            if (!UserExtraDataInstance.getInstance().getUniId().equals("")){
                UserExtraData extraData = UserExtraDataInstance.getInstance();
                selectUni(
                        extraData.getUniId(),
                        extraData.getUniName(),
                        extraData.getUniCompleteName()
                );
            }else {

                userDataManager.listenForUserProfileData(null);
                userDataManager.setmGotUserExtraDataListener(new GotUserExtraDataListener() {
                    @Override
                    public void gotExtraData(UserExtraData extraData) {
                        if (!extraData.getUniId().equals("")) {
                            selectUni(
                                    extraData.getUniId(),
                                    extraData.getUniName(),
                                    extraData.getUniCompleteName()
                            );
                        }
                    }
                });
            }
        }

        searchUniHandler.Search("");

        menuManager = new MenuManager(
                this,
                (MaterialMenuView)findViewById(R.id.MaterialMenuButton),
                (DrawerLayout)findViewById(R.id.DrawerLayout)
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        menuManager.closeDrawer();
    }

    protected void selectUni(final String uniId, final String uniShortName, final String uniCompleteName){
        userDataManager.setUni(uniShortName, uniId, uniCompleteName);

        UserExtraDataInstance.getInstance()
                .setUniId(uniId);
        UserExtraDataInstance.getInstance()
                .setUniName(uniShortName);
        UserExtraDataInstance.getInstance()
                .setUniCompleteName(uniCompleteName);



        userDataManager.setSentUniDataListener(new SentUniDataListener() {
            @Override
            public void onSentUni() {
                Intent intent = new Intent(ActivitySelectUni.this, ActivitySearchCourse.class);
                /*intent.putExtra("Uni", uniId);
                intent.putExtra("UniName", uniShortName);*/


                startActivity(intent);

                SetLoaded();
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        });

        userDataManager.setUni(
                UserExtraDataInstance.getInstance().getUniName(),
                UserExtraDataInstance.getInstance().getUniId(),
                UserExtraDataInstance.getInstance().getUniCompleteName()
        );
    }
    /*private FirebaseRecyclerOptions<BasicListItem> SearchAndMakeList(Query firebaseSearchQuery){
        return new FirebaseRecyclerOptions.Builder<BasicListItem>()
                .setQuery(firebaseSearchQuery, new SnapshotParser<BasicListItem>(){
                    @NonNull
                    @Override
                    public BasicListItem parseSnapshot(DataSnapshot snapshot) {
                        String details = "";
                        for (final DataSnapshot postSnapshot : snapshot.child("CompleteName").getChildren()) {
                            details += (String)postSnapshot.getValue();
                            details += "   ";
                        }
                        return new BasicListItem((String)snapshot.child("Name").getValue(),
                                (String)snapshot.child("CompleteName").getValue() ,
                                Long.parseLong(snapshot.getKey()));
                    }
                })
                .build();
    }*/

    protected void firebaseUniSearch(String searchText){
        adapter.clear();
        searchUniHandler.Search(searchText.toLowerCase());

        /// hacemos queries por dos criterios y luego unimos
        /*Query firebaseSearchQuery1 = mUserDatabase
                .child("Facultades")
                .orderByChild("CompleteName")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")
                .limitToFirst(10);

        Query firebaseSearchQuery2 = mUserDatabase
                .child("Facultades")
                .orderByChild("Name")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")
                .limitToFirst(10);


        FirebaseRecyclerOptions<BasicListItem> options1 = SearchAndMakeList(firebaseSearchQuery1);
        FirebaseRecyclerOptions<BasicListItem> options2 = SearchAndMakeList(firebaseSearchQuery2);

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<BasicListItem, ListItemViewHolder>(options1) {
            @Override
            protected void onBindViewHolder(ListItemViewHolder holder, int position, BasicListItem model) {
                holder.setDetails(getApplicationContext(), model.getName(),model.getDetail());
                final Long uniId = model.getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivitySelectUni.this, ActivitySearchCourse.class);
                        intent.putExtra("Uni",uniId);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_list_element, parent, false);

                return new ListItemViewHolder(view);
            }
        };*/

        /*mResultList.setAdapter(adapter);
        adapter.startListening();*/


    }


    private void SetLoading(){
        progressWheel.setVisibility(View.VISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        ClearListItems();
    }
    private void SetLoaded(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.INVISIBLE);
        ClearListItems();
    }

    private void SetNoResults(){
        progressWheel.setVisibility(View.INVISIBLE);
        sadIcon.setVisibility(View.VISIBLE);
        ClearListItems();
    }
    /** para hacer que se borren las opciones mostradas **/
    private void ClearListItems(){
        ShownDataListed.clear();
        adapter.notifyDataSetChanged();
    }
}
