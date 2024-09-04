-- TRUNCATE TABLE TABLE_NAME;
drop table if exists MEMBER CASCADE;
create table MEMBER              -- 회원 정보
(
 id         bigint generated by default as identity,    -- 고유 아이디
 mail       varchar(100)    unique not null,            -- 중복 방지용 메일 인증
 user_id    varchar(30)     unique not null,            -- 사용자 아이디
 name       varchar(15)     not null,                   -- 사용자 이름(실명)
 password   varchar(100)    not null,                   -- 암호화해서 저장
 phone      varchar(12)     ,                           -- 하이픈 빼고 저장 / 핸드폰은 나중에 필수 값 처리 -> 아직 입력하는 화면도 없음
 cdt        datetime,                                   -- 생성일자
 primary key (id)
);

drop table if exists LOCATION_STORAGE_M CASCADE;
create table LOCATION_STORAGE_M       -- 여행 계획M(장소저장)
(
 travel_id      bigint generated by default as identity,    -- 얘 때문에 Master/Detail 나눠야 할듯..
 user_id        varchar(30) not null,       -- 사용자 아이디
 title          varchar(100),               -- 그룹핑 할 제목, 필요할거 같은데..
 trip_start     date,                       -- 여행 시작일
 trip_end       date,                       -- 여행 마지막
 rmks           varchar(150),               -- 비고
 cid            bigint,                     -- 생성자      => 나중에 복사하는 경우 최초 생성자 id값 계속 들고가게 해서 원조(?) 판별
 cdt            datetime,                   -- 생성 일자
 primary key (travel_id)
);

drop table if exists LOCATION_STORAGE_D CASCADE;
create table LOCATION_STORAGE_D         -- 여행 계획D
(
 travel_id      bigint not null,            -- Detail       // 마스터에서 넘겨 받아야함
 travel_sq      bigint not null,            -- 여행지 순서(클릭 좌표)
 latitude       double not null,            -- 위도
 longitude      double not null,            -- 경도
 hour00         varchar(300),               -- 00:00 ~ 00:59
 hour01         varchar(300),
 hour02         varchar(300),
 hour03         varchar(300),
 hour04         varchar(300),
 hour05         varchar(300),
 hour06         varchar(300),
 hour07         varchar(300),
 hour08         varchar(300),
 hour09         varchar(300),
 hour10         varchar(300),
 hour11         varchar(300),
 hour12         varchar(300),               -- 12:00 ~ 12:59
 hour13         varchar(300),
 hour14         varchar(300),
 hour15         varchar(300),
 hour16         varchar(300),
 hour17         varchar(300),
 hour18         varchar(300),
 hour19         varchar(300),
 hour20         varchar(300),
 hour21         varchar(300),
 hour22         varchar(300),
 hour23         varchar(300),               -- 23:00 ~ 23:59
 day_trip       int,                        -- 여행 일자(2박3일 중 1일차)
 rmks           varchar(150),               -- 비고
 cid            bigint,                     -- 생성자
 cdt            datetime,                   -- 생성 일자
 primary key (travel_id, travel_sq)
);

drop table if exists LOCATION CASCADE;
create table LOCATION                           -- 여행 별 위,경도 좌표 값 저장
(
 travel_id          bigint not null,            -- 여행 번호(LOCATION_STORAGE_M에서 넘겨 받아야함)
 travel_sq          bigint not null,            -- 여행 순서(클릭 좌표)
 latitude           double not null,            -- 위도
 longitude          double not null,            -- 경도
 address_name       varchar(100),               -- 주소지명
 location_name      varchar(100),               -- 장소명(or 건물명)
 cdt                datetime,                   -- 생성 일자
 cid                bigint,                     -- 생성자
 primary key (travel_id, travel_sq)
);