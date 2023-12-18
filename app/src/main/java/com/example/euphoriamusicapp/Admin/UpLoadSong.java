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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euphoriamusicapp.MainActivity;
import com.example.euphoriamusicapp.R;

import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.data.TopicAndCategory;
import com.example.euphoriamusicapp.fragment.HomeFragment;
import com.example.euphoriamusicapp.fragment.playlist.SongFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpLoadSong extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final  static  String IMAGE = "IMAGE";
    final  static  String AUDIO = "AUDIO";
    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri ;
    Button logout;
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
    String uri_image = "",uri_audio = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_song);

        logout = findViewById(R.id.logout);
        textViewImage = findViewById(R.id.textViewSongsFilesSelected);
        progressBar = findViewById(R.id.progressbar);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
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
                   if(topicAndCategory.getType() == HomeFragment.SONG){
                        categories.add(topicAndCategory.getTopicAndCategoryName());
                    }
                }
                ArrayAdapter <String> dataAdpter = new ArrayAdapter<>(UpLoadSong.this , android.R.layout.simple_spinner_item,categories);
                dataAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdpter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(UpLoadSong.this, MainActivity.class);
                startActivity(intent);
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


            art = metadataRetriever.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
            album_art.setImageBitmap(bitmap);
            uploadImage(art);

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
            StorageReference storageReference = mStorageref.child(System.currentTimeMillis()+"."+getfileextension(audioUri));
            mUploadsTask = storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    coppyuri(uri,AUDIO);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progess = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progess);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            })
            ;

        }else {
            Toast.makeText(this, "No file Selected to uploads", Toast.LENGTH_SHORT).show();
        }





    }

    private  String getfileextension(Uri audioUri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(audioUri));

    }
    private void uploadImage(byte[] imageData) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorageref = storage.getReference();
        if (imageData != null) {
            // Tạo một StorageReference với tên file duy nhất (ví dụ: timestamp)
            StorageReference fileReference = mStorageref.child(System.currentTimeMillis() + ".jpg");

            // Tạo một ByteArrayOutputStream để chuyển đổi byte array thành dạng dữ liệu cần thiết để tải lên

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(imageData, 0, imageData.length);

            // Tải ảnh lên Firebase Storage
            fileReference.putBytes(baos.toByteArray())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    coppyuri(uri,IMAGE);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xử lý khi không thể lấy đường dẫn (URL)
                                            Toast.makeText(UpLoadSong.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Toast.makeText(UpLoadSong.this, "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi tải lên thất bại
                            Toast.makeText(UpLoadSong.this, "Lỗi khi tải ảnh lên: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Không tìm thấy ảnh để tải lên", Toast.LENGTH_SHORT).show();
        }
    }

    private void coppyuri( Uri uri,String type) {
        if(type.equals(IMAGE)){
            uri_image = uri.toString();
        }else{
            uri_audio = uri.toString();
        }
       if( uri_audio != "" &&  uri_image != ""){
           List<MusicAndPodcast> listSong = new ArrayList<>();
           FirebaseDatabase   firebaseDatabase = FirebaseDatabase.getInstance();
           DatabaseReference databaseReference = firebaseDatabase.getReference();
           databaseReference.child("songs").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if(listSong != null){
                       listSong.clear();
                   }
                   for (DataSnapshot data: snapshot.getChildren()) {
                       MusicAndPodcast song = data.getValue(MusicAndPodcast.class);
                       listSong.add(song);
                   }
                   putDataToRealTimeDatabase(listSong.size() + 1,uri_image,uri_audio);

               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {
               }
           });

       }
    }

    private void putDataToRealTimeDatabase(int size,String uri_image1, String uri_audio1) {
        MusicAndPodcast uploadSong = new MusicAndPodcast(positon_ca,title.getText().toString(),artist.getText().toString(),uri_image1,uri_audio1,false,true,0,1);
        referenceSongs.child(String.valueOf(size)).setValue(uploadSong);
        FirebaseDatabase   firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference data =  firebaseDatabase.getReference("songs");
        HashMap<String, Object> result = new HashMap<>();
        result.put("latest", true);
        data.child(String.valueOf(size-1)).updateChildren(result);
        uri_audio = "" ;
        uri_image = "";
    }
}