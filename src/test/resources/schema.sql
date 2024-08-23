drop table if exists MEMBER CASCADE;
create table MEMBER
(
 id         bigint generated by default as identity,
 mail       varchar(100)    unique not null,
 name       varchar(15)     not null,       -- 사용자 이름
 user_id    varchar(30)     unique not null,       -- 사용자 아이디
 password   varchar(100)    not null,       -- 암호화해서 저장
 phone      varchar(12)     ,               -- 하이픈 빼고 저장 / 핸드폰은 나중에 필수 값 처리 -> 아직 입력하는 화면도 없음
 cdt        datetime,                       -- 생성일자
 primary key (id)
);

drop table if exists LOCATION_STORAGE CASCADE;
create table LOCATION_STORAGE
(
 travel_id      bigint generated by default as identity,    -- 얘 때문에 Master/Detail 나눠야 할듯..
 user_id        varchar(30) not null,    -- 사용자 아이디
 latitude       double not null,    -- 위도
 longitude      double not null,    -- 경도
 title          varchar(100),       -- 그룹핑 할 제목, 필요할거 같은데..
 address_name       varchar(100),       -- 장소명
 rmks       varchar(150),       -- 비고
 sq         int,                -- 좌표 순서
 cdt        datetime,           -- 생성 일자
 primary key (travel_id)
);


