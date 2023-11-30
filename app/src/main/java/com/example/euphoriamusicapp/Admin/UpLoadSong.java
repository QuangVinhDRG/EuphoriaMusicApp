package com.example.euphoriamusicapp.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.adapter.PodcastAdapter;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.Podcast;
import com.example.euphoriamusicapp.data.TopicAndCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import com.example.euphoriamusicapp.R;

public class UpLoadSong extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri ;
    StorageReference mStorageref;
    StorageTask mUploadsTask ;
    DatabaseReference referenceSongs ;
    String songsCategory ;
    MediaMetadataRetriever metadataRetriever;
    byte [] art ;
    String title1, artist1;
    TextView title, artist,album;
    ImageView album_art ;
    int positon_ca = 1;
    Uri uri_image,uri_audio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_song);


        textViewImage = findViewById(R.id.textViewSongsFilesSelected);
        progressBar = findViewById(R.id.progressbar);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
        album = findViewById(R.id.album);
        album_art = findViewById(R.id.imageview);

        metadataRetriever = new MediaMetadataRetriever();
        referenceSongs = FirebaseDatabase.getInstance().getReference().child("songs");
        mStorageref = FirebaseStorage.getInstance().getReference().child("songs");

        Spinner spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);


        List <String> categories = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Catelory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    TopicAndCategory topicAndCategory = data.getValue(TopicAndCategory.class);
                    categories.add(topicAndCategory.getTopicAndCategoryName());
                    Log.d("TAG", "onDataChange: " + topicAndCategory.getTopicAndCategoryName());
                }
                ArrayAdapter <String> dataAdpter = new ArrayAdapter<>(UpLoadSong.this , android.R.layout.simple_spinner_item,categories);
                dataAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdpter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        positon_ca = i+1;
        songsCategory = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "Selected: "+ songsCategory, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public  void openAudioFiles (View v ){

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i,101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode  == RESULT_OK && data.getData() != null){

            audioUri = data.getData();
            String fileNames = getFileName(audioUri);
            textViewImage.setText(fileNames);
            metadataRetriever.setDataSource(this,audioUri);

            StorageReference storageReference = mStorageref.child(System.currentTimeMillis()+".png");
            art = metadataRetriever.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
            album_art.setImageBitmap(bitmap);
            UploadTask uploadTask = storageReference.putBytes(art);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpLoadSong.this, "Upload image lỗi", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uri_image = taskSnapshot.getUploadSessionUri();
                }
            });



            artist.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            title.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));

            artist1 = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            title1 = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);


        }


    }

    private  String getFileName(Uri uri){

        String result = null;
        if(uri.getScheme().equals("content")){

            Cursor cursor = getContentResolver().query(uri, null,null,null,null);
            try {
                if (cursor != null && cursor.moveToFirst()) {

                      result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            }
            finally {
                cursor.close();
            }
        }

        if(result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut != -1){
                result = result.substring(cut +1);

            }
        }
        return  result;
    }

    public  void  uploadFileTofirebase (View v ){
        if(textViewImage.equals("No file Selected")){
            Toast.makeText(this, "please selected an image!", Toast.LENGTH_SHORT).show();

        }
        else{
            if(mUploadsTask != null && mUploadsTask.isInProgress()){
                Toast.makeText(this, "songs uploads in allready progress!", Toast.LENGTH_SHORT).show();

            }else {
                uploadFiles();


            }
        }

    }



    private void uploadFiles() {

        if(audioUri != null){
            Toast.makeText(this, "uploads please wait!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            final  StorageReference storageReference = mStorageref.child(System.currentTimeMillis()+"."+getfileextension(audioUri));
            mUploadsTask = storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uri_audio = taskSnapshot.getUploadSessionUri();
                    BasicMusicInformation uploadSong = new BasicMusicInformation(positon_ca,title.getText().toString(),artist.getText().toString(),uri_image.toString(),uri_audio.toString());
                    String path = referenceSongs.push().getKey();
                    referenceSongs.child(path).setValue(uploadSong);
                            }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progess = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progess);

                }
            });

        }else {
            Toast.makeText(this, "No file Selected to uploads", Toast.LENGTH_SHORT).show();
        }





    }

    private  String getfileextension(Uri audioUri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(audioUri));

    }


}