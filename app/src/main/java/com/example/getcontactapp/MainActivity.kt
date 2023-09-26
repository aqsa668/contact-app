package com.example.getcontactapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.getcontactapp.databinding.ActivityMainBinding
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.getcontactapp.ui.theme.GetContactAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var binder: ActivityMainBinding
    private lateinit var nameTextBox : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        nameTextBox = binder.nameTextBox
        val view = binder.root
        setContentView(view)


        binder.getContact.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            startActivityForResult(intent, 1)
        }


    }



    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val contactUri: Uri? = data?.data
            contactUri?.let {
                val contactCursor: Cursor? = contentResolver.query(
                    contactUri,
                    null,
                    null,
                    null,
                    null
                )

                contactCursor?.apply {
                    if (moveToFirst()) {
                        val contactName = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        binder.phoneTextBox.setText(contactName)
                        Log.d("Selected Contact", contactName)
                        // Process other contact details as needed
                    }
                    close()
                }
            }
        }
    }

}