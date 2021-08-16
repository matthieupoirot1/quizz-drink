package edu.info.aen.topquiz.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.info.aen.topquiz.data.questions.Question;
import edu.info.aen.topquiz.data.questions.QuestionDao;

@Database(entities = {Question.class}, version = 1)
public abstract class TopQuizzDatabase extends RoomDatabase {
    public abstract QuestionDao questionDao();

    private static volatile TopQuizzDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TopQuizzDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {

            //Synchronized block of code => if DB is accessed in different Threads,
            // this code block will be accessible only by one at a time
            synchronized (TopQuizzDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    TopQuizzDatabase.class,
                                    "top_quizz").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                QuestionDao dao = INSTANCE.questionDao();
                Log.e("DB", "onCreate: " + "Erasing every questions :");
                dao.deleteAllQuestions();
                Log.e("DB", "onCreate: " + "Every questions erased !");
                Question example2 = new Question("Quel est le nom du premier être vivant dans l'espace ?",
                            "Milka", "Tilka", "Laïka", "Taïka", 3L);
                dao.insertOneQuestion(example2);
                Question example3 = new Question("En quelle année l'homme a-t-il marché sur la lune ?",
                        "1959", "1969", "1983", "1973", 2L);
                dao.insertOneQuestion(example3);
                Question example4 = new Question("Quel est la meilleure marque ?",
                        "Nike", "Adidas", "Puma", "Rivaldi", 2L);
                dao.insertOneQuestion(example4);
                Question example5 = new Question("Qui est le plus beau ?",
                        "Anthony", "Anthony", "Anthony", "Anthony", 2L);
                dao.insertOneQuestion(example5);
                Question example6 = new Question("Qui est le plus gentil ?",
                        "Anthony", "Anthony", "Anthony", "Anthony", 2L);
                dao.insertOneQuestion(example6);
                Question example7 = new Question("Qui est le sexy ?",
                        "Anthony", "Anthony", "Anthony", "Anthony", 2L);
                dao.insertOneQuestion(example7);
                Question example8 = new Question("Quelle épaisseur d'un isolation en R-7  ?",
                        "20.3cm", "36.30", "33.5", "10.90", 3L);
                dao.insertOneQuestion(example8);
            });
        }
    };
}
