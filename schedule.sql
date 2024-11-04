-- schedule 테이블 생성
CREATE TABLE `schedule` (
                `id`	INT	AUTO_INCREMENT,
                `name`	VARCHAR(10)	NOT NULL,
                `password`	INT	NOT NULL,
                `title`	VARCHAR(20)	NOT NULL,
                `content`	VARCHAR(40)	NOT NULL,
                `created_at`	DATE NOT NULL,
                `modified_at`	DATE NOT NULL,
                PRIMARY KEY(id)
);

-- 전체 목록 조회
SELECT *
FROM schedule;

-- 선택 일정 조회
SELECT *
FROM schedule
WHERE id = 1;

-- 일정 생성
INSERT INTO schedule (name,password,title,content,created_at,modified_at)
VALUES ('김철수',1234,'운동','수영','2024-11-04','2024-11-04')

-- 선택한 일정 수정
UPDATE schedule
SET content = '수정된 내용'
WHERE id = 1;

-- 선택한 일정 삭제
DELETE from schedule
where id =1;