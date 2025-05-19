package org.miobook.controllers;

import org.miobook.auth.Authenticated;
import org.miobook.commands.*;
import org.miobook.responses.BaseResponse;
import org.miobook.responses.CartRecord;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.services.CartServices;
import org.miobook.services.RedisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartServices cartServices;
    @Autowired
    RedisServices redisServices;

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/add")
    public BaseResponse<Void> add_cart(@RequestBody AddCart command, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        command.setUsername(redisServices.getUsername(token));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @DeleteMapping("/remove")
    public BaseResponse<Void> remove_cart(@RequestBody RemoveCart command, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        command.setUsername(redisServices.getUsername(token));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/borrow")
    public BaseResponse<Void> borrow_nook(@RequestBody BorrowBook command, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        command.setUsername(redisServices.getUsername(token));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @PostMapping("/purchase")
    public BaseResponse<PurchaseCartRecord> purchase_cart(@RequestBody PurchaseCart command, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        command.setUsername(redisServices.getUsername(token));
        return command.execute(cartServices);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @Authenticated(roles = {"customer"})
    @GetMapping("/list")
    public BaseResponse<CartRecord> show_cart(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        ShowCart command = new ShowCart();
        command.setUsername(redisServices.getUsername(token));
        return command.execute(cartServices);
    }
}
