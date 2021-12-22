# 무궁화꽃이 피었습니다 게임-자바 네트워크 게임

자바 소켓함수를 활용하여 만든 네트워크 게임. 요즘(2021년 11월) 대세 드라마 오징어게임에서 소스를 가져와서 만들었다. 방향키로 본인 캐릭터를 조종할 수 있으며 기존 '무궁화 꽃이 피었습니다' 게임 룰과 같이 움직일 수 없는 상황에는 움직이면 탈락하게 되고 움직일 수 있는 상황에는 움직일 수 있다. 최종 골인지점에 통과해야 게임에서 승리 할 수 있다.  



## 느낀 점

 네트워크 프로그래밍, 소켓함수에 대해서 심도있게 공부할수 있었다. 또한 프로젝트를 자바를 이용하여 만들게 되어서 자바 GUI와 Thread에 대해서 더 자세히 알게 되었다. 처음에 서버와 클라이언트 통신을 프로그래밍하기전 GUI부분을 코딩하는데 애를 먹었지만 기존에 잘못 알고 있던 개념들을 바로 잡게 되어 크게 막히는거 없이 진행할 수 있었다. 이번 텀프로젝트를 바탕으로 앞으로 더 많은 네트워크 프로그래밍 관련해서 사이드 프로젝트를 진행할 것이다. 이번에는 게임을 만들었지만 채팅프로그램을 만들고 싶고, Android studio를 활용하여 모바일과 통신하는 프로그램을 만들고 싶다. 약 3주간 밤늦게 까지 작업하면서 부족한 부분을 채우고 성장한거 같아서 뿌듯하다.



## 게임 화면

* 첫 화면-4인이 될때 까지 기다린다.![1](/Users/parkseongchan/Desktop/Squid Game/img/1.png)

  



* 게임 화면![스크린샷 2021-12-22 오후 6.06.42](/Users/parkseongchan/Desktop/Squid Game/img/스크린샷 2021-12-22 오후 6.06.42.png)

  ![스크린샷 2021-12-22 오후 6.06.54](/Users/parkseongchan/Desktop/Squid Game/img/스크린샷 2021-12-22 오후 6.06.54.png)

  ![스크린샷 2021-12-22 오후 6.07.04](/Users/parkseongchan/Desktop/Squid Game/img/스크린샷 2021-12-22 오후 6.07.04.png)

  ![스크린샷 2021-12-22 오후 6.07.17](/Users/parkseongchan/Desktop/Squid Game/img/스크린샷 2021-12-22 오후 6.07.17.png)



## 시스템 구성도

![스크린샷 2021-12-22 오후 6.16.25](/Users/parkseongchan/Library/Application Support/typora-user-images/스크린샷 2021-12-22 오후 6.16.25.png)

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

![스크린샷 2021-12-22 오후 6.21.18](/Users/parkseongchan/Desktop/Squid Game/img/스크린샷 2021-12-22 오후 6.21.18.png)

![스크린샷 2021-12-22 오후 6.22.11](/Users/parkseongchan/Library/Application Support/typora-user-images/스크린샷 2021-12-22 오후 6.22.11.png)

![스크린샷 2021-12-22 오후 6.22.34](/Users/parkseongchan/Library/Application Support/typora-user-images/스크린샷 2021-12-22 오후 6.22.34.png)

![스크린샷 2021-12-22 오후 6.22.56](/Users/parkseongchan/Desktop/Squid Game/img/스크린샷 2021-12-22 오후 6.22.56.png)



## 게임 기능



### 컨트롤

1. 방향키 ( →←↑↓) 로 플레이어를 움직일 수 있다.
2. 스페이스바(SPACE)로 다른 플레이어를 밀 수 있다.
3. "무궁화꽃이 피었습니다" 라는 문구에는 움직일 수 없다.



### 게임룰

1. 오래 생존하거나 골인 지점에 통과하면 높은 Rank를 받는다.
2. 유저를 뒤에서 밀 수 있다. "무궁화꽃이 피었습니다" 라는 문구에 밀어야지 다른 유저를 탈락 시킬 수 있다.
3. 골인 지점에 통과하거나, 전원 사망하면 게임이 종료된다. 게임이 종료되면 본인 순위를 확인시켜 주는 창을 띄운다.



