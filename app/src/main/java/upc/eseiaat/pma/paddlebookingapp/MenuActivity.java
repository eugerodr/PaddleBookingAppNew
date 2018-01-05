package upc.eseiaat.pma.paddlebookingapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ArrayList<String> reservation_list;
    private ArrayAdapter adapter;
    private String data;
    private String player_1_data="Julia";
    private String player_2_data="Marta";
    private int pos;
    private int id=0;
    private FloatingActionButton btn_add_reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView list = (ListView) findViewById(R.id.lista_reservas);
        btn_add_reservation = (FloatingActionButton) findViewById(R.id.btn_add_reservation);

        reservation_list = new ArrayList<>();
        reservation_list.add("Martes, 5 de diciembre");
        reservation_list.add("Miércoles, 6 de diciembre");
        reservation_list.add("Jueves, 7 de diciembre");
        reservation_list.add("Viernes, 8 de diciembre");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservation_list);
        list.setAdapter(adapter);

        btn_add_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write a message to database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(String.format("Reservation %d", id));
                myRef.setValue(id);
                myRef.child(String.format("hora %d", id)).setValue(id);
                id++;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos=position;
                data = reservation_list.get(position);
                // (I)
                Intent intent = new Intent(getApplicationContext(), ViewReservationActivity.class);
                intent.putExtra("data",  data);
                intent.putExtra("player_1_data", player_1_data);
                intent.putExtra("player_2_data", player_2_data);
                startActivityForResult(intent, 0);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:

                if (resultCode==RESULT_OK) {
                    Boolean item_value = data.getBooleanExtra("cancel_item", false);

                    if (item_value) {
                        reservation_list.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                }
        }
    }
}
