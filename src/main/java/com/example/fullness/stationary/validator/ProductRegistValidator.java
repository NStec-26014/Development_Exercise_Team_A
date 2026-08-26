package com.example.fullness.stationary.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.fullness.stationary.controller.form.ProductRegistForm;

@Component
public class ProductRegistValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductRegistForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductRegistForm form = (ProductRegistForm) target;

        validateName(form, errors);
        validatePrice(form, errors);
        validateProductCategory(form, errors);
        validateQuantity(form, errors);
        // validateImageUrl(form, errors);
    }

    private void validateName(ProductRegistForm form, Errors errors) {
        String name = form.getName();
        if (name == null) {
            errors.rejectValue("name", "", "商品名を入力してください");
            return;
        }
        int length = name.trim().length();
        if (length < 2 || length > 20) {
            errors.rejectValue("name", "", "商品名は2〜20文字で入力してください");
        }
    }

    private void validatePrice(ProductRegistForm form, Errors errors) {
        Integer price = form.getPrice();
        if (price == null) {
            errors.rejectValue("price", "", "価格を入力してください");
            return;
        }
        if (price < 0) {
            errors.rejectValue("price", "", "正しい価格形式で入力してください");
            return;
        }
        if (price > 1000000) {
            errors.rejectValue("price", "", "価格は100万円以下で入力してください");
        }
    }

    private void validateQuantity(ProductRegistForm form, Errors errors) {
        Integer quantity = form.getQuantity();
        if (quantity == null) {
            errors.rejectValue("quantity", "", "在庫数を入力してください");
            return;
        }
        if (quantity < 0) {
            errors.rejectValue("quantity", "", "正しい在庫数形式で入力してください");
            return;
        }
        if (quantity > 1000) {
            errors.rejectValue("quantity", "", "在庫数は1000個以下で入力してください");
        }
    }

    private void validateProductCategory(ProductRegistForm form, Errors errors) {
        Integer productCategoryId = form.getProductCategoryId();
        if (productCategoryId == null) {
            errors.rejectValue("productCategoryId", "", "カテゴリを選択してください");
        }
    }

    private void validateImageUrl(ProductRegistForm form, Errors errors) {
        String imageUrl = form.getImageUrl();
        if (imageUrl == null) {
            errors.rejectValue("imageUrl", "", "画像をアップロードしてください");
            return;
        }
        String normalized = imageUrl.trim().toLowerCase();
        if (!normalized.matches("jpg")) {
            errors.rejectValue("imageUrl", "", "正しい画像形式でアップロードしてください");
        }

        long maxSizeInBytes = 1000;
        if (maxSizeInBytes > 1000) {
            errors.rejectValue("imageUrl", "", "画像サイズは1000px以下でアップロードしてください");
        }
    }
}
