SELECT s.name, s.age, f.name
FROM students s
      LEFT JOIN faculties f on f.id=s.faculty_id;

select s.name,s.age
from students s
 inner join avatars a on s.id=a.student
