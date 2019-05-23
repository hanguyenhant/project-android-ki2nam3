package nam3.baitaplon.letseat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nam3.baitaplon.letseat.Model.User;

public class SignUp extends AppCompatActivity {

    EditText edtName, edtUsername, edtPassword;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Tìm lại id giao diện của name, username, password, sign up
        edtName = (EditText)findViewById(R.id.edtName);
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        //Khởi tạo Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Đọc từ database
                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Kiểm tra xem username có tồn tại trong database không
                        //Nếu đã tồn tại
                        if (dataSnapshot.child(edtUsername.getText().toString()).exists())
                        {
                            Toast.makeText(SignUp.this, "Username is exist!", Toast.LENGTH_SHORT ).show();
                        }
                        //Nếu chưa thì cho phép đăng ký => thêm user mới vào database
                        else
                        {
                            User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                            table_user.child(edtUsername.getText().toString()).setValue(user);

                            //Thông báo thành công và chuyển về kết thúc activity
                            Toast.makeText(SignUp.this, "Sign Up successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Lỗi đọc dữ liệu
                        Log.w("myLog", "Failed to read value.");
                    }
                });
            }
        });
    }
}
