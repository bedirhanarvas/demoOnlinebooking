package test.example.demo.user.api.query;

import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.user.application.service.query.UserQueryService;
import test.example.demo.user.domain.entity.User;
import test.example.demo.user.domain.enums.Role;

@RestController
@RequestMapping("/api/users/queries")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<Optional<User>>> getUserById(@PathVariable Long id) {
        return buildResponse(userQueryService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<DataResult<List<User>>> getAllUsers() {
        return buildResponse(userQueryService.getAllUsers());
    }

    @GetMapping("/tc/{tcNumber}")
    public ResponseEntity<DataResult<Optional<User>>> getUserByTc(@PathVariable String tcNumber) {
        return buildResponse(userQueryService.getUserByTcNumber(tcNumber));
    }

    @GetMapping("/role")
    public ResponseEntity<DataResult<List<User>>> getUsersByRole(@RequestParam Role role) {
        return buildResponse(userQueryService.getUsersByRole(role));
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<User>>> searchUsers(@RequestParam String keyword) {
        return buildResponse(userQueryService.searchUsersByNameOrSurname(keyword));
    }

    @GetMapping("/{id}/with-reservations")
    public ResponseEntity<DataResult<Optional<User>>> getUserWithReservations(@PathVariable Long id) {
        return buildResponse(userQueryService.getUserWithReservation(id));
    }

    private <T> ResponseEntity<DataResult<T>> buildResponse(DataResult<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

