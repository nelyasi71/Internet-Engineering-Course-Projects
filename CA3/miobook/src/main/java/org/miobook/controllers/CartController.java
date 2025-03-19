package org.miobook.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.CartRecord;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartServices cartServices;

    @Authenticated(roles = {"customer"})
    @PostMapping("/add")
    public BaseResponse<Void> add_cart(@RequestBody AddCart command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @Authenticated(roles = {"customer"})
    @PostMapping("/remove")
    public BaseResponse<Void> remove_cart(@RequestBody RemoveCart command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @Authenticated(roles = {"customer"})
    @PostMapping("/purchase")
    public BaseResponse<Void> borrow_nook(@RequestBody BorrowBook command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @Authenticated(roles = {"customer"})
    @PostMapping("/borrow")
    public BaseResponse<PurchaseCartRecord> purchase_cart(@RequestBody PurchaseCart command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @Authenticated(roles = {"customer"})
    @GetMapping("")
    public BaseResponse<CartRecord> show_cart(@PathVariable String username, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ShowCart command = new ShowCart();
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }
}
