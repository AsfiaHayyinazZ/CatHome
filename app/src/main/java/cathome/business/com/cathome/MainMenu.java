package cathome.business.com.cathome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainMenu extends AppCompatActivity {

    private DatabaseReference kucingDatabase;
    private RecyclerView rv_kucing;
    FirebaseRecyclerAdapter<ModelKucing, KucingViewHolder> kucingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        kucingDatabase = FirebaseDatabase.getInstance().getReference("Kucing");

        rv_kucing = findViewById(R.id.rv_kucing);

        rv_kucing.setHasFixedSize(true);
        rv_kucing.setLayoutManager(new GridLayoutManager(this, 2));


        kucingAdapter = new FirebaseRecyclerAdapter<ModelKucing, KucingViewHolder>(
                ModelKucing.class,
                R.layout.item_kucing,
                KucingViewHolder.class,
                kucingDatabase
        ) {
            @Override
            protected void populateViewHolder(KucingViewHolder kucingViewHolder, ModelKucing modelKucing, int i) {
                kucingViewHolder.setDetails(getApplicationContext(), modelKucing.getNama(), modelKucing.getDeskripsi(),
                        modelKucing.getGambar());

                kucingViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(MainMenu.this,DetailKucing.class);
                        intent.putExtra("NamaKucing",kucingAdapter.getRef(position).getKey());

                        startActivity(intent);
                    }
                });

            }
        };
        rv_kucing.setAdapter(kucingAdapter);
    }

    public static class KucingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public KucingViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            view.setOnClickListener(this);

        }

        public void setDetails(Context context, String nama, String deskripsi, String gambar) {

            TextView txt_nama = view.findViewById(R.id.txt_nama_kucing);
            TextView txt_deskripsi = view.findViewById(R.id.txt_deskripsi_kucing);
            ImageView img_gambar = view.findViewById(R.id.img_kucing);

            txt_nama.setText(nama);
            txt_deskripsi.setText(deskripsi);

            Glide.with(context).load(gambar).into(img_gambar);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
