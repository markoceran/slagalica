package com.example.slagalica.fragments;

import static android.app.Activity.RESULT_OK;
import static com.example.slagalica.MainActivity.db;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slagalica.MainActivity;
import com.example.slagalica.R;
import com.example.slagalica.activities.RegisterActivity;
import com.example.slagalica.activities.StartUpActivity;
import com.example.slagalica.model.Korisnik;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.socket.client.Url;

public class IzmeniProfilFragment extends Fragment {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference collectionRef =  MainActivity.db.collection("korisnici");

    //DocumentReference documentRef = collectionRef.document("k2FdSWmo7q6VJR6xz48g");
    private Map<String, Object> data = new HashMap<>();
    private ImageView imgProfile;

    private static final int PICK_IMAGE_REQUEST = 1;

   // private ImageView profilePicture;

    private Uri imagePath1;
    private String imagePath;

    private static Korisnik logovaniKorisnik2;
    private EditText korisnickoIme;
    private EditText email;
    private EditText lozinka;


    private Button izmeniPodatke;

    private Button odjava;
    private Button btnUpload;

    private boolean isPasswordVisible = false;
    //private static String korisnikEmail;



    public static IzmeniProfilFragment newInstance(Korisnik korisnik){
        //korisnikEmail = someParam;
        Bundle args = new Bundle();
        args.putString("key","test");

        logovaniKorisnik2 = korisnik;

        IzmeniProfilFragment fragment = new IzmeniProfilFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_izmeni_profil, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */
        getData();

        korisnickoIme = view.findViewById(R.id.korisnickoIme);
        email = view.findViewById(R.id.email);
        lozinka = view.findViewById(R.id.lozinka);
        //profilePicture = view.findViewById(R.id.avatar);
        odjava = view.findViewById(R.id.logout);
        izmeniPodatke = view.findViewById(R.id.izmeniPodatke);
        imgProfile = view.findViewById(R.id.avatar);
        btnUpload = view.findViewById(R.id.btnUploadImage);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProfilData();
        setOnClickListener();
    }

    private void setProfilData(){

        email.setText(logovaniKorisnik2.getEmail());
        korisnickoIme.setText(logovaniKorisnik2.getKorisnickoIme());
        lozinka.setText(logovaniKorisnik2.getsifra());

        String base64Image = logovaniKorisnik2.getProfilePicture();
        if (!TextUtils.isEmpty(base64Image)) {
            // Decode the Base64 encoded string to a byte array
            byte[] imageData = Base64.decode(base64Image, Base64.DEFAULT);

            // Convert the byte array to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            // Wait for the ImageView to be measured
            imgProfile.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Ensure this listener is only called once
                    imgProfile.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // Scale the bitmap to match the size of the ImageView
                    int desiredWidth = imgProfile.getWidth();
                    int desiredHeight = imgProfile.getHeight();
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true);

                    // Set the scaled bitmap as the profile picture in the ImageView
                    imgProfile.setImageBitmap(scaledBitmap);
                }
            });
        } else {
            Log.d("ProfilePicture", "No profile picture available");
        }

    }

    public void getData() {
        MainActivity.db.collection("korisnici")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Get the document data and convert it to a map
                        Map<String, Object> documentData = document.getData();

                        // Extract the 'korisnickoIme' field as the key
                        String korisnickoIme = (String) documentData.get("korisnickoIme");

                        // Add the document data to the 'data' map using 'korisnickoIme' as the key
                        data.put(korisnickoIme, documentData);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                });
    }
    private void setOnClickListener(){

        lozinka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPasswordVisible) {
                    lozinka.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordVisible = false;
                } else {
                    lozinka.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordVisible = true;
                }

            }
        });

        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), StartUpActivity.class));
            }
        });

        izmeniPodatke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Retrieve the updated values from EditText views
                    String updatedEmail = email.getText().toString();
                    String updatedKorisnickoIme = korisnickoIme.getText().toString();
                    String updatedLozinka = lozinka.getText().toString();

                logovaniKorisnik2.setEmail(updatedEmail);
                logovaniKorisnik2.setKorisnickoIme(updatedKorisnickoIme);
                logovaniKorisnik2.setsifra(updatedLozinka);

                Map<String, Object> dataUser = new HashMap<>();
                dataUser.put("korisnickoIme", updatedKorisnickoIme);
                dataUser.put("email", updatedEmail);
                dataUser.put("sifra", updatedLozinka);
                String encodedEmail = encodeString(logovaniKorisnik2.getEmail());

                // Update the profile picture if a new one is selected
                if (imagePath1 != null) {
                    try {
                        InputStream inputStream = requireActivity().getContentResolver().openInputStream(imagePath1);

                        byte[] imageData;
                        int bufferSize = 1024;
                        byte[] buffer = new byte[bufferSize];
                        int bytesRead;
                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }

                        inputStream.close();
                        imageData = output.toByteArray();

                        String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);

                        // Update the profile picture in the dataUser object
                        dataUser.put("profilePicture", base64Image);

                        // TODO: Save the dataUser object to Firebase Realtime Database
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Image upload failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
/*
                // Update the profile picture if a new one is selected
                if (imagePath1 != null) {
                    byte[] imageData;
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int bytesRead;
                    ByteArrayOutputStream output = new ByteArrayOutputStream();

                    try {
                        InputStream inputStream = requireActivity().getContentResolver().openInputStream(imagePath1);

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }

                        inputStream.close();
                        imageData = output.toByteArray();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Image upload failed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
                    dataUser.put("profilePicture", base64Image);
                }*/


                CollectionReference korisniciRef = db.collection("korisnici");
                Query query = korisniciRef.whereEqualTo("email", logovaniKorisnik2.getEmail());

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                // Get the first document from the query result
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                DocumentReference userRef = korisniciRef.document(documentSnapshot.getId());
                                userRef.update(dataUser)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                email.setText(updatedEmail);
                                                korisnickoIme.setText(updatedKorisnickoIme);
                                                lozinka.setText(updatedLozinka);
                                                Toast.makeText(getActivity(), "Uspešno ste izmenili podatke", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Data error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                // Document with the email not found
                                Toast.makeText(getActivity(), "Document with the email not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error occurred while querying the collection
                            Toast.makeText(getActivity(), "Error querying collection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

/*
                    // Update the values in Firebase
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("korisnici").child(encodedEmail);
                    userRef.child("email").setValue(updatedEmail);
                    userRef.child("korisnickoIme").setValue(updatedKorisnickoIme);
                    userRef.child("sifra").setValue(updatedLozinka);*/
                }
            });


                /*data.put("korisnickoIme", updatedKorisnickoIme);
                data.put("email", updatedEmail);
                data.put("sifra", updatedLozinka);

               // DocumentReference userRef = db.collection("korisnici").document(logovaniKorisnik2.getKorisnickoIme());
                collectionRef.add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Document added successfully
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error adding document
                            }
                        });


            }*/

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            uploadImage();
            }
        });



    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Uploading...");
        //progressDialog.setCancelable(false);
        progressDialog.show();
/*
        FirebaseStorage.getInstance().getReference("images/" + UUID.randomUUID().toString()).putFile(imagePath1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                updateProfilePicture(task.getResult().toString());
                            }
                        }
                    });
                    Toast.makeText(getActivity(), "Image uploaded", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded" + (int) progress + "%");
            }
        });*/

        // Convert the image to a base64 encoded string
        String base64Image;

// Convert the image to a byte array
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(imagePath1);
            byte[] imageData = new byte[inputStream.available()];
            inputStream.read(imageData);
            inputStream.close();

            // Encode the byte array to base64
            base64Image = Base64.encodeToString(imageData, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Image upload failed", Toast.LENGTH_SHORT).show();
            return;
        }

// Upload the image to FirebaseFirestore
        Map<String, Object> data = new HashMap<>();
        data.put("profilePicture", base64Image);

        // Find the current user document based on their email
        CollectionReference korisniciRef = db.collection("korisnici");
        Query query = korisniciRef.whereEqualTo("email", logovaniKorisnik2.getEmail());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        // Get the first document from the query result
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        String userId = documentSnapshot.getId();

                        // Update the user document with the uploaded image data
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("korisnici")
                                .document(userId)
                                .update(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Image uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Image upload failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Document with the email not found
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Document with the email not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred while querying the collection
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error querying collection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
    private void updateProfilePicture(String url) {
        FirebaseDatabase.getInstance().getReference("korisnik/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profilePicture").setValue(url);
    }*/

    private String encodeString(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imagePath = selectedImageUri.toString();
                imagePath1 = data.getData();

                // Update the image view with the new image
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    imgProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Retrieve the actual URI of the photo
                Cursor cursor = null;
                try {
                    String[] proj = { MediaStore.Images.Media.DATA };
                    cursor = requireActivity().getContentResolver().query(selectedImageUri, proj, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        String actualUri = cursor.getString(column_index);
                        // Use the actualUri as needed
                        Log.d("Actual URI", actualUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
    }


}