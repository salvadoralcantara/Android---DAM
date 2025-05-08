package sv.edu.ues.dam_sharedPrefer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btguardar, btmonstrar, btsalir;
    private EditText etclave, etvalor;
    private TextView tvsalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializacion de los elementos de la interfaz
        btguardar = findViewById(R.id.btnGuardar);
        btmonstrar = findViewById(R.id.btnMostrar);
        btsalir = findViewById(R.id.btnSalir);
        etclave = findViewById(R.id.edtClave);
        etvalor = findViewById(R.id.edtValor);
        tvsalida = findViewById(R.id.tvSalida);

        // Guarda en SharedPreferences
        btguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences configuracion = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                String clave = etclave.getText().toString();
                String valor = etvalor.getText().toString();

                SharedPreferences.Editor editorpref = configuracion.edit();
                editorpref.putString(clave, valor);
                editorpref.apply();
                Toast.makeText(MainActivity.this, "Preferencias guardadas con éxito", Toast.LENGTH_LONG).show();
            }
        });

        // Mostrar valor guardado
        btmonstrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencias = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                String clave = etclave.getText().toString();
                String valor = preferencias.getString(clave, "No existen las preferencias");

                tvsalida.setText("Clave: " + clave + "\nValor: " + valor);
            }
        });

        // Salir de la aplicación
        btsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
