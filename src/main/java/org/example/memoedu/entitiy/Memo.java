package org.example.memoedu.entitiy;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.memoedu.dto.MemoRequestDto;

@Getter
@AllArgsConstructor
public class Memo {

    //사용 변수 선언
    private Long id; //wrapper class임에 따라, int 보다 크고, null을 포함하기에 사용함.
    private String content;
    private String title;

    public void update(MemoRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }



}
