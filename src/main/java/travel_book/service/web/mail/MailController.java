package travel_book.service.web.mail;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travel_book.service.web.mail.service.MailService;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    // 이 요청을 신규 회원가입 메일인증 / 기존 회원 비밀번호 찾기 메일 인증 두 곳에서 사용해서 isPasswordFind 라는 값을 하나 더 넘겨줘서 기존 회원 존재 유무 체크 패스
//    public String MailSend(String mail) {     // 데이터 타입 이슈
    public ResponseEntity<Map<String, String>> MailSend(@RequestBody Map<String, Object> paramMap) {

        log.info("paraMap = {}", paramMap);
        boolean isPasswordFind = Boolean.parseBoolean((String) paramMap.get("isPasswordFind"));
        String mail = paramMap.get("mail").toString();
        log.info("mail = {}", mail);
        log.info("isPasswordFind = {}", isPasswordFind);

        Map<String, String> resultMap = new HashMap<>();

        if (!isPasswordFind) {  // true = 비밀번호 찾기 화면 요청, false = 회원가입 메일 인증 화면
            boolean result = mailService.checkedMail(mail);
            if (result) {
                resultMap.put("message", "등록된 메일 주소 입니다.");
                return new ResponseEntity<>(resultMap, HttpStatus.OK);
            }
        }
        int number = mailService.sendMail(mail);
        //String num = "" + number; // 문자열로 바꾸려고 합쳤나본데.. 왜..? 사용도안하는데
        log.info("MailSend 호출 number = {}", number);
        resultMap.put("num", String.valueOf(number));   // 이것도 그냥 눈에 보기 좋게 던져줬는데, 추후에 빼고 서버에서 관리
        resultMap.put("message", "인증번호 요청했습니다.");

        return new ResponseEntity<>(resultMap, HttpStatus.OK);


    }

    // 제이쿼리로 mailChk 호출해서 가지고 있는 인증 키랑 비교 후 success면 $("ID").hide();    ==> 인증키 어떻게 관리? 그냥 전역변수?
    // -> 인증키 클라이언트로 안보내고 서버(세션)에서 관리하고 확인 요청 올 때만 확인해주면 됨
    @PostMapping("/verifyCode")     // 인증메일 확인 절차
    public ResponseEntity<Map<String, String>> CheckedCode(@RequestBody Map<String, String> requestMap) {
        String userInputCode = requestMap.get("code");
        log.info("userInputCode={}",userInputCode);
        Map<String, String> responseMap = new HashMap<>();
        if (mailService.verifyCode(userInputCode)) {
            responseMap.put("message", "인증에 성공했습니다.");
            return new ResponseEntity<>(responseMap,HttpStatus.ACCEPTED);
        }
        responseMap.put("message", "인증 번호가 다릅니다.");
        return new ResponseEntity<>(responseMap,HttpStatus.NO_CONTENT);
    }

}
