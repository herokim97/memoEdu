package org.example.memoedu.dto;

import lombok.Getter;
import org.example.memoedu.entitiy.Memo;

@Getter

public class MemoResponseDto {

    //서버 측, 응답 할 데이터
    private Long id;
    private String title;
    private String content;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.content = memo.getContent();
    }

}
