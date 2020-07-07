
## 카카오책 📚🔍
  - 소개
    - 유저가 입력한 키워드에 따라 조회된 책의 목록과 상세내용을 확인할 수 있는 애플리케이션입니다.   
   [카카오 책검색 API](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-book)를 이용하였습니다.
   
   - 과제 수행 전략 및 방법
     - Paging 처리를 위해 Jetpack의 LiveData 클래스와 Paging 라이브러리를 사용하였습니다.
     - 유명한 서점 애플리케이션의 UI를 참고하여 친숙한 느낌을 주고자 하였습니다. 
     - 각 정보의 색상과 크기 등에 변화를 주어 검색 결과를 알아보기 쉽도록 하였습니다. 
     - API로 전달받는 책 contents 데이터가 제한되어 있다는 점을 고려하여, url을 통해 책 상세 페이지로 이동이 가능하도록 하였습니다. 
     - API meta 데이터를 이용하여 책 검색 시 검색결과(검색 건수)를 토스트 메시지로 띄워 사용자가 보다 사용하기 편리하도록 하였습니다. 

  - 사용한 외부 라이브러리
    - 이미지 로딩을 위해 [Glide](https://github.com/bumptech/glide)를 사용하였습니다.
    - HTTP 통신을 위해 [Retrofit](https://github.com/square/retrofit), [okhttp](https://github.com/square/okhttp)을 사용하였습니다.

 

