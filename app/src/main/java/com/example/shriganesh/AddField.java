package com.example.shriganesh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddField extends AppCompatActivity {
@Override
    public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_field);

    Button passDataTargetReturnDataButton = findViewById(R.id.done);
    passDataTargetReturnDataButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            EditText editTextFieldName = findViewById(R.id.editTextFieldName);
            String fieldName = editTextFieldName.getText().toString();
            Intent intent = new Intent(AddField.this,MainActivity.class);
            intent.putExtra("message_return", fieldName);
            setResult(RESULT_OK,intent);
            finish();
        }
    });
}

}
