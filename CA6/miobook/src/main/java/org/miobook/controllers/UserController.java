package org.miobook.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseHistoryRecord;
import org.miobook.responses.PurchasedBooksRecord;
import org.miobook.responses.UserRecord;
import org.miobook.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserServices userServices;

    @PostMapping("/user")
    @CrossOrigin(origins = "http://localhost:5173")
    public BaseResponse<Void> add_user(@RequestBody AddUser command) {
        return command.execute(userServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/credit")
    public BaseResponse<Void> add_credit(@RequestBody AddCredit command, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(userServices);
    }

    @GetMapping("/users/{username}")
    public BaseResponse<UserRecord> show_user(@PathVariable String username) {
        ShowUserDetails command = new ShowUserDetails(username);
        return command.execute(userServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/purchase-history")
    public BaseResponse<PurchaseHistoryRecord> show_purchase_history(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ShowPurchaseHistory command = new ShowPurchaseHistory();
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(userServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/purchased-books")
    public BaseResponse<PurchasedBooksRecord> show_purchased_books(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ShowPurchasedBooks command = new ShowPurchasedBooks();
        command.setUsername((String) session.getAttribute("username"));
        return command.execute(userServices);
    }
}
