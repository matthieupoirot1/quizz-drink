package edu.info.aen.topquiz.data.questions;

/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Calendar;

/**
 */
@Entity(
        tableName = "question",
        indices = {@Index("question_id")})

public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    public long questionId;

    @ColumnInfo(name = "question_text")
    public String text;

    @ColumnInfo(name = "question_answer_0")
    public String answer0;
    @ColumnInfo(name = "question_answer_1")
    public String answer1;
    @ColumnInfo(name = "question_answer_2")
    public String answer2;
    @ColumnInfo(name = "question_answer_3")
    public String answer3;

    @ColumnInfo(name = "question_correct_answer")
    public Long correctAnswer;

    //todo make the constructor match the fields
    public Question(@NonNull String text, @NonNull String answer0, @NonNull String answer1, @NonNull String answer2, @NonNull String answer3,@NonNull Long correctAnswer) {
        this.text = text;
        this.answer0 = answer0;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correctAnswer = correctAnswer;
    }
}
