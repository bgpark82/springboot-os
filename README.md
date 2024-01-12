# ElasticSearch Kotlin In Action

# 영국 다나와
취업 비자를 지원하는 영국 회사 검색을 편하게 해주는 웹사이트입니다

## Installation
1. elasticsearch, kibana 실행
```http request
docker-compose -f docker/docker-compose.yml -d up
```

2. 영국 회사 데이터 입력 

csv 파일의 회사 목록을 읽어 elasticsearch에 저장한다
```http request
POST /uk/api/v1/companies
```

3. 영국 회사 검색
