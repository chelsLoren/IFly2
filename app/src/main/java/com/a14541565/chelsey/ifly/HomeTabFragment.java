package com.a14541565.chelsey.ifly;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Chelsey on 01/08/2017.
 */

public class HomeTabFragment extends Fragment{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Photo> arrayList = new ArrayList<>();

    EditText editText_Name, editText_Des, editText_Location;
    ImageView viewImage;
    Button btnSelectPhoto, btnUploadPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setHasFixedSize(true);
//        adapter = new RecyclerAdapter(arrayList);
//        recyclerView.setAdapter(adapter);

        editText_Name = (EditText) view.findViewById(R.id.editText_Name);
        editText_Des = (EditText) view.findViewById(R.id.editText_Des);
        editText_Location = (EditText) view.findViewById(R.id.editText_Location);

        btnSelectPhoto = (Button) view.findViewById(R.id.btnSelectPhoto);
        btnUploadPhoto = (Button) view.findViewById(R.id.btnUploadPhoto);
        viewImage = (ImageView) view.findViewById(R.id.viewImage);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload_photo(view);
                //readFromLocalStorage();
//                Fragment DBfragment = new DBTabFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, DBfragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });
        return view;
    }

//    public void upload_photo(View view){
//        String name = editText_Name.getText().toString();
//        String description = editText_Des.getText().toString();
//        String location = editText_Location.getText().toString();
//        saveToLocalStorage(name, description, location);
//
//        editText_Name.setText("");
//        editText_Des.setText("");
//        editText_Location.setText("");
//    }

//    private void readFromLocalStorage(){
//
//        arrayList.clear();
//        DbHelper dbHelper = new DbHelper(getActivity());
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//
//        Cursor cursor = dbHelper.readFromLocalDatabase(database);
//        while(cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex(DbPhoto.NAME));
//            String description = cursor.getString(cursor.getColumnIndex(DbPhoto.DESCRIPTION));
//            String location = cursor.getString(cursor.getColumnIndex(DbPhoto.LOCATION));
//            int sync_status = cursor.getInt(cursor.getColumnIndex(DbPhoto.SYNC_STATUS));
//            arrayList.add(new Photo(name, description, location, sync_status));
//        }
//
//        adapter.notifyDataSetChanged();
//        cursor.close();
//        dbHelper.close();
//    }

//    private void saveToLocalStorage(String name, String description, String location){
//        DbHelper dbHelper = new DbHelper(getActivity());
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//
//        if(checkNetworkConnection()){
//
//        }else{
//
//            dbHelper.saveToLocalDatabase(name, description, location, DbPhoto.SYNC_STATUS_FAILED, database);
//        }
//
//        readFromLocalStorage();
//        dbHelper.close();
//    }

//    public boolean checkNetworkConnection(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(viewImage.getContext());
        builder.setTitle("Add a Photo of a Bird!");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), ".jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals(".jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    viewImage.setImageBitmap(bitmap);

                    //Review the String path
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;

                    //Current Timestamp attached to the image
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }




}
