package edu.info.aen.topquiz.data.questions;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import edu.info.aen.topquiz.data.TopQuizzDatabase;

public class QuestionRepository{

    private final QuestionDao mQuestionDao;
    private LiveData<List<Question>> mAllQuestions;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public QuestionRepository(Application application) {
        TopQuizzDatabase db = TopQuizzDatabase.getDatabase(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getAllQuestions();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Question>> getAllQuestions() {
        return mAllQuestions;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Question question) {
        TopQuizzDatabase.databaseWriteExecutor.execute(() -> {
            mQuestionDao.insertOneQuestion(question);
        });
    }

    LiveData<Question> getQuestionById(long id){
            return mQuestionDao.getQuestionById(id);
    }
}
