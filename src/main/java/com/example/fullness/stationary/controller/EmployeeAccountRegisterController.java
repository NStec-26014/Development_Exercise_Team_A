package com.example.fullness.stationary.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.fullness.stationary.config.TextEncoder;
import com.example.fullness.stationary.controller.form.EmployeeAccountForm;
import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.service.EmployeeAccountService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * コントローラークラス
 */
@Controller
@RequestMapping("/admin/account")
public class EmployeeAccountRegisterController {

    @Autowired
    EmployeeAccountService employeeAccountService;

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
    public String employeeAccountShowInput(HttpSession session, RedirectAttributes ra, Model model) {
        List<String> errorMessages = new ArrayList<String>();
        String errorMessage = "アカウント登録可能な社員が存在しません";
        try {
            if (employeeAccountService.showAllByNameIsNull().isEmpty()) {
                errorMessages.add(errorMessage);
                ra.addFlashAttribute("errorMessages", errorMessages);
                System.out.println(errorMessages);
                model.addAttribute("employees", new ArrayList<Employee>(employeeAccountService.showAllByNameIsNull()));
                return "accountForm";
            } else {
                model.addAttribute("employees", new ArrayList<Employee>(employeeAccountService.showAllByNameIsNull()));
                return "accountForm";
            }
        } catch (Exception e) {
            String errorMessage2 = "社員情報の取得に失敗しました";
            errorMessages.add(errorMessage2);
            ra.addFlashAttribute("errorMessages", errorMessages);
            return "accountForm";
        }
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
            @Valid @ModelAttribute EmployeeAccountForm employeeAccountForm,
            BindingResult bindingResult, HttpSession session, RedirectAttributes ra, Model model) {
        String accountErrorMessege = "このアカウント名は既に使用されています";
        List<String> errorMessages = new ArrayList<String>();
        try {
            boolean canRegisterAccountName = employeeAccountService
                    .canRegisterAccountName(employeeAccountForm.getAccountName());
            // 入力チェック
            if (bindingResult.hasErrors()) {
                if (canRegisterAccountName == false) {
                    bindingResult.rejectValue("accountName", "duplicate", accountErrorMessege);
                }
                errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .toList();
                ra.addFlashAttribute("errorMessages", errorMessages);
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
        } catch (Exception e) {
            String errorMessage = "社員情報の取得に失敗しました";
            errorMessages.add(errorMessage);
            ra.addFlashAttribute("errorMessages", errorMessages);
            return "redirect:/admin/account/form";
        }
    }

    // 確認画面を表示するメソッド
    // formインスタンスに情報を詰め込むことも行う
    @GetMapping("/confirm")
    public String accountConfirm(@ModelAttribute EmployeeAccountForm employeeAccountForm, HttpSession session,
            Model model) {
        // セッションデータがない場合は入力画面にリダイレクトする
        if (session.getAttribute("employeeAccountForm") == null) {
            return "redirect:/admin/account/form";
        }
        // formインスタンスに情報を詰め込む
        EmployeeAccountForm form = (EmployeeAccountForm) session.getAttribute("employeeAccountForm");

        // 社員IDから社員名を取得してformインスタンスに詰め込む
        String employeeName = employeeAccountService
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
    public String accountRegisterConfirm(HttpSession session, RedirectAttributes ra, Model model) {
        List<String> errorMessages = new ArrayList<String>();
        EmployeeAccount employeeAccount = new EmployeeAccount();

        TextEncoder textEncoder = new TextEncoder();
        employeeAccount.setEmployeeId((Integer) session.getAttribute("employeeId"));
        employeeAccount.setName((String) session.getAttribute("accountName"));
        // パスワードをハッシュ化してpasswordにセットする
        employeeAccount.setPassword(textEncoder.toHash((String) session.getAttribute("password")));
        // DBにアカウントを登録し。成功したらtrueを返す
        boolean success = employeeAccountService
                .registerEmployeeAccount(employeeAccount);
        if (success == true) {
            // DBに登録が成功したら"/admin/account/complete"にリダイレクトする
            return "redirect:/admin/account/complete";
        } else {
            String errorMessage = "登録処理に失敗しました。管理者に連絡してください。";
            errorMessages.add(errorMessage);
            ra.addFlashAttribute("errorMessages", errorMessages);
            // 例外発生でエラーを返す
            return "/accountConfirm";
        }
    }

    @GetMapping("/complete")
    public String accountRegister(HttpSession session, Model model) {
        // セッションデータがない場合はメニュー画面にリダイレクトする
        if (session.getAttribute("employeeName") == null || session.getAttribute("accountName") == null) {
            return "redirect:/admin";
        }
        // DBに登録した内容を取得する
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("accountName", session.getAttribute("accountName"));
        // 完了画面に遷移する
        return "accountComplete";
    }

}
