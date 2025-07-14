package unq.pdes._5.g1.segui_tus_compras.model.dto.in.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuestionsDto {
    @NotBlank(message = "text field cannot be blank")
    @Size(max = 255, message = "Question text cannot exceed 255 characters")
    public String text;
}
