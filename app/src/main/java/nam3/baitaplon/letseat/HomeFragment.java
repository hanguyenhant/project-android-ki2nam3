package nam3.baitaplon.letseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nam3.baitaplon.letseat.Model.Category;

public class HomeFragment extends Fragment {

    public final static String ID_EXTRA="nam3.baitaplon.letseat._ID";

    ListView list_categories = null;
    List<Category> model = null;
    CategoryAdapter adapter = null;

    FirebaseDatabase database;
    DatabaseReference table_category;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Tạo giao diện Home
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("myLog", "Home fragment!");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list_categories = (ListView)getView().findViewById(R.id.list_categories);

        model = new ArrayList<Category>();

        //Khởi tạo database để lấy dữ liệu từ Category
        database = FirebaseDatabase.getInstance();
        table_category = database.getReference("Category");

        table_category.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Đọc dữ liệu từ database
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    //Thêm vào model
                    model.add(category);
                }

                adapter = new CategoryAdapter();
                list_categories.setAdapter(adapter);
                //Lắng nghe sự kiện nhấn vào item trong list view
                list_categories.setOnItemClickListener(onListClick);
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
            //Đóng gói dữ liệu id vào intent
            Intent detailcategory = new Intent(getActivity(), DetailCategory.class);
            detailcategory.putExtra(ID_EXTRA, String.valueOf(id));
            // khởi động activity DetailCategory => hiển thị danh sách các món ăn thuộc category đó.
            startActivity(detailcategory);
        }
    };

    class CategoryAdapter extends ArrayAdapter<Category>
    {
        CategoryAdapter()
        {
            super(getActivity(), R.layout.row_category, model);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent)
        {
            View row = convertView;
            CategoryHolder holder = null;

            if (row == null)
            {
                LayoutInflater inflater = getLayoutInflater();

                row = inflater.inflate(R.layout.row_category, parent, false);
                holder = new CategoryHolder(row);
                row.setTag(holder);
            }
            else
            {
                holder = (CategoryHolder)row.getTag();
            }

            holder.populateFrom(model.get(position));
            return row;
        }
    }

    static class CategoryHolder
    {
        private ImageView imgImage = null;
        private TextView txtName = null;

        CategoryHolder(View row)
        {
            //Tìm lại id giao diện của row
            imgImage = (ImageView)row.findViewById(R.id.imgImage);
            txtName = (TextView)row.findViewById(R.id.txtName);
        }

        void populateFrom(Category category)
        {
            //Thiết lập giá trị cho giao diện tương ứng
            txtName.setText(category.getName());
            Picasso.get().load(category.getImage()).into(imgImage);
        }
    }
}
