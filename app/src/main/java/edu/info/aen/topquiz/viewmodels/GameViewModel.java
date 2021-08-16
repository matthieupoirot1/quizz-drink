package edu.info.aen.topquiz.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import edu.info.aen.topquiz.data.questions.Question;
import edu.info.aen.topquiz.data.questions.QuestionRepository;

public class GameViewModel extends AndroidViewModel {
    private final SavedStateHandle mState;
    private QuestionRepository repository;

    private LiveData<List<Question>> mAllQuestions;

    private MutableLiveData<Question> mCurrentQuestion;
    public MutableLiveData<Question> getCurrentQuestion() {
        if (mCurrentQuestion == null) {
            mCurrentQuestion = new MutableLiveData<>();
        }
        return mCurrentQuestion;
    }

    private ArrayList<Integer> seen = new ArrayList<>();

    /**
     * Order is important
     * @param application provided by the SavedStateViewModelFactory
     * @param savedStateHandle provided by the SavedStateViewModelFactory
     */
    public GameViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        mState = savedStateHandle;
        this.repository = new QuestionRepository(application);
        //load LiveDataList from repo
        mAllQuestions = repository.getAllQuestions();
        //mState.set(QUESTION_KEY, example);
    }

    /**
     * return LiveData containing a List of Question objects
     * @return LiveData
     */
    public LiveData<List<Question>> getQuestionsList(){
        return this.mAllQuestions;
    }

    /**
     * Change l'interieur du LiveData pour une question dont l'id n'est pas déjà vu.
     */
    public void nextQuestion() {
        if(mAllQuestions.getValue() != null){
          List<Question> questions = mAllQuestions.getValue();
          Random random = new Random();
          Question question;
          int index;
          do{
              index = random.nextInt(questions.size());
              question = questions.get(index);
          }while(seen.size() < questions.size() && seen.contains(index));
          if(seen.size()==questions.size()) {
              question = new Question("Il n'y a plus de question :(", "","","","",0L);
          }else{
              this.seen.add(index);
          }
          getCurrentQuestion().setValue(question);
        }
    }

    void saveQuestionState(Question question) {
        this.mState.set("question", question);
    }
}
