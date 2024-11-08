package com.ohgiraffers.chap05springdata.restapi;


import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {

    /*
    * ResponseEntity 란 ?
    * 결과 데이터와 http 상태 코드를 직접 제어할 수 있는 클래스이다.
    * HttpStatus, HttpHeaders, HttpBody 를  포함한다.
    * */

    private List<UserDTO> users;

    public ResponseEntityTestController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "홍길동", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "유관순", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "이순신", new Date()));
    }

    // 모든  사용자를 조회하는 api 엔드포인트 메소드
    @GetMapping("/users")
    public ResponseEntity findAllUsers() {

        // http 헤더 객체 생성 - 필수 아님
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"
        , Charset.forName("UTF-8")));

        // 응답에 포함할 데이터 맵을 생성
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

        return new ResponseEntity(responseMap,headers, HttpStatus.OK);

    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity findUserById(@PathVariable("userNo") int userNo) {

        // http 헤더 객체 생성 - 필수 아님
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"
                , Charset.forName("UTF-8")));

        UserDTO foundUser = null;
        for (UserDTO user : users) {
            if (user.getNo() == userNo) {
                foundUser = user;
                break;
            }
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);
        return new ResponseEntity(responseMap,headers, HttpStatus.OK);

    }



}
