package org.example.memoedu.controller;

import org.example.memoedu.dto.MemoRequestDto;
import org.example.memoedu.dto.MemoResponseDto;
import org.example.memoedu.entitiy.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class MemoController {


    //저장할 자료 구조
    private final Map<Long, Memo> memoList = new HashMap<>();

    //PostMapping
    @PostMapping
    //Status Code Create
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {
        // 식별자 1씩 증가
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청 받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContent());

        //Inmemeory DB에 Memo 저장
        memoList.put(memoId, memo);

        //Status Code Create
        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

    //메모 목록 조회 기능
    @GetMapping
    public List<MemoResponseDto> findAllMemos() {

        //intiList
        List<MemoResponseDto> responseList = new ArrayList<>();

        //HashMap<Memo> --> findAll List<MemoResponseDto>
        for (Memo memo : memoList.values()) {
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            responseList.add(responseDto);
        }

        // Map to List
//        responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return responseList;
    }

    //단건 조회 기능
    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {


        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);

    }



    //단건 전체 수정 기능
    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.update(dto);


        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    //단건 일부 수정 기능
    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(
            @PathVariable long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);
        if(memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(dto.getTitle() == null || dto.getContent() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.updateTitle(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {

        if(memoList.containsKey(id)) {
            memoList.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
