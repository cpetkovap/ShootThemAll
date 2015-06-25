
create table APP.Users(
id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
username varchar(45) not null unique,
password varchar(45) not null,
email varchar (45) not null,
notificationAllow  int not null check(notificationAllow>=0 and notificationAllow<=1) default 0,
levelNo smallint not null default 1,
score int not null default 0,
choosen_weapon_id int not null default 1,
last_activity_on timestamp, 
primary key(id)
);

create table APP.Weapons(
id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,
damage int not null default 0,
price int not null default 0,
primary key (id)
); 

create table APP.UnlockedWeapons( 
user_id int not null ,
weapon_id int not null,
primary key (user_id, weapon_id),
foreign key (weapon_id) references app.Weapons (id),
foreign key (user_id) references app.Users (id)
);

create table APP.Boosters(
id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
duration bigint not null,
description varchar(255) not null ,
primary key(id)
);


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


drop table app.USERS;
drop table app.weapons;
DROP TABLE  app.UnlockedWeapons;
drop table app.boosters;
