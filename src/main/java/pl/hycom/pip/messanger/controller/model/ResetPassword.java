package pl.hycom.pip.messanger.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Piotr on 03.06.2017.
 */
@Data
@NoArgsConstructor
public class ResetPassword {

    private String userMail;

    private String resetToken;

    private String newPassword;
}
