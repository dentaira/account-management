package dentaira.accountmanagement.web.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.entity.EntityId;
import dentaira.accountmanagement.usecase.user.UserCreateCommand;
import dentaira.accountmanagement.usecase.user.UserDTO;
import dentaira.accountmanagement.usecase.user.UserEditCommand;
import dentaira.accountmanagement.usecase.user.UserUsecase;
import dentaira.accountmanagement.user.User;
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
    public UserDTO patch(@PathVariable EntityId<User> id, @RequestBody UserEditCommand command) {
        return userUsecase.edit(id, command);
    }

    @PatchMapping(path = "/{id}/email")
    public UserDTO patchEmail(@PathVariable EntityId<User> id, @RequestBody String email) {
        var emailAddress = new EmailAddress(email);
        return userUsecase.changeEmail(id, emailAddress);
    }

}
