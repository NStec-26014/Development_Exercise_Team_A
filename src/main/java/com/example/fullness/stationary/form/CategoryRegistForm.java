package com.example.fullness.stationary.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRegistForm implements Serializable {

    // バリデーションチェック
    @NotBlank(message = "カテゴリ名を入力してください")
    @Size(min = 1, max = 30, message = "カテゴリ名は1～30文字で入力してください")

    private String categoryName;

}
