INSERT INTO categories (category_name)
VALUES ('헬스'),
       ('크로스핏'),
       ('요가'),
       ('필라테스'),
       ('오운완'),
       ('오늘의 식단')
    ON DUPLICATE KEY UPDATE category_name = category_name;
