# 예약 시스템 구축

>사용한 개발 언어 
 - java 17
   - jar 파일 프로젝트 최상위 디렉토리 추가 업로드
   - 실행 명령어 : java -jar lezhin-0.0.1-SNAPSHOT.jar

> 사용한 프레임워크 
 - spring boot 3.1.5
 - spring-boot-starter-data-jpa
   - ddl-auto : 'create' 설정으로 서버 구동시 자동 테이블 생성

> 사용한 RDBMS
 - H2
   - 서버 설치 없이 테스트 환경 구축에 용의
   - http://localhost:8080/h2-console/login.jsp

> 데이타 설계(ERD)
 - <img src="https://i.ibb.co/VCbs68D/erd.png">

> 사용한 캐시
 - embedded-redis
   - embedded 실행으로 서버 설치 없이 테스트 환경 구축에 용의

> swagger ui
 - URL 확인 및, 테스트에 용의한 swagger ui 적용 (springdoc-openapi-starter-webmvc-ui)
 - http://localhost:8080/swagger-ui/index.html

> 사용한 빌드툴
- maven

> 그 밖에 고민거리
 - 어느 정도 까지 구현해야 하는가
    - 시간은 한정적이고, 하나씩 추가하다보니 시간이 어느새...
    - 다양한 방법이 있었고, 어떤 방법을 채택 해야 하는지에 대한 고민이 많았다.
 - 빌드툴 선택 (maven vs gradle)
   - 우선 gradle 로 처음 시작했으나, 모듈 구성에서 많은 차질이 있었고,
   - gradle 버전이 업데이트 되어 자료 검색도 쉽지 않아 maven 으로 재구성하게 되었다.
   - maven 경우 lib, module 로 구성할수 있지만, 
   - module 구성하는 편이 개발 단계에서 라이브러리 추가 수정에 따른 새로고침 이슈가 적어 선택하게 되었다.
 - 동시성 처리를 어떻게 해결해야하는가
    - redis Lettuce 사용 동시성 해결 방법 선택
    - 스핀 방식으로 부하의 이슈가 있지지만 성능 이슈가 없다는 가정하에, 기능 구현에 집중했다.
    - AOP 까지 완료 하지 못한 아쉬움...
 - 실시간 인기 강연 데이터 일관성은 ?
   - 3일간의 제약 사항이 있었고, redis stored set으로는 해결이 힘듦을 알았다. 
   - redis ttl(만료시간)을 활용해서 3일이 지나면 자동으로 삭제되게 하여 집계되지 않게 하였다.
   - 정적키("ranking::lecture") + 동적키("lectureId::memberId")를 조합해
   - 정적키를 기준으로, 다이나미믹키를 split 해 랭킹 순위를 정했다.
   - 각 강의에 신청한 인원들을 바탕으로 랭킹산정 하는 방식으로 구현 했다.
 