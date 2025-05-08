package test.example.demo.user.api.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.user.application.dto.UpdateUserRequest;
import test.example.demo.user.application.service.command.UserCommandService;
import test.example.demo.user.domain.entity.User;

@RestController
@RequestMapping("/api/users/commands")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping
    public ResponseEntity<Result> createUser(@RequestBody @Valid User user) {
        Result result = userCommandService.createUser(user);
        return buildResponseEntity(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateUser(@PathVariable Long id,
                                             @RequestBody @Valid UpdateUserRequest request) {
        Result result = userCommandService.updateUser(id, request);
        return buildResponseEntity(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteUser(@PathVariable Long id) {
        Result result = userCommandService.deleteUser(id);
        return buildResponseEntity(result);
    }

    private ResponseEntity<Result> buildResponseEntity(Result result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

