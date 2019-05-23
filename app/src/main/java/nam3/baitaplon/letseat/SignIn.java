package nam3.baitaplon.letseat;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class SignIn extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Tìm lại id giao diện của username, password, sign in
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //Khởi tạo Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Đọc từ database
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Kiểm tra xem username có tồn tại trong database không
                        //Nếu có tồn tại
                        if (dataSnapshot.child(edtUsername.getText().toString()).exists())
                        {
                            //Lấy thông tin user
                            User user = dataSnapshot.child(edtUsername.getText().toString()).getValue(User.class);
                            //Kiểm tra password
                            if (user.getPassword().equals(edtPassword.getText().toString()))
                            {
                                //Hiện thông báo đăng nhập thành công
                                Toast.makeText(SignIn.this, "Sign In successfully!", Toast.LENGTH_SHORT).show();
                                Intent home = new Intent(SignIn.this, Home.class);
                                startActivity(home);
                                finish();
                            }
                            else
                            {
                                //Hiện thông báo đăng nhập thất bại
                                Toast.makeText(SignIn.this, "Wrong password!", Toast.LENGTH_SHORT ).show();
                            }
                        }
                        //Nếu không thì hiện thông báo không tồn tại username
                        else
                        {
                            Toast.makeText(SignIn.this, "User does not exist!", Toast.LENGTH_SHORT ).show();
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
