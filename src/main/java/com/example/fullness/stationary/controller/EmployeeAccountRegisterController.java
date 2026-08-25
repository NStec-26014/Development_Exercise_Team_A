package com.example.fullness.stationary.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fullness.stationary.config.TextEncoder;
import com.example.fullness.stationary.controller.form.EmployeeAccountForm;
import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.service.Impl.EmployeeAccountServiceImpl;

import jakarta.servlet.http.HttpSession;

/**
 * コントローラークラス
 */
@Controller
@RequestMapping("/admin/account")
public class EmployeeAccountRegisterController {

    @Autowired
    EmployeeAccountServiceImpl employeeAccountServiceImpl;

    // @Autowired
    // EmployeeAccountForm employeeAccountForm;

    /**
     * ユーザーが"/admin/account/form"のURLを打ち込んだ場合、
     * resource/templates配下のaccountform.htmlを表示させる
     * 
     * 
     */

    // 画面を表示するメソッド

    /**
     * formのURLが実行されたときに実行
     * DBからアカウント名がない社員の名前とIDを取得
     */
    @GetMapping("/form")
    public String employeeAccountShowInput(HttpSession session, Model model) {
        // model.addAttribute("form", new EmployeeAccountForm(null, null, null));
        model.addAttribute("employees", new ArrayList<Employee>(employeeAccountServiceImpl.showAllByNameIsNull()));
        return "accountForm";
    }

    /**
     * 入力規則に沿っているかを確認するメソッド
     * 沿っていなかった場合、担当者アカウント登録(入力)画面にリダイレクトする
     * 登録が重複してしまう場合も担当者アカウント登録(入力)画面にリダイレクトする
     * 
     * @param model
     * @return
     */

    @PostMapping("/validate")
    public String employeeAccountValidateInput(
            @Validated @ModelAttribute EmployeeAccountForm employeeAccountForm,
            BindingResult bindingResult, HttpSession session, Model model) {
        boolean canRegisterAccountName = employeeAccountServiceImpl
                .canRegisterAccountName(employeeAccountForm.getAccountName());
        System.out.println(canRegisterAccountName);
        // 入力チェック
        // if (bindingResult.hasErrors()) {
        // // ra.addFlashAttribute(employeeAccountForm);
        // // ra.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX +
        // // Conventions.getVariableName(employeeAccountForm),
        // // bindingResult);
        // // return "redirect:/admin/account/form";
        // return "redirect:/admin/account/form";
        // }

        // 重複チェック
        // エラーメッセージを表示させる
        if (canRegisterAccountName == false) {
            // model.addAttribute("message", "このアカウント名は既に使用されています");
            return "redirect:/admin/account/form";
        } else {

            // リダイレクト先に入力データを渡すために保存
            // model.addAttribute("employeeAccountForm", Form);
            session.setAttribute("employeeAccountForm", employeeAccountForm);
            session.setAttribute("employeeId", employeeAccountForm.getEmployeeId());
            session.setAttribute("accountName", employeeAccountForm.getAccountName());
            session.setAttribute("password", employeeAccountForm.getPassword());
            return "redirect:/admin/account/confirm";
        }
    }

    // 確認画面を表示するメソッド
    // formインスタンスに情報を詰め込むことも行う
    @GetMapping("/confirm")
    public String accountConfirm(@ModelAttribute EmployeeAccountForm employeeAccountForm, HttpSession session,
            Model model) {
        // formインスタンスに情報を詰め込む
        EmployeeAccountForm form = (EmployeeAccountForm) session.getAttribute("employeeAccountForm");

        // 社員IDから社員名を取得してformインスタンスに詰め込む
        String employeeName = employeeAccountServiceImpl
                .showEmployeeNameByEmployeeId(form.getEmployeeId());
        form.setEmployeeName(employeeName);

        model.addAttribute("registerForm", form);
        session.setAttribute("register", model.getAttribute("registerForm"));
        session.setAttribute("employeeName", employeeName);
        // 画面を遷移
        return "accountConfirm";
    }

    // ビュー層で押されたボタンに対する遷移先を制御するメソッド

    @PostMapping(value = "/confirm", params = "action=back")
    public String accountConfirmBack() {
        return "accountForm";
    }

    @PostMapping(value = "/confirm", params = "action=register")
    public String accountConfirmRegister() {
        return "redirect:/admin/account/register";
    }

    // *アカウントをDBに登録するメソッド */
    @GetMapping("/register")
    public String accountRegisterConfirm(HttpSession session, Model model) {
        EmployeeAccount employeeAccount = new EmployeeAccount();

        TextEncoder textEncoder = new TextEncoder();
        employeeAccount.setEmployeeId((Integer) session.getAttribute("employeeId"));
        employeeAccount.setName((String) session.getAttribute("accountName"));
        // パスワードをハッシュ化してpasswordにセットする
        employeeAccount.setPassword(textEncoder.toHash((String) session.getAttribute("password")));
        // DBにアカウントを登録し。成功したらtrueを返す
        boolean success = employeeAccountServiceImpl
                .registerEmployeeAccount(employeeAccount);
        if (success == true) {
            // DBに登録が成功したら"/admin/account/complete"にリダイレクトする
            return "redirect:/admin/account/complete";
        } else {
            // 例外発生でエラーを返す
            return "/error"; // 仮のURL
        }
    }

    @GetMapping("/complete")
    public String accountRegister(HttpSession session, Model model) {
        // DBに登録した内容を取得する
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("accountName", session.getAttribute("accountName"));
        // 完了画面に遷移する
        return "accountComplete";
    }

}
