package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskValidator {
    // バリデーションを実行する
    public static List<String> validate(Task tsk) {
        List<String> errors = new ArrayList<String>();

        String content_error = _validateContent(tsk.getContent());
        if(!content_error.equals("")) {
            errors.add(content_error);
        }

        return errors;
    }

    // タスク内容の必須入力チェック
    private static String _validateContent(String content) {
        if(content == null || content.equals("")) {
            return "タスクの内容を入力してください。";
        }

        return "";
    }

}
