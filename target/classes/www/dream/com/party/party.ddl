--Oracle 자료형 선택 시 
--int, long -> number(9), 19
--date -> 년 월 일(date), 일시(timestamp)
--boolean ->char(1)


DROP TABLE f_contact_point;
DROP TABLE f_contact;
DROP TABLE f_party;

DROP TABLE f_contact_point_type;

--Sequence
DROP SEQUENCE seq_party_id; 
CREATE SEQUENCE seq_party_id START WITH -990000000 MINVALUE -990000000 ; 

--party_type, description
create table f_party_type(
   party_type         char(10),
   description        varchar2(100)
);

insert into f_party_type(party_type, description)
   values('Admin', '관리자');
 
--user_id, user_pwd, name, birth_dt, sex, enabled, reg_dt, upt_dt, descrim
create table f_party(
        user_id       	varchar2(10)     primary key,
  	    user_pwd     	 varchar2(100)    not null,   --100 : 암호화된 결과물까지 고려
        name          varchar2(100)    not null,   --100 : 전지구적인 사람의 이름 길이까지 고려
        birth_dt       Date,                  -- 생일 년월일
        sex        	   char(1)      	default 1 not null,   --true male, false female
        email		  varchar2(100)		,
        address		varchar2(100)	,		
        phone_num		number(9) 		,
        enabled        char(1)   	      default 1,
        reg_dt         timestamp     	  default sysdate not null,   --등록시점
        upt_dt         timestamp          default sysdate not null,   --수정시점
        descrim       	 varchar2(10) 
 
      --Admin용 속성 정의함
      --Manager용 속성 정의함
      --User용 속성 정의함
);




insert into f_party(user_id, user_pwd, name, birth_dt, sex, enabled, descrim)
   values('admin', '1234', '김이박', '1999-01-30', '1', '1', 'Admin');


-- 각 행위자별 여러 연락처 정보 관리
--party_type, description
create table f_contact_point_type(
   contact_point_type   char(10),
   description         varchar2(100)
);

insert into f_contact_point_type(contact_point_type, description)
   values('address', '주소지');   

   
--연락처. 회원 탈퇴시 정보는 실제 삭제 처리.
--user_id, contact_point_type, info, reg_dt, upt_dt
create table f_contact_point (
   user_id         varchar2(10),
   contact_point_type   char(10),
   info            varchar2(50),-- 연락처 정보
   reg_dt         timestamp      default sysdate not null,   --등록시점
   upt_dt         timestamp      default sysdate not null,   --수정시점
   primary key (user_id, contact_point_type),
   CONSTRAINT fk_cp_party FOREIGN KEY (user_id) REFERENCES s_party(user_id)
);

create table f_contact_point (
   user_id         varchar2(10),
   contact_point_type   char(10),
   info            varchar2(50),-- 연락처 정보
   reg_dt         timestamp      default sysdate not null,   --등록시점
   upt_dt         timestamp      default sysdate not null,   --수정시점
);

admin...address 313호 2020.01.01 2025.01.01
admin...address 2025.01.01 9999.12.31

 	from_dt         timestamp      default sysdate not null,   --등록시점
	thru_dt         timestamp      default sysdate not null,   --수정시점
  

insert into s_contact_point(user_id, contact_point_type, info)
   values('admin', 'address', '서울시 금천구 가산동 312호');

--Code Table. 
   
   
