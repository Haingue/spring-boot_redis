package com.tmmf.test.controller;

import com.tmmf.test.dto.UserDto;
import com.tmmf.test.exception.UserNotFoundException;
import com.tmmf.test.exception.UserNotValidException;
import com.tmmf.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/service/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser () {
        return ResponseEntity.ok(userService.loadAllUser());
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser (@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
        } catch (UserNotValidException error) {
            return ResponseEntity.badRequest().build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDto> putUser (@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.updateUser(userDto));
        } catch (UserNotValidException error) {
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException error) {
            return ResponseEntity.notFound().build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDto> deleteUser (@PathParam("login") String login) {
        try {
            userService.deleteUser(login);
            return ResponseEntity.ok().build();
        } catch (UserNotValidException error) {
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException error) {
            return ResponseEntity.notFound().build();
        } catch (Exception error) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
