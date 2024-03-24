package dentaira.accountmanagement.adapter.web;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.user.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserUsecase userUsecase;

  @PostMapping
  public UserDTO post(@RequestBody @Valid UserCreateCommand command) {
    return userUsecase.create(command);
  }

  @PatchMapping(path = "/{id}")
  public UserDTO patch(@PathVariable UserId id, @RequestBody UserEditCommand command) {
    return userUsecase.edit(id, command);
  }

  @PatchMapping(path = "/{id}/email")
  public UserDTO patchEmail(@PathVariable UserId id, @RequestBody EmailAddress email) {
    return userUsecase.changeEmail(id, email);
  }
}
