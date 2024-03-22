package dentaira.accountmanagement.web;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.user.UserCreateCommand;
import dentaira.accountmanagement.user.UserDTO;
import dentaira.accountmanagement.user.UserEditCommand;
import dentaira.accountmanagement.user.UserUsecase;
import dentaira.accountmanagement.user.UserId;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserUsecase userUsecase;

    @PostMapping
    public UserDTO post(@RequestBody UserCreateCommand command) {
        return userUsecase.create(command);
    }

    @PatchMapping(path = "/{id}")
    public UserDTO patch(@PathVariable UserId id, @RequestBody UserEditCommand command) {
        return userUsecase.edit(id, command);
    }

    @PatchMapping(path = "/{id}/email")
    public UserDTO patchEmail(@PathVariable UserId id, @RequestBody String email) {
        var emailAddress = new EmailAddress(email);
        return userUsecase.changeEmail(id, emailAddress);
    }

}
