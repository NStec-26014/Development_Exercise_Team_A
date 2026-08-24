package com.example.fullness.stationary.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.fullness.stationary.form.ProductEditForm;

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

        if (form.getPrice() == null) {
            errors.rejectValue("price", "required", "価格を入力してください");
        } else if (form.getPrice() < 0 || form.getPrice() > MAX_PRICE) {
            errors.rejectValue("price", "range", "価格は100万円以下で入力してください");
        }

        if (form.getQuantity() == null) {
            errors.rejectValue("quantity", "required", "在庫数を入力してください");
        } else if (form.getQuantity() < 0 || form.getQuantity() > MAX_QUANTITY) {
            errors.rejectValue("quantity", "range", "在庫数は1000個以下で入力してください");
        }

        if (form.getProductCategoryId() == null || form.getProductCategoryId() == 0L) {
            errors.rejectValue("productCategoryId", "required", "カテゴリを選択してください");
        }
    }
}
