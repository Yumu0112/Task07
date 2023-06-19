package com.example.task07;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final Map<Integer, String> users = new HashMap<>();

    UserController() {
        users.put(123, "user01");
        users.put(456, "user02");
        users.put(789, "user03");
    }

    @GetMapping("/user/index/{id}")
    public ResponseEntity<String> getUser(@PathVariable int id) {
        String user = users.get(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm form) {

        //  名前が空だったらエラーを返却
        if (form.getName() == null || form.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required.");

        }
        users.put(form.getId(), form.getName());
        return ResponseEntity.ok("User successfully created!!!");
    }


    @PatchMapping("/user/edit/{id}")
    public ResponseEntity<String> edit(@PathVariable("id") int id, @RequestBody EditForm form) {

        //  0文字以上20文字未満で名前の入力をバリデーション
        if (users.containsKey(id)) {
            String name = form.getName();
            if (name.length() > 20 || form.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Enter a name with at least 0 characters or no more than 20 characters.");
            }

            users.put(id, name);
            return ResponseEntity.ok("Information successfully changed!!!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        String user = users.remove(id);
        if (user != null) {
            return ResponseEntity.ok("User successfully deleted!!!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
