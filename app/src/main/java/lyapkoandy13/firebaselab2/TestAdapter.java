package lyapkoandy13.firebaselab2;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lyapkoandy13 on 16.12.16.
 */

public class TestAdapter extends ArrayAdapter {

    // declaring our ArrayList of items
    private ArrayList<Test> tests;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public TestAdapter(Context context, int textViewResourceId, ArrayList<Test> tests) {
        super(context, textViewResourceId, tests);
        this.tests = tests;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Test i = tests.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView tt = (TextView) v.findViewById(R.id.itemTextView);
            final TextView button1 = (TextView) v.findViewById(R.id.testButton1);
            final TextView button2 = (TextView) v.findViewById(R.id.testButton2);
            final TextView button3 = (TextView) v.findViewById(R.id.testButton3);
            final TextView button4 = (TextView) v.findViewById(R.id.testButton4);
            final ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (tt != null){
                tt.setText(tests.get(position).getQuestion());
            }

//            tt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(button1.getVisibility() == View.VISIBLE){
//                        button1.setVisibility(View.GONE);
//                        button2.setVisibility(View.GONE);
//                        button3.setVisibility(View.GONE);
//                        button4.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
//                    } else {
//                        button1.setVisibility(View.VISIBLE);
//                        button2.setVisibility(View.VISIBLE);
//                        button3.setVisibility(View.VISIBLE);
//                        button4.setVisibility(View.VISIBLE);
//                        imageView.setVisibility(View.VISIBLE);
//                    }
//                }
//            });

            ArrayList<String> variants = new ArrayList<>();
            variants.add(tests.get(position).getRight_var());
            variants.add(tests.get(position).getVar1());
            variants.add(tests.get(position).getVar2());
            variants.add(tests.get(position).getVar3());

            boolean isThereRight = false;
            if (button1 != null){
                int random = new Random().nextInt(4);
                button1.setText(variants.get(random));
                variants.remove(random);
                if(random == 0) {button1.setTag(1); isThereRight = true;} else {button1.setTag(0);};
            }
            if (button2 != null){
                int random = new Random().nextInt(3);
                button2.setText(variants.get(random));
                variants.remove(random);
                if(random == 0) {button2.setTag(1); isThereRight = true;} else {button2.setTag(0);};
            }
            if (button3 != null){
                int random = new Random().nextInt(2);
                button3.setText(variants.get(random));
                variants.remove(random);
                if(random == 0) {button3.setTag(1); isThereRight = true;} else {button3.setTag(0);};
            }
            if (button4 != null){
                button4.setText(variants.get(0));
                if(!isThereRight){ button4.setTag(1);} else { button4.setTag(0);};
            }

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if((Integer) view.getTag() == 0){
                        view.setBackgroundColor(Color.RED);
                        button1.setEnabled(false);
                        button2.setEnabled(false);
                        button3.setEnabled(false);
                        button4.setEnabled(false);
                    } else {
                        view.setBackgroundColor(Color.GREEN);
                        button1.setEnabled(false);
                        button2.setEnabled(false);
                        button3.setEnabled(false);
                        button4.setEnabled(false);
                    }
                }
            };

            button1.setOnClickListener(onClickListener);
            button2.setOnClickListener(onClickListener);
            button3.setOnClickListener(onClickListener);
            button4.setOnClickListener(onClickListener);

            StorageReference imageReference = FirebaseStorage.getInstance().getReference().child("photos/"+tests.get(position).getImage_url());

            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build();
            mFirebaseRemoteConfig.setConfigSettings(configSettings);
            mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mFirebaseRemoteConfig.activateFetched();
                        Log.e("FETCH", String.valueOf(task.isSuccessful()));
                    }
                }
            });

            String show_images = mFirebaseRemoteConfig.getString("show_images");
            Log.e("SHOW", show_images.toString());

            try {
                if(show_images.equals("1")) {
                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(getContext()).load(uri).fit().centerCrop().into(imageView);
                            imageView.setVisibility(View.VISIBLE);
                        }
                    });

                } else {

                    imageView.setVisibility(View.GONE);

                }
            } catch (Exception e) {

            }

        }

        // the view must be returned to our activity
        return v;

    }




}
