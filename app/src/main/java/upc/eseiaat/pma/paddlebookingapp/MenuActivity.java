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
import android.widget.Toast;

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

    DatabaseReference databaseReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseReservations = FirebaseDatabase.getInstance().getReference("reservations");

        ListView list = (ListView) findViewById(R.id.lista_reservas);
        FloatingActionButton btn_add_reservation = (FloatingActionButton) findViewById(R.id.btn_add_reservation);

        reservation_list = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservation_list);
        list.setAdapter(adapter);

        btn_add_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReservation();
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

    private void addReservation() {

        String hour = "15:00h";
        String day = "Monday";
        String month = "May";
        String id = databaseReservations.push().getKey();
        String user1_id = "Whatever";
        String user2_id = "Whatever";

        if (!hour.isEmpty() && !day.isEmpty() && !month.isEmpty()) {

            Reservations reservation = new Reservations(id, hour, day, month, user1_id, user2_id);
            databaseReservations.child(id).setValue(reservation);

            Toast.makeText(this, R.string.added_reservation, Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, R.string.missing_data, Toast.LENGTH_SHORT).show();
        }


    }
}