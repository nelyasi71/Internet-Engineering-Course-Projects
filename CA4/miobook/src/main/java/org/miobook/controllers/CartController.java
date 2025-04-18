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

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/add")
    public BaseResponse<Void> add_cart(@RequestBody AddCart command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @DeleteMapping("/remove")
    public BaseResponse<Void> remove_cart(@RequestBody RemoveCart command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/borrow")
    public BaseResponse<Void> borrow_nook(@RequestBody BorrowBook command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/purchase")
    public BaseResponse<PurchaseCartRecord> purchase_cart(@RequestBody PurchaseCart command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/list")
    public BaseResponse<CartRecord> show_cart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ShowCart command = new ShowCart();
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(cartServices);
    }
}
