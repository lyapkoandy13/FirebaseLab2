package lyapkoandy13.firebaselab2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddActivity extends AppCompatActivity {

    private DatabaseReference rootDbRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference textDbRef = rootDbRef.child("tests");
    final private int GALLERY_INTENT = 2;
    private StorageReference mStorage;
    private ProgressDialog progressBar;
    private String testKey;
    private EditText question;
    private EditText right_var;
    private EditText var1;
    private EditText var2;
    private EditText var3;
    private DatabaseReference newValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();

    }

    private void init(){
        mStorage = FirebaseStorage.getInstance().getReference();

        progressBar = new ProgressDialog(this);

        question = (EditText) findViewById(R.id.addQuestionEditText);
        right_var = (EditText) findViewById(R.id.addRightAnswerEditText);
        var1 = (EditText) findViewById(R.id.addAnswer2EditText);
        var2 = (EditText) findViewById(R.id.addAnswer3EditText);
        var3 = (EditText) findViewById(R.id.addAnswer4EditText);
//        Button selectImageButton = (Button) findViewById(R.id.addSelectImageButton);
//
//        selectImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//
//                intent.setType("image/");
//
//                startActivityForResult(intent, GALLERY_INTENT);
//             }
//        });

        Button addButton = (Button) findViewById(R.id.mainAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newValue = textDbRef.push();
                testKey = newValue.getKey();

                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/");

                startActivityForResult(intent, GALLERY_INTENT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            progressBar.setMessage("Uploading image");
            progressBar.show();

            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("photos/"+testKey);

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Upload Done!", Toast.LENGTH_LONG).show();
                    progressBar.dismiss();

                    Test test = new Test(String.valueOf(question.getText()), String.valueOf(right_var.getText()),
                            String.valueOf(var1.getText()), String.valueOf(var2.getText()),
                            String.valueOf(var3.getText()), String.valueOf(testKey));
                    newValue.setValue(test);
                    Toast.makeText(getApplicationContext(),"Added to Database", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't upload image, sorry :(", Toast.LENGTH_SHORT).show();

                    progressBar.dismiss();
                }
            });

        }
    }
}
