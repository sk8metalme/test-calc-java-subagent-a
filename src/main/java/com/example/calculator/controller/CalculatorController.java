package com.example.calculator.controller;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.service.CalculatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/")
    public String index(Model model) {
        if (!model.containsAttribute("calculationRequest")) {
            model.addAttribute("calculationRequest", new CalculationRequest());
        }
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@Valid @ModelAttribute CalculationRequest request, 
                          BindingResult bindingResult, 
                          RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.calculationRequest", bindingResult);
            redirectAttributes.addFlashAttribute("calculationRequest", request);
            redirectAttributes.addFlashAttribute("error", "入力値にエラーがあります");
            return "redirect:/";
        }

        try {
            BigDecimal result = calculatorService.calculate(
                request.getFirstNumber(), 
                request.getSecondNumber(), 
                request.getOperation()
            );
            
            request.setDisplayValue(result.toString());
            redirectAttributes.addFlashAttribute("calculationRequest", request);
            redirectAttributes.addFlashAttribute("success", "計算が完了しました");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("calculationRequest", request);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/button")
    public String handleButton(@RequestParam String button, 
                             @ModelAttribute CalculationRequest request,
                             RedirectAttributes redirectAttributes) {
        
        try {
            // requestがnullの場合は新しいインスタンスを作成
            if (request == null) {
                request = new CalculationRequest();
            }
            
            // デバッグログを追加
            System.out.println("Button clicked: " + button);
            System.out.println("Current display: " + request.getDisplayValue());
            
            CalculationRequest updatedRequest = handleButtonClick(request, button);
            
            // 更新された状態をFlashAttributeに保存
            redirectAttributes.addFlashAttribute("calculationRequest", updatedRequest);
            redirectAttributes.addFlashAttribute("success", "ボタンがクリックされました: " + button);
            
            System.out.println("Updated display: " + updatedRequest.getDisplayValue());
            
        } catch (Exception e) {
            if (request == null) {
                request = new CalculationRequest();
            }
            redirectAttributes.addFlashAttribute("calculationRequest", request);
            redirectAttributes.addFlashAttribute("error", "エラー: " + e.getMessage());
            System.err.println("Button click error: " + e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/button-ajax")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleButtonAjax(@RequestParam String button, 
                                                               @ModelAttribute CalculationRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // requestがnullの場合は新しいインスタンスを作成
            if (request == null) {
                request = new CalculationRequest();
            }
            
            System.out.println("AJAX Button clicked: " + button);
            System.out.println("Current display: " + request.getDisplayValue());
            
            CalculationRequest updatedRequest = handleButtonClick(request, button);
            
            response.put("success", true);
            response.put("displayValue", updatedRequest.getDisplayValue());
            response.put("message", "ボタンがクリックされました: " + button);
            
            System.out.println("Updated display: " + updatedRequest.getDisplayValue());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            System.err.println("AJAX Button click error: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    private CalculationRequest handleButtonClick(CalculationRequest request, String button) {
        if (request == null) {
            request = new CalculationRequest();
        }

        switch (button) {
            case "C":
                return new CalculationRequest();
                
            case "CE":
                request.setDisplayValue("0");
                return request;
                
            case "±":
                if (!request.getDisplayValue().equals("0")) {
                    if (request.getDisplayValue().startsWith("-")) {
                        request.setDisplayValue(request.getDisplayValue().substring(1));
                    } else {
                        request.setDisplayValue("-" + request.getDisplayValue());
                    }
                }
                return request;
                
            case "%":
                try {
                    BigDecimal number = new BigDecimal(request.getDisplayValue());
                    BigDecimal result = calculatorService.calculatePercentage(number);
                    request.setDisplayValue(result.toString());
                } catch (Exception e) {
                    throw new RuntimeException("パーセント計算エラー: " + e.getMessage());
                }
                return request;
                
            case "√":
                try {
                    BigDecimal number = new BigDecimal(request.getDisplayValue());
                    BigDecimal result = calculatorService.calculateSquareRoot(number);
                    request.setDisplayValue(result.toString());
                } catch (Exception e) {
                    throw new RuntimeException("平方根計算エラー: " + e.getMessage());
                }
                return request;
                
            case "x²":
                try {
                    BigDecimal number = new BigDecimal(request.getDisplayValue());
                    BigDecimal result = calculatorService.calculatePower(number, new BigDecimal("2"));
                    request.setDisplayValue(result.toString());
                } catch (Exception e) {
                    throw new RuntimeException("二乗計算エラー: " + e.getMessage());
                }
                return request;
                
            case "=":
                if (!request.getPreviousValue().isEmpty() && !request.getCurrentOperation().isEmpty()) {
                    try {
                        BigDecimal first = new BigDecimal(request.getPreviousValue());
                        BigDecimal second = new BigDecimal(request.getDisplayValue());
                        BigDecimal result = calculatorService.calculate(first, second, request.getCurrentOperation());
                        request.setDisplayValue(result.toString());
                        request.setPreviousValue("");
                        request.setCurrentOperation("");
                        request.setShouldResetDisplay(true);
                    } catch (Exception e) {
                        throw new RuntimeException("計算エラー: " + e.getMessage());
                    }
                }
                return request;
                
            case "+":
            case "-":
            case "*":
            case "/":
                if (!request.getPreviousValue().isEmpty() && !request.getCurrentOperation().isEmpty()) {
                    // 連続計算
                    try {
                        BigDecimal first = new BigDecimal(request.getPreviousValue());
                        BigDecimal second = new BigDecimal(request.getDisplayValue());
                        BigDecimal result = calculatorService.calculate(first, second, request.getCurrentOperation());
                        request.setPreviousValue(result.toString());
                        request.setCurrentOperation(button);
                        request.setShouldResetDisplay(true);
                    } catch (Exception e) {
                        throw new RuntimeException("計算エラー: " + e.getMessage());
                    }
                } else {
                    request.setPreviousValue(request.getDisplayValue());
                    request.setCurrentOperation(button);
                    request.setShouldResetDisplay(true);
                    // 演算子ボタンをクリックした後は、ディスプレイをクリアしない
                    // 次の数字入力まで現在の値を保持
                }
                return request;
                
            default:
                // 数字ボタン
                if (request.isShouldResetDisplay() || request.getDisplayValue().equals("0")) {
                    request.setDisplayValue(button);
                    request.setShouldResetDisplay(false);
                } else {
                    if (request.getDisplayValue().length() < 15) { // 表示桁数制限
                        request.setDisplayValue(request.getDisplayValue() + button);
                    }
                }
                return request;
        }
    }
}
