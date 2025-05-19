package org.miobook.controllers;

import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.PurchaseHistoryRecord;
import org.miobook.responses.PurchasedBooksRecord;
import org.miobook.responses.UserRecord;
import org.miobook.services.RedisServices;
import org.miobook.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserServices userServices;
    @Autowired
    RedisServices redisServices;

    @PostMapping("/user")
    @CrossOrigin(origins = "http://localhost:5173")
    public BaseResponse<Void> add_user(@RequestBody AddUser command) {
        return command.execute(userServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/credit")
    public BaseResponse<Void> add_credit(@RequestBody AddCredit command, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        command.setUsername(redisServices.getUsername(token));
        return command.execute(userServices);
    }

    @GetMapping("/users/{username}")
    @CrossOrigin(origins = "http://localhost:5173")
    public BaseResponse<UserRecord> show_user(@PathVariable String username) {
        ShowUserDetails command = new ShowUserDetails(username);
        return command.execute(userServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/purchase-history")
    public BaseResponse<PurchaseHistoryRecord> show_purchase_history(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        ShowPurchaseHistory command = new ShowPurchaseHistory();
        command.setUsername(redisServices.getUsername(token));
        return command.execute(userServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/purchased-books")
    public BaseResponse<PurchasedBooksRecord> show_purchased_books(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        ShowPurchasedBooks command = new ShowPurchasedBooks();
        command.setUsername(redisServices.getUsername(token));
        return command.execute(userServices);
    }
}
