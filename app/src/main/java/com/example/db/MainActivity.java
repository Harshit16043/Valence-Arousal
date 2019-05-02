package com.example.db;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Math.log10;

public class MainActivity extends AppCompatActivity {


    private TextView t,valence;
    String positive[]={"happy","happiness","good","great","fine","relaxed","relax","relaxation","strong","stronger","strongest","powerful","power","better","best",
            "happier","happiest",
            "miracle","marvel","marvelous","gift","boon","love","loved","surprise","surprised","surprise","surprising","calm","calmness","inquisitive","interested","interesting",
            "curious","easygoing","amusing","amused","amusement","aroused",
            "astonished","astonishing","excited","exciting","excitement","enthusiasm","enthusiastic","delighted","delightful","bright","brighter","brightest","brighten","brightened",
            "pleased",
            "pleasing","glad","content","serene","ease","satisfied","satisfying","satisfaction","satisfactory","satisfactorily","fine","easy",
            "simple",
            "beautiful","smart","smartness","clever","cleverness","tender","tenderness","passionate",
            "passion","rousing","confidence","confident","boisterous","rowdy","cheerful","fun","funny","sweet","sweetness","amiable","impressed",
            "impressive","hope","sacred","holy","inspired","inspiring","quiet","soothing","leisurely","tranquil","merry","gay","joy","joyful",
            "joyous","light",
            "graceful","sprightly","fanciful","fancy","whimsical","humorous","humor","rich","richer","richest","richness","awesome","amazed","amazing",
            "fortunate","luck","lucky","festival","enjoy","exquisite","pretty","cool","kind","kindness","humble","kindly","humbly","peace",
            "solution","solved","solvable","honour","tasty","likable","liked","passed","comfort","comfortable","bittersweet","high","moral",
            "morality","pleasure","reliable","real","true","truth","positive","positivity","realistic","optimism","optimistic","relief",
            "relieved","magnificent","magnificence","cute","adorable","nice","original","helping","supporting","fair","lovely","faith","faithful","helpful","safe","safety",
            "saves","saved","able","ability","possible","possibility","win","winner","wins","gain","opportunity","entertained","forgive","forgiveness","heaven",
            "shiny","healthy","high"};
    String negative[]={"sad","sadness","unhappy","bad","worse","worst","exhausted","tired","tiredness","curse","cursed","anguish",
            "anger","angry","angrier","angriest","cry",
            "crying","cried","scary","scarier", "scariest","scared","fear","feared","frightening","frightened","afraid","disabled","crippled",
            "handicap","handicapped", "retarted","childish","misery","miserly","immature","juvenile","dumb","stupid","idiot","dull","nonsense",
            "lack","lacks","lacked","lacking",
            "tough","hard","alarmed","tense","tension","annoy","annoyed","annoying","annoyance","distress","distressed","distressing","depressed","depression",
            "depress","depressing","frustration",
            "frustrate","frustrating","frustrated","miserable","gloomy","bored","boring","boredom","droopy","ugly","disgust","disgusted",
            "disgusting","aggressive","aggression","fiery","fiersome","intense","volatile","visceral","disappointed","disappointment","wry","displeased",
            "discontent","discontended","offence","offended","oppression","unimpressed","irritation","irritate","irritated","pathetic","tragedy",
            "lackness", "tragic","accident","dead","death","loss","expire","expired","bane","poison","poisonous","melancholy","dark","heavy",
            "dignified","dignifying",
            "serious","dramatic","restless","vigorous","exhilarated","agitate","agitation","agitated","poor","poorer","poorest","poorness","trouble","troubled",
            "troubling","nervous","nervousness","problem","problematic","awful","sorrow","sorrowful","despair","gloom","desolation",
            "isolation","isolated","isolate","separated","separation","grief","grieve","grieving","mourn","mournful","weep","weeping",
            "misfortune","misfortunate","ache","aching","pain","painful","pity","pitiful","poignant","guilt","crime","criminal","prison",
            "prisoner",
            "haunt","haunted","haunting","bitter","sour","war","fight","questionable","dishonourable","dishonour","hate","hated","ras","despise",
            "detest","dislike","disliked","embarrass","embarrassment","regret","regretful","fail","failed","failure","failing","remorse",
            "remorseful","ashamed","sorry","shame","shameful","disgrace","disgraceful","fever","ill","illness","sick","sickens","sickness","low",
            "wicked","immoral","fool","foolish","gullible","weak","weaker","weakest","unreliable","unreal","unrealistic","pessimism","pessimistic",
            "artificial","negative","negativity","fake","racist","racism","hate","hates","hated","broke","broken","broked","broken","fatal","lethal","vicious","bloat",
            "bloating","vomit","vomiting","danger","dangerous","disable","disability","impossible","lose","lost","loser","nightmare","horrible","horrific","horror","revenge",
            "vengeance","hell","dull","mental","mad","disturbed","disturbance","low","false","lie","lying","anxiety","anxious","worry","worrying","worried","stress",
            "stressing","stressed","upset","headache"};
    TextToSpeech textToSpeech;
     TextView ab;
     Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final TextView t1=findViewById(R.id.text);
       final TextView ab = findViewById(R.id.abcd);
        final SoundMeter so = new SoundMeter();


        //final double dB = 20*log10(x / 32767.0);

        final Button button = findViewById(R.id.button2);
        Button button1 = findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getspeech(v);

            }
        });


            button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    class async extends AsyncTask<Void,Void,Void> {
                        public async() {
                            super();
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            button.setText("START");

                        }

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            button.setText("capturing");

                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            so.start();
                            long count=0;
                            double sum=0;
                            long a= System.currentTimeMillis();
                            while(true) {
                                long b=System.currentTimeMillis();
                                Context context = getApplicationContext();
                                double x = so.getTheAmplitude();
                                double dB = 20 * log10(x / 32767.0);
                                String s=Double.toString(dB);
                                if(!s.equals("-Infinity")){
                                    sum+=dB;
                                    count++;
                                }
                                CharSequence text = Double.toString(dB);
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                //toast.show();
                                if(b-a>=5000){
                                    double d=sum/count;
                                    ab.setText(" " + d);
                                    so.stop();
                                    if(d<-18){
                                        ab.setText(ab.getText()+" low arousal" );
                                    }
                                    else {
                                        ab.setText(ab.getText()+" high arousal" );

                                    }

                                    break;
                                }

                            }
                            return null;
                        }
                    }

                 async a=new async();
                    a.onPreExecute();
                    a.doInBackground();
                    a.onPostExecute(null);


                }
            });
        t=(TextView)findViewById(R.id.text);
        valence=(TextView)findViewById(R.id.valence);
        textToSpeech= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });


    }

    public void getspeech(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //if(intent.resolveActivity(getPackageManager())!=null) {
        try{
            startActivityForResult(intent, 10);


        }
        catch (ActivityNotFoundException e){
            Toast.makeText(this,"install/update google voice search box",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                t.setText(result.get(0));
                tell(result.get(0));
            }
        }
    }

    public void tell(String s){
        String text="neutral valence";
        String arr[]=s.split(" ");
        int prev1=0,prev2=0,prev3=0;
        int flag=0;
        for(int i=0;i<arr.length;i++){
            if(i>0 && (arr[i-1].equals("not") || arr[i-1].equals("doesn't") || arr[i-1].equals("didn't") || arr[i-1].equals("couldn't")
                    || arr[i-1].equals("shouldn't") || arr[i-1].equals("haven't") || arr[i-1].equals("hadn't") || arr[i-1].equals("isn't")
                    || arr[i-1].equals("ain't") )){
                prev1=1;
            }
            else{
                prev1=0;
            }
            if(i>1 && (arr[i-2].equals("not") || arr[i-2].equals("doesn't") || arr[i-2].equals("didn't") || arr[i-2].equals("couldn't")
                    || arr[i-2].equals("shouldn't") || arr[i-2].equals("haven't") || arr[i-2].equals("hadn't") || arr[i-2].equals("isn't")
                    || arr[i-2].equals("ain't") )){
                prev2=1;
            }
            else{
                prev2=0;
            }
            if(i>2 && (arr[i-3].equals("not") || arr[i-3].equals("doesn't") || arr[i-3].equals("didn't") || arr[i-3].equals("couldn't")
                    || arr[i-3].equals("shouldn't") || arr[i-3].equals("haven't") || arr[i-3].equals("hadn't") || arr[i-3].equals("isn't")
                    || arr[i-3].equals("ain't") )){
                prev3=1;
            }
            else{
                prev3=0;
            }
            for(int j=0;j<positive.length;j++){
                if(arr[i].equals(positive[j])){
                    flag=1;
                    if(prev1==1 || prev2==1 || prev3==1){
                        text="negative valence";
                    }
                    else{
                        text="positive valence";

                    }
                    break;
                }
            }
            if(flag==1){
                break;
            }
            for(int j=0;j<negative.length;j++){
                if(arr[i].equals(negative[j])){
                    flag=1;
                    if(prev1==1 || prev2==1 || prev3==1){
                        text="positive valence";
                    }
                    else{
                        text="negative valence";

                    }
                    break;
                }
            }
            if(flag==1){
                break;
            }
        }
        float pitch=0.5f;
        float speed=0.7f;
        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
            valence.setText(text);
        }
        else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            valence.setText(text);
        }
    }

}






















class SoundMeter {
    static final private double EMA_FILTER = 0.6;

    private MediaRecorder mRecorder = null;
    private double mEMA = 0.0;

    public void start() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null/");

            try {
                mRecorder.prepare();
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            mRecorder.start();
            mEMA = 0.0;
        }
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getTheAmplitude() {
        if (mRecorder != null)
            return (mRecorder.getMaxAmplitude());
        else
            return 1;
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return (mRecorder.getMaxAmplitude() / 2700.0);
        else
            return 0;

    }

    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }
}