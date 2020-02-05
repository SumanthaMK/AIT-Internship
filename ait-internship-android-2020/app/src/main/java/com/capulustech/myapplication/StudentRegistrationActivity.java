package com.capulustech.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.Locale;

public class StudentRegistrationActivity extends AppCompatActivity
{
    Student student;
    TextToSpeech textToSpeech;
    boolean is_speaking;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);




        final Button b4=findViewById(R.id.stopaudio);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.stop();
            }
        });

        Button playss=findViewById(R.id.playmusic);//To Play Music
        playss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer=MediaPlayer.create(StudentRegistrationActivity.this,R.raw.music);
                mediaPlayer.start();

            }
        });

        final Button b2=findViewById(R.id.videoCapture);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dexter.withActivity(StudentRegistrationActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                videoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                                startActivityForResult(videoIntent, 1111);


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response)
                            {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                           PermissionToken token)
                            {

                            }
                        })
                        .check();

            }
        });
        final Button bt1=findViewById(R.id.speakBtn);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText nameET = findViewById(R.id.nameET);
                final EditText usnET = findViewById(R.id.usnET);
                final EditText branchET = findViewById(R.id.branchET);
                //final EditText sectionET = findViewById(R.id.sectionET);
                final EditText mobileNumberET = findViewById(R.id.mobileET);
                final Spinner sectionSpn= findViewById(R.id.sectionSpn);
                final Spinner branchSpn=findViewById(R.id.branchSpn);

                Button registerBtn = findViewById(R.id.registerBtn);

                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.demo);
                registerBtn.setAnimation(animation);
                b2.setAnimation(animation);
                b4.setAnimation(animation);
                bt1.setAnimation(animation);



                String name = nameET.getText().toString();
                //  String branch = branchET.getText().toString();
                String usn = usnET.getText().toString();
                String mobileNumber = mobileNumberET.getText().toString();
                // String section = sectionET.getText().toString();
                String section = sectionSpn.getSelectedItem().toString();
                String branch = branchSpn.getSelectedItem().toString();

                student = new Student();
                student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobileNumber;
                student.section = section;

                String message="Hello This is My Details\n"
                        +"Name: " + student.name
                        +"\nBranch: " +student.branch
                        +"\nUSN : " +student.usn
                        +"\nSection : " +student.section
                        +"\nMobile No : " +student.mobileNumber;
                speak(message);

            }
        });

        final ImageView imgs=findViewById(R.id.ivRegLogo);
        imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(StudentRegistrationActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener()
                        {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra("android.intent.extras.CAMERA_FACING",1);
                                startActivityForResult(intent,6789);//Camera Open Codes

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response)
                            {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                           PermissionToken token)
                            {

                            }
                        })
                        .check();

            }
        });
        Dexter.withActivity(StudentRegistrationActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        FusedLocation fusedLocation =
                                new FusedLocation(StudentRegistrationActivity.this,
                                        false);
                        fusedLocation.onLocReceived(new MyLocListener()
                        {
                            @Override
                            public void onLocReceived(final LatLng latLng)
                            {
                                //Toast.makeText(StudentRegistrationActivity.this,
                                  //      "" + latLng.latitude, Toast.LENGTH_SHORT).show();
                               // Toast.makeText(StudentRegistrationActivity.this, "Lattitude" +latLng.latitude + ","+latLng.longitude,
                                 //       Toast.LENGTH_LONG).show();
                                //string x=latLng.longitude;
                                TextView tv=findViewById(R.id.langit);
                               // TextView tw=findViewById(R.id.latit);
                              final   EditText el=findViewById(R.id.lots);
                                final   EditText elt=findViewById(R.id.longs);
                             double x=latLng.longitude;
                             String y=x+"";
                                tv.setText(y);
                                double r=latLng.latitude;
                                String s=r+"";
                                //tw.setText(s);
                                el.setText("Latitude: "+s);
                                elt.setText("Longitude: "+y);

                                Button shareBtn = findViewById(R.id.shareBtn);
                                shareBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final EditText nameET = findViewById(R.id.nameET);
                                        final EditText usnET = findViewById(R.id.usnET);
                                        final EditText branchET = findViewById(R.id.branchET);
                                        //final EditText sectionET = findViewById(R.id.sectionET);
                                        final EditText mobileNumberET = findViewById(R.id.mobileET);
                                        final Spinner sectionSpn= findViewById(R.id.sectionSpn);
                                        final Spinner branchSpn=findViewById(R.id.branchSpn);

                                        Button registerBtn = findViewById(R.id.registerBtn);


                                        String name = nameET.getText().toString();
                                        //  String branch = branchET.getText().toString();
                                        String usn = usnET.getText().toString();
                                        String mobileNumber = mobileNumberET.getText().toString();
                                        // String section = sectionET.getText().toString();
                                        String section = sectionSpn.getSelectedItem().toString();
                                        String branch = branchSpn.getSelectedItem().toString();

                                        student = new Student();
                                        student.name = name;
                                        student.branch = branch;
                                        student.usn = usn;
                                        student.mobileNumber = mobileNumber;
                                        student.section = section;



                                        String message="Hello This is My Details\n"
                                                +"Name: " + student.name
                                                +"\nBranch: " +student.branch
                                                +"\nUSN : " +student.usn
                                                +"\nSection : " +student.section
                                                +"\nMobile No : " +student.mobileNumber
                                                +"\nThis is My Location: " +"http://maps.google.com/maps?q=" +
                                                latLng.latitude + "," + latLng.longitude;
                                        Intent sendintent=new Intent();
                                        sendintent.setAction(Intent.ACTION_SEND);
                                        sendintent.putExtra(Intent.EXTRA_TEXT,message);
                                        sendintent.setType("text/plain");
                                        startActivity(sendintent);

                                    }
                                });

                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(StudentRegistrationActivity.this, "Lattitude",
                                                Toast.LENGTH_LONG).show();

                                        Uri uri = Uri.parse("http://maps.google.com/maps?q=" +
                                                latLng.latitude + "," + latLng.longitude);

                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);

                                    }
                                });



                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token)
                    {

                    }
                })
                .check();




        final EditText nameET = findViewById(R.id.nameET);
        final EditText usnET = findViewById(R.id.usnET);
        final EditText branchET = findViewById(R.id.branchET);
        //final EditText sectionET = findViewById(R.id.sectionET);
        final EditText mobileNumberET = findViewById(R.id.mobileET);
        final Spinner sectionSpn= findViewById(R.id.sectionSpn);
        final Spinner branchSpn=findViewById(R.id.branchSpn);

        Button registerBtn = findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = nameET.getText().toString();
              //  String branch = branchET.getText().toString();
                String usn = usnET.getText().toString();
                String mobileNumber = mobileNumberET.getText().toString();
               // String section = sectionET.getText().toString();
                String section = sectionSpn.getSelectedItem().toString();
                String branch = branchSpn.getSelectedItem().toString();

                student = new Student();
               student.name = name;
                student.branch = branch;
                student.usn = usn;
                student.mobileNumber = mobileNumber;
                student.section = section;

                student.addStudent(StudentRegistrationActivity.this,student);
                Toast.makeText(StudentRegistrationActivity.this,"Student Added Successfully",Toast.LENGTH_SHORT).show();



                /*
                Intent intent = new Intent();
                intent.putExtra("student", student);
                startActivity(intent);
                */

                //in StudentDetailsActivity
                //Student student1 = (Student) getIntent().getSerializableExtra("student");


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==6789 && resultCode==RESULT_OK)
        {
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            ImageView iv=findViewById(R.id.ivRegLogo);
            iv.setImageBitmap(bitmap);
        }
        else if (requestCode == 1111 && resultCode == RESULT_OK)
        {
            Uri videoUri = data.getData();
            VideoView videoView = findViewById(R.id.videoView);
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }
    public void speak(final String message)
    {


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate(1f);
                    textToSpeech.setPitch(1f);
                    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.settings)
        {
            Toast.makeText(this,"Settings Clicked",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.musics)
        {
            MediaPlayer mediaPlayer=MediaPlayer.create(StudentRegistrationActivity.this,R.raw.music);
            mediaPlayer.start();
        }
        else if (id==R.id.logout)
        {
            showAlertDialog();
        }
        else if(id==R.id.slists)
        {
            Intent intentss=new Intent(this,StudentListActivity.class);
            startActivity(intentss);
        }
        else if (id==R.id.webs)
        {
            Intent intent=new Intent(this,WebActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void showAlertDialog()
    {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setTitle("Logout?");
        alertBuilder.setMessage("Do You Want To Logout?");
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String msgs="Logged Out Successfully";
                speak(msgs);
                finish();

            }
        });
        alertBuilder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Welcome Back to Student Registeration Activity",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Student Registeration Activity Closed",Toast.LENGTH_SHORT).show();
    }
}
