package nam3.baitaplon.letseat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nam3.baitaplon.letseat.Model.Food;

public class DetailCategory extends AppCompatActivity {

    public final static String ID_EXTRA="nam3.baitaplon.letseat._ID";

    ListView list_foods = null;
    List<Food> model = null;
    FoodAdapter adapter = null;

    FirebaseDatabase database;
    DatabaseReference table_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);

        list_foods = (ListView)findViewById(R.id.list_foods);

        model = new ArrayList<Food>();

        //Khởi tạo database để lấy dữ liệu từ Category
        database = FirebaseDatabase.getInstance();
        table_food = database.getReference("Food");

        table_food.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Đọc dữ liệu từ database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d("myLog", " "+ getIntent().getExtras().getString(ID_EXTRA).toString());

                    Food food = postSnapshot.getValue(Food.class);
                    //Lấy ra những món ăn có cùng ID với category
                    if (food.getCategoryID().equals(getIntent().getExtras().getString(ID_EXTRA).toString()))
                    {
                        //Thêm vào model
                        model.add(food);
                    }

                }

                adapter = new FoodAdapter();
                list_foods.setAdapter(adapter);
                //Lắng nghe sự kiện nhấn vào item trong list view
                list_foods.setOnItemClickListener(onListClick);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Lỗi đọc dữ liệu
                Log.w("myLog", "Failed to read value.");
            }
        });
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Khi nhấn vào một item trong list view thì
            // khởi động activity DetailCategory => hiển thị danh sách các món ăn thuộc category đó.
            Intent detailcategory = new Intent(DetailCategory.this, DetailCategory.class);

            detailcategory.putExtra(ID_EXTRA, String.valueOf(id));
            startActivity(detailcategory);
        }
    };

    class FoodAdapter extends ArrayAdapter<Food>
    {
        FoodAdapter()
        {
            super(DetailCategory.this, R.layout.row_food, model);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent)
        {
            View row = convertView;
            FoodHolder holder = null;

            if (row == null)
            {
                LayoutInflater inflater = getLayoutInflater();

                row = inflater.inflate(R.layout.row_food, parent, false);
                holder = new FoodHolder(row);
                row.setTag(holder);
            }
            else
            {
                holder = (FoodHolder)row.getTag();
            }

            holder.populateFrom(model.get(position));
            return row;
        }
    }

    static class FoodHolder
    {
        private ImageView imgImage = null;
        private TextView txtName = null;

        FoodHolder(View row)
        {
            //Tìm lại id giao diện của row
            imgImage = (ImageView)row.findViewById(R.id.imgImage);
            txtName = (TextView)row.findViewById(R.id.txtName);
        }

        void populateFrom(Food food)
        {
            //Thiết lập giá trị cho giao diện tương ứng
            txtName.setText(food.getName());
            Picasso.get().load(food.getImage()).into(imgImage);
        }
    }
}
