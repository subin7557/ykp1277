package kindnewspaper.a20110548.ac.kr.kindnewspaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by subin on 2016-06-02.
 */
public class make_splash extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}



