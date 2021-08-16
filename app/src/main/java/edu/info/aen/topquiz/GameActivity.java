package edu.info.aen.topquiz;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import edu.info.aen.topquiz.databinding.ActivityGameBinding;
import edu.info.aen.topquiz.viewmodels.GameViewModel;

public class GameActivity extends AppCompatActivity {

    ActivityGameBinding binding;
    private GameViewModel gameViewModel;
    private Long actualCorrectAnswer = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        this.gameViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(this.getApplication(), this)).get(GameViewModel.class);

        initQuestionFromLiveDataList();
        observeCurrentQuestionUpdate();
        this.binding.gameActivityTextViewQuestion.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.ttf"));
        this.binding.progressBar1.setOnClickListener(v -> {
            this.highlightButtonsOnAnswer((ProgressBar) v);
        });
        this.binding.progressBar2.setOnClickListener(v -> {
            this.highlightButtonsOnAnswer((ProgressBar) v);
        });
        this.binding.progressBar3.setOnClickListener(v -> {
            this.highlightButtonsOnAnswer((ProgressBar) v);
        });
        this.binding.progressBar4.setOnClickListener(v -> {
            this.highlightButtonsOnAnswer((ProgressBar) v);
        });
    }

    /**
     *
     */
    void initQuestionFromLiveDataList(){
        this.gameViewModel.getQuestionsList().observe(this, questions -> {
            if(gameViewModel.getCurrentQuestion().getValue() == null){
                this.gameViewModel.nextQuestion();
            }
        });
    }

    private void animateValidProgressBar(@NonNull ProgressBar progressBar){
        progressBar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.progress_button, null));
        progressBar.setProgress(0);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), 100).setDuration(2000);
        objectAnimator.addUpdateListener(valueAnimator -> {
            int progress = (int) valueAnimator.getAnimatedValue();
            progressBar.setProgress(progress);
        });
        objectAnimator.start();
    }

    void observeCurrentQuestionUpdate(){
        this.gameViewModel.getCurrentQuestion().observe(this, question -> {
            // Update the UI, in this case, a TextView.
            this.binding.gameActivityTextViewQuestion.setText(question.text);
            this.binding.textViewButton1.setText(question.answer0);
            this.binding.textViewButton2.setText(question.answer1);
            this.binding.textViewButton3.setText(question.answer2);
            this.binding.textViewButton4.setText(question.answer3);
            this.actualCorrectAnswer = question.correctAnswer;
        });
    }
    public void highlightButtonsOnAnswer(@NonNull ProgressBar pressedBar){
        pressedBar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pending_button, null));
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            pressedBar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.invalid_button, null));
            if (actualCorrectAnswer == 1L) {
                this.animateValidProgressBar(this.binding.progressBar1);
            }else if(actualCorrectAnswer == 2L){
                this.animateValidProgressBar(this.binding.progressBar2);
            }else if(actualCorrectAnswer == 3L){
                this.animateValidProgressBar(this.binding.progressBar3);
            }else{
                this.animateValidProgressBar(this.binding.progressBar4);
            }
            loadingNext3Seconds();
        }, 1000);
    }

    private void loadingNext3Seconds(){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            this.resetButtonsAppearance();
            this.gameViewModel.nextQuestion();
        }, 3000);
    }

    private void resetButtonsAppearance(){
        this.binding.progressBar1.setProgressDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.base_button, null));
        this.binding.progressBar2.setProgressDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.base_button, null));
        this.binding.progressBar3.setProgressDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.base_button, null));
        this.binding.progressBar4.setProgressDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.base_button, null));
    }
}