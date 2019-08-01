package cathome.business.com.cathome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailKucing extends AppCompatActivity {

    public TextView txt_nama, txt_deskripsi;
    public ImageView img_kucing;
    public FirebaseDatabase database;
    public DatabaseReference kucing;
    String kucingId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kucing);

        database = FirebaseDatabase.getInstance();
        kucing = database.getReference("Kucing");
        txt_nama = findViewById(R.id.txt_detail_nama);
        txt_deskripsi = findViewById(R.id.txt_detail_deskripsi);
        img_kucing = findViewById(R.id.img_detail_kucing);

        if (getIntent() != null) {
            kucingId = getIntent().getStringExtra("NamaKucing");
            if (!kucingId.isEmpty()) {
                getDetailKucing(kucingId);
            }
        }

    }

    private void getDetailKucing(String kucingId) {
        kucing.child(kucingId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ModelKucing modelKucing = dataSnapshot.getValue(ModelKucing.class);
                txt_nama.setText(modelKucing.getNama());
                txt_deskripsi.setText(modelKucing.getDeskripsi());

                Glide.with(getBaseContext()).load(modelKucing.getGambar()).into(img_kucing);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
