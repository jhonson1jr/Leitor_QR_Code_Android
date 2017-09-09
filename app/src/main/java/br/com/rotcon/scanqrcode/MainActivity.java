package br.com.rotcon.scanqrcode;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button btnScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button)findViewById(R.id.btnScan); //instanciando
        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES); //ler qrcodes
                integrator.setPrompt("Camera Scan"); //texto q vai aparecer na tela
                integrator.setCameraId(0); //0 camera traseira; 1 camera frontal
                integrator.setBeepEnabled(true); //faz bip ao ler
                integrator.setOrientationLocked(false);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.initiateScan(); //startando
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() != null){
                alerta(result.getContents());
            }else{
                alerta("Scan Cancelado");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void alerta(String mensagem) { //exibir mensagem na tela
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
