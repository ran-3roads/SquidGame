# 무궁화꽃이 피었습니다 게임-자바 네트워크 게임

자바 소켓함수를 활용하여 만든 네트워크 게임. 요즘(2021년 11월) 대세 드라마 오징어게임에서 소스를 가져와서 만들었다. 방향키로 본인 캐릭터를 조종할 수 있으며 기존 '무궁화 꽃이 피었습니다' 게임 룰과 같이 움직일 수 없는 상황에는 움직이면 탈락하게 되고 움직일 수 있는 상황에는 움직일 수 있다. 최종 골인지점에 통과해야 게임에서 승리 할 수 있다.  



## 느낀 점

 네트워크 프로그래밍, 소켓함수에 대해서 심도있게 공부할수 있었다. 또한 프로젝트를 자바를 이용하여 만들게 되어서 자바 GUI와 Thread에 대해서 더 자세히 알게 되었다. 처음에 서버와 클라이언트 통신을 프로그래밍하기전 GUI부분을 코딩하는데 애를 먹었지만 기존에 잘못 알고 있던 개념들을 바로 잡게 되어 크게 막히는거 없이 진행할 수 있었다. 이번 텀프로젝트를 바탕으로 앞으로 더 많은 네트워크 프로그래밍 관련해서 사이드 프로젝트를 진행할 것이다. 이번에는 게임을 만들었지만 채팅프로그램을 만들고 싶고, Android studio를 활용하여 모바일과 통신하는 프로그램을 만들고 싶다. 약 3주간 밤늦게 까지 작업하면서 부족한 부분을 채우고 성장한거 같아서 뿌듯하다.



## 게임 화면

* 첫 화면-4인이 될때 까지 기다린다.
<img width="726" alt="1" src="https://user-images.githubusercontent.com/66467266/147194895-a64ab5c7-b4ec-4f54-96a4-c1a1bfcd14cb.png">

  



* 게임 화면

<img width="711" alt="스크린샷 2021-12-22 오후 6 06 42" src="https://user-images.githubusercontent.com/66467266/147194987-ae3970eb-cdc1-4d9e-883e-ab035b468aba.png">

<img width="803" alt="스크린샷 2021-12-22 오후 6 07 17" src="https://user-images.githubusercontent.com/66467266/147195043-b5be14bc-20a6-466c-bb53-0a7b6e9ff611.png">


## 시스템 구성도
<img width="1117" alt="스크린샷 2021-12-23 오후 2 56 57" src="https://user-images.githubusercontent.com/66467266/147195156-872537a4-e2d9-4758-9e22-c911b72cb522.png">



## Protocol table

| Protocol | 용도/내용             | 방향              |
| -------- | --------------------- | ----------------- |
| 100      | Login                 | Client -> Server  |
| 101      | User Init             | Server  -> Client |
| 300      | User move             | Client  -> Server |
| 400      | User  Position        | Server  -> Client |
| 444      | Kill User             | Client  -> Server |
| 445      | Murder  User          | Server  -> Client |
| 600      | User Die              | Client  -> Server |
| 700      | User Goal in          | Client  -> Server |
| 800      | Game done             | Client  -> Server |
| 999      | Game Over and ranking | Server  -> Client |

## 시스템 흐름도
<img width="987" alt="스크린샷 2021-12-22 오후 6 21 18" src="https://user-images.githubusercontent.com/66467266/147195306-03ecf818-e514-493b-86b2-cd65251832fa.png">
<img width="949" alt="스크린샷 2021-12-22 오후 6 22 56" src="https://user-images.githubusercontent.com/66467266/147195359-61a2c3df-4862-4b04-8b9f-f5a73694e3c3.png">
<img width="1097" alt="스크린샷 2021-12-23 오후 2 58 31" src="https://user-images.githubusercontent.com/66467266/147195398-cd076d4e-4ae8-41fe-9fc2-13ee513bc950.png">
<img width="1114" alt="스크린샷 2021-12-23 오후 2 58 46" src="https://user-images.githubusercontent.com/66467266/147195429-2994e15d-a040-484e-9497-b414421b2c80.png">




## 게임 기능



### 컨트롤

1. 방향키 ( →←↑↓) 로 플레이어를 움직일 수 있다.
2. 스페이스바(SPACE)로 다른 플레이어를 밀 수 있다.
3. "무궁화꽃이 피었습니다" 라는 문구에는 움직일 수 없다.



### 게임룰

1. 오래 생존하거나 골인 지점에 통과하면 높은 Rank를 받는다.
2. 유저를 뒤에서 밀 수 있다. "무궁화꽃이 피었습니다" 라는 문구에 밀어야지 다른 유저를 탈락 시킬 수 있다.
3. 골인 지점에 통과하거나, 전원 사망하면 게임이 종료된다. 게임이 종료되면 본인 순위를 확인시켜 주는 창을 띄운다.



