package com.example.eduboost_backend.dto.quiz;

import com.example.eduboost_backend.model.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO {
    private Long id;
    private String optionText;
    private boolean isCorrect;

    public static OptionDTO fromEntity(Option option) {
        OptionDTO dto = new OptionDTO();
        dto.setId(option.getId());
        dto.setOptionText(option.getOptionText());
        dto.setCorrect(option.isCorrect());
        return dto;
    }
}