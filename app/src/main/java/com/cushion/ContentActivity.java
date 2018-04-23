package com.cushion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class ContentActivity extends AppCompatActivity {
    private Button btn=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        btn=(Button)this.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ContentActivity.this,MainActivity.class);
                intent.putExtra("webHtml","main.html");
                startActivity(intent);
            }
        });
    }
}
