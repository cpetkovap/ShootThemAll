
insert into app.Weapons (damage, price) values(1,0); 
insert into app.Weapons (damage, price) values(2,200);
insert into app.Weapons (damage, price) values(3,500); 

insert into app.Users (username, password, email, score ) values('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@gmail.com', 0);
insert into app.Users (username, password, email, score ) values('Ivan', '202cb962ac59075b964b07152d234b70', 'Ivan@gmail.com', 100);
insert into app.Users (username, password, email, score ) values('Petko', '81dc9bdb52d04dc20036dbd8313ed055', 'Petko@gmail.com', 10);
insert into app.Users (username, password, email, score ) values('Tanq', '9275192b6991c20ad4c99c51ca49b175', 'Tanq@gmail.com', 200);
insert into app.Users (username, password, email, score ) values('Simona', '550a141f12de6341fba65b0ad0433500', 'simona@gmail.com', 100);
insert into app.Users (username, password, email, score ) values('Evgeni', 'f137b1b2bd57c16ee40279a57228c4e0', 'evgeni@gmail.com', 200);
insert into app.Users (username, password, email, score ) values('Tihomir', 'bcbe3365e6ac95ea2c0343a2395834dd', 'tisho@gmail.com', 300);
insert into app.Users (username, password, email, score ) values('Vanq', 'b59c67bf196a4758191e42f76670ceba', 'v@gmail.com', 230);
insert into app.Users (username, password, email, score ) values('Poli', '6a567729b7e8167b26a18af967370f03', 'd@gmail.com', 530);
insert into app.Users (username, password, email, score ) values('Kiril', '09ae0bc54b66c7892169d06c30778cdb', 'k@gmail.com', 230);
insert into app.Users (username, password, email, score ) values('Lili', '63d4d0b7e60cda87cbab9b173e1f892c', 'loly@gmail.com', 60);
insert into app.Users (username, password, email, score ) values('Zozi', '2a1c895a72d8f899852bcecae26e52dc', 'zizi@gmail.com', 630);
insert into app.Users (username, password, email, score ) values('Roni', 'eb9279982226a42afdf2860dbdc29b45', 'rrr@gmail.com', 310);
insert into app.Users (username, password, email, score ) values('Qna', 'bcbe3365e6ac95ea2c0343a2395834dd', 'qna@gmail.com', 70);

insert into app.boosters (DURATION, DESCRIPTION) values(2000, 'Add bullets.');
insert into app.boosters(DURATION, DESCRIPTION) values(1000, 'Add health.');
insert into app.boosters (DURATION, DESCRIPTION) values(3000, 'Add points.');
insert into app.boosters (DURATION, DESCRIPTION) values(2000, 'Freez next one enemy.');

insert into app.achievements (achievement_points, description) values(500, 'firstAchievment');
insert into app.achievements (achievement_points, description) values(1000, 'secondAchievment');
insert into app.achievements (achievement_points, description) values(2000, 'thirdAchievment');
insert into app.achievements (achievement_points, description) values(5000, 'fourthAchievment');
insert into app.achievements (achievement_points, description) values(10000, 'fifthAchievment');
insert into app.achievements (achievement_points, description) values(50000, 'sixthAchievment');



drop table app.unlockedWeapons;
drop table app.Boosters;
drop table app.UserAchievements;
drop table app.Achievements;
drop table app.users;
drop table app.weapons;