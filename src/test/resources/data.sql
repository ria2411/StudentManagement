INSERT INTO students (id, name, furigana, nickname, email, region, age, gender, remark, is_deleted) VALUES
(1, '酒井栞', 'シオリサカイ', 'シオリ', 'shiori.sakai@abccc.co.jp', '東京', 36, 'Female', '特になし', false),
(2, '深田涼太', 'フカダリョウタ', 'リョウタ', 'ryota.fukada@gmail.com', '東京', 30, 'Male', '特になし', false),
(3, '桜井日奈子', 'サクライヒナコ', 'ヒナコ', 'hinako.sakurai@gmail.com', '千葉', 22, 'Female', '', false);

INSERT INTO student_courses (id, student_id, course_name, start_date, end_date) VALUES
(1, 1, 'Javaコース', '2024-07-01 00:00:00', '2025-07-01 00:00:00'),
(2, 2, 'Javaコース', '2025-04-19 00:00:00', '2026-04-18 00:00:00'),
(3, 3, 'AWSコース', '2025-06-23 00:00:00', '2026-06-22 00:00:00');
