package kindnewspaper.a20110548.ac.kr.kindnewspaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by subin on 2016-06-02.
 */
public class make_splash extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Toast.makeText(this,"hh된댱",Toast.LENGTH_SHORT).show();
            Thread.sleep(30000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}



