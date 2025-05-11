package sv.edu.ues.usoxml;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajuste para soportar pantallas con barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Varaibles de Referencia a los componentes del layout
        Button btcargar = findViewById(R.id.btCargar);
        Button btsalir = findViewById(R.id.btSalir);
        LinearLayout contenedor = findViewById(R.id.Departamentos);

        // Botón para cargar XML
        btcargar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                contenedor.removeAllViews();
                try {
                    AssetManager assetManager = getAssets();
                    InputStream inputStream = assetManager.open("departamentos.xml");

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(inputStream);
                    document.getDocumentElement().normalize();

                    NodeList nodeList = document.getElementsByTagName("departamento");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            String _id = element.getElementsByTagName("id").item(0).getTextContent();
                            String _nombre = element.getElementsByTagName("nombre").item(0).getTextContent();

                            TextView tvdepartamento = new TextView(getApplicationContext());
                            tvdepartamento.setText(_id + "  -  " + _nombre);
                            tvdepartamento.setPadding(20, 20, 10, 20);
                            tvdepartamento.setTextSize(16);
                            contenedor.addView(tvdepartamento);
                        }
                    }
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Botón para cerrar la app
        btsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salirApp();
            }
        });
    }

    // Metodo para cerrar la app
    private void salirApp() {
        finishAffinity(); // Cierra por completo la app
    }
}
