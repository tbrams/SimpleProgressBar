package android.brams.dk.simpleprogressbar;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TBR";
    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    private long fileSize = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        btnStartProgress = (Button) findViewById(R.id.btnStartProgress);
        btnStartProgress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // prepare for a progress bar dialog
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("File downloading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                //reset progress bar status
                progressBarStatus = 0;

                //reset filesize
                fileSize = 0;

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {

                            // process some tasks
                            progressBarStatus = doSomeTasks();

                            // your computer is too fast, sleep 1 second
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }

                        // ok, file is downloaded,
                        if (progressBarStatus >= 100) {

                            // sleep 2 seconds, so that you can see the 100%
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // close the progress bar dialog
                            progressBar.dismiss();
                        }
                    }
                }).start();
            }
        });



        Button btnStartProgress2 = (Button) findViewById(R.id.btnStartProgress2);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.inlinePB);

        btnStartProgress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: gra");

                //reset progress bar status
                progressBarStatus = 0;

                //reset filesize
                fileSize = 0;

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {

                            // process some tasks
                            progressBarStatus = doSomeTasks();

                            // your computer is too fast, sleep 1 second
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    pb.setProgress(progressBarStatus);
                                }
                            });
                        }

                        // ok, file is downloaded,
                        if (progressBarStatus >= 100) {

                            // sleep 2 seconds, so that you can see the 100%
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // reset the Progressbar
                            pb.setProgress(0);
                        }
                    }
                }).start();
            }
        });

    }



    // file download simulator... a really simple
    public int doSomeTasks() {

        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            }
            // ...add your own

        }

        return 100;

    }

}