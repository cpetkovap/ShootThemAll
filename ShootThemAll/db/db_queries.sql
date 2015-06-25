
insert into app.Weapons (damage, price) values(1,0); 
insert into app.Weapons (damage, price) values(2,200);
insert into app.Weapons (damage, price) values(3,500); 
insert into app.Users values(2,'2','1','1',1,1,1,1);
insert into app.Users (username, password, email, score ) values('Ivan', '123', 'Ivan@gmail.com', 100);
insert into app.Users (username, password, email, score ) values('Petko', '1234', 'Petko@gmail.com', 10);
insert into app.Users (username, password, email, score ) values('Tanq', 'Tanq', 'Tanq@gmail.com', 200);
insert into app.Users (username, password, email, score ) values('SIMONA', '444', 'simona@gmail.com', 100);
insert into app.Users (username, password, email, score ) values('Evgeni', 'Ta333nq', 'evgeni@gmail.com', 200);
insert into app.Users (username, password, email, score ) values('Tihomit', '222', 'tisho@gmail.com', 300);
insert into app.Users (username, password, email, score ) values('Vanq', '1111', 'v@gmail.com', 230);
insert into app.Users (username, password, email, score ) values('Poli', '111d1', 'd@gmail.com', 530);
insert into app.Users (username, password, email, score ) values('Kirik', 'q1111', 'k@gmail.com', 230);
insert into app.Users (username, password, email, score ) values('Lolipop', 'popi', 'loly@gmail.com', 60);
insert into app.Users (username, password, email, score ) values('Zozi', 'zizi', 'zizi@gmail.com', 630);
insert into app.Users (username, password, email, score ) values('Roni', 'rrrr', 'rrr@gmail.com', 310);
insert into app.Users (username, password, email, score ) values('Qna', '222', 'qna@gmail.com', 70);

insert into app.boosters (DURATION, DESCRIPTION) values(2000, 'Add bullets.');
insert into app.boosters(DURATION, DESCRIPTION) values(1000, 'Add health.');
insert into app.boosters (DURATION, DESCRIPTION) values(3000, 'Add points.');

insert into app.achievements (achievement_points, description) values(500, 'firstAchievment')
insert into app.achievements (achievement_points, description) values(1000, 'secondAchievment')
insert into app.achievements (achievement_points, description) values(2000, 'thirdAchievment')
insert into app.achievements (achievement_points, description) values(5000, 'fourthAchievment')
insert into app.achievements (achievement_points, description) values(10000, 'fifthAchievment')
insert into app.achievements (achievement_points, description) values(50000, 'sixthAchievment')


drop table app.users;
drop table app.weapons;
drop table app.unlockedWeapons;
drop table app.Boosters;
drop table app.Achievements;
drop table app.UserAchievements;
