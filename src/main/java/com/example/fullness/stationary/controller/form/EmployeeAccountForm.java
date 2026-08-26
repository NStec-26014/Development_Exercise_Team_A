package com.example.fullness.stationary.controller.form;

import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 社員アカウント登録のセッションデータを保存するクラス
 */

@Data
public class EmployeeAccountForm implements Serializable {

    @NotNull(message = "社員名を選択してください")
    private Integer employeeId;

    private String employeeName;

    @NotNull(message = "アカウント名を入力してください")
    @Size(min = 5, max = 20, message = "アカウント名は5～20文字で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "アカウント名は半角英数字で入力してください")
    private String accountName;

    @NotNull(message = "パスワードを入力してください")
    @Size(min = 5, max = 20, message = "パスワードは5～20文字で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "パスワードは半角英数字で入力してください")
    private String password;

}
