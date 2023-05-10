package com.yongyong.firstproject.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class SessionGroup {
    private String room;
    private WebSocketSession session;
}

@Component
public class WebsocketProvider extends TextWebSocketHandler {
    // 원래 provider 라기보다 handler 로 이름을 만들어 주는것이 좋다.

    // handler는 계속 연결 과 메세지 수송신과 연결해제를 관리한다.
    private List<SessionGroup> sessionList = new ArrayList<>();

    // 연결
    // TextWebSocketHandler 에서 AbstractWebSocketHandler 까지 더 들어가면
    // 아래 오버라이드 코드들이 있다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String room = session.getHandshakeHeaders().getFirst("room");
        // request 헤더에 보내는것이랑 동일한것, 키를 이용해서 value를 가져올 수 있다.
        sessionList.add(new SessionGroup(room, session)); // 관리하는 세션으로 등록하겟다!.
        // session에 대해서 room 은 동일한 것이기 때문에 하나로 관리를 할 것이다.
        // 그래서 위에다가 여기서 만 사용할 클래스를 하나 만든다.
        // 그리고 위의 List생성 객체를 websocet 에서 sessiongroup으로 바꿔준다
        // 또 new Sessiongroup으로 바꿔준다.
        System.out.println(room + " / " + session.getId() + "Web Socket Connected!!");
    }

    // 메세지 송수신
    @Override // 해당 오버라이드도 마찬가지

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String room = session.getHandshakeHeaders().getFirst("room");
        for (SessionGroup sessionGroup : sessionList) {
            if (sessionGroup.getRoom().equals(room))
                sessionGroup.getSession().sendMessage(message); // 해당 룸에만 메세지가 송수신이 가능하다.

        }
    }

    // 연결 해제
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + "Web Socket Closed!!");
        for (SessionGroup sessionGroup : sessionList) {// 관리하는 대상에서 지우는 단계
            if (sessionGroup.getSession().equals(session)) {
                sessionList.remove(sessionGroup);
            }
        }
    }
}