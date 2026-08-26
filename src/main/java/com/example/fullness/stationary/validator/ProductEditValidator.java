package com.example.fullness.stationary.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.fullness.stationary.controller.form.ProductEditForm;

import jakarta.annotation.Nonnull;

@Component
public class ProductEditValidator implements Validator {

    private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 20;
    private static final int MAX_PRICE = 1_000_000;
    private static final int MAX_QUANTITY = 1000;

    @Nonnull
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductEditForm.class.isAssignableFrom(clazz);
    }

    @Nonnull
    @Override
    public void validate(Object target, Errors errors) {
        ProductEditForm form = (ProductEditForm) target;

        if (form.getId() == null) {
            errors.rejectValue("id", "required", "商品IDは必須です");
        }

        if (!StringUtils.hasText(form.getName())) {
            errors.rejectValue("name", "required", "商品名を入力してください");
        } else {
            String name = form.getName().trim();
            if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
                errors.rejectValue("name", "length", "商品名は2〜20文字で入力してください");
            }
        }

        // もしすでに型変換エラー（typeMismatch）が発生している場合
        if (errors.hasFieldErrors("price") &&
                "typeMismatch".equals(errors.getFieldError("price").getCode())) {

            // 💡 英語のエラーメッセージが画面に勝手に出るのを防ぐため、
            // フィールド（"price"）に直接紐付けず、オブジェクト全体のエラー（グローバルエラー）として登録します！
            errors.reject("invalidPriceFormat", "正しい価格形式で入力してください");

        } else if (form.getPrice() == null) {
            errors.rejectValue("price", "required", "価格を入力してください");
        } else if (form.getPrice() < 0 || form.getPrice() > MAX_PRICE) {
            errors.rejectValue("price", "range", "価格は100万円以下で入力してください");
        }

        if (errors.hasFieldErrors("quantity") &&
                "typeMismatch".equals(errors.getFieldError("quantity").getCode())) {

            // 在庫数もグローバルエラーとして登録し、英語を非表示にします
            errors.reject("invalidQuantityFormat", "正しい在庫数形式で入力してください");

        } else if (form.getQuantity() == null) {
            errors.rejectValue("quantity", "required", "在庫数を入力してください");
        } else if (form.getQuantity() < 0 || form.getQuantity() > MAX_QUANTITY) {
            errors.rejectValue("quantity", "range", "在庫数は1000個以下で入力してください");
        }

    }
}
