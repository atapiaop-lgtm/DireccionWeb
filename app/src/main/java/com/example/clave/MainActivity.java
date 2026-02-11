package com.example.clave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextDireccion;
    private CheckBox checkBox;
    private Button buttonEnviar;
    private WebView webView;
    private LinearLayout layoutFormulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDireccion = findViewById(R.id.editTextDireccion);
        checkBox = findViewById(R.id.checkBox);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        webView = findViewById(R.id.webView);
        layoutFormulario = findViewById(R.id.layoutFormulario);

        Intent intent = getIntent();

        // Verifica si debe mostrar el WebView
        if (intent.getBooleanExtra("mostrarWeb", false)) {

            String direccion = intent.getStringExtra("direccion");

            layoutFormulario.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);

            if (!direccion.startsWith("http://") &&
                    !direccion.startsWith("https://")) {
                direccion = "https://" + direccion;
            }

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(direccion);

        } else {

            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String direccion = editTextDireccion.getText().toString().trim();

                    if (direccion.isEmpty()) {
                        Toast.makeText(MainActivity.this,
                                "Debe ingresar una dirección",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean marcada = checkBox.isChecked();
                    String estado = marcada ? "marcada" : "desmarcada";

                    Toast.makeText(MainActivity.this,
                            "Casilla " + estado +
                                    "\nDirección: " + direccion,
                            Toast.LENGTH_LONG).show();

                    // Reenvía la misma Activity
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("mostrarWeb", true);
                    intent.putExtra("direccion", direccion);
                    startActivity(intent);
                }
            });
        }
    }

    // Bloquea salida desde la segunda "ventana"
    @Override
    public void onBackPressed() {
        if (webView.getVisibility() == View.VISIBLE) {
            // No hace nada (bloquea botón atrás)
        } else {
            super.onBackPressed();
        }
    }
}