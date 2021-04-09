create database JavaBTGK
go

create table NhanVien 
(
	MaNV nvarchar(20),
	MaPB char(10),
	TenNV nvarchar(50),
	DiaChi nvarchar(50),
	SDT int,
	phongban nvarchar(100),
	GioiTinh nvarchar(20),
	constraint pk_NV primary key(MaNV,MaPB)
)
drop table NhanVien
insert into NhanVien
values ('001','te','phamvanpha','cntt3',123456,'te','Nam'),
		('002','te','test','cntt3',123456,'te','Nam'),
		('003','te','test2','cntt3',123456,'te','Nam')
insert into NhanVien
values ('004','te','test2222','cntt3',123456,'te','Nam')


select * from NhanVien

create table login (
	MaNV nvarchar(20),
	usename varchar(50) unique,
	pass char(20),
	level int,
	constraint pk_login primary key (MaNV)
)

insert into login 
values ('001','test','test123',0),
('002','admin','admin123',1),
('003','admin2','admin123',1)
insert into login 
values ('004','test2','test123',0)

drop table login

select * from login where usename = 'test'

select level from login where usename = 'test'

select * from NhanVien
set dateformat dmy
create table QLPB(
	MaPB char(10),
	TenPB nvarchar(100),
	NgayTL date,
	SLNV int,
	DuAn nvarchar(50)
	constraint pk_QLPB primary key (MaPB)
)
drop table QLPB
insert into QLPB 
values ('te2','test2','26-3-2021',2,'code sml')
select * from QLPB
select lg.MaNV,nv.MaPB, nv.TenNV,nv.DiaChi, nv.SDT from NhanVien nv right join login lg on nv.MaNV = lg.MaNV

alter table NhanVien add constraint fk_pb foreign key(MaPB) references QLPB(MaPB) on update cascade on delete cascade

create table salary(
	MaNV nvarchar(20),
	ngaylv int,
	luongcb int,
	thuong int,
	phat int,
	thue float,
	tongluong int
	constraint pk_salary primary key(MaNV)
)
drop table salary
insert into salary 
values ('001',10,8000000,1000000,0,0.5,10000000)

insert into salary values('002',10,8000000,1000000,0,0.5,0)

delete from salary  where MaNV = '005'
select * from salary

select SUM(luongcb) from salary where MaNV = '004'
alter table NhanVien add constraint fk_pb foreign key(MaPB) references QLPB(MaPB) on update cascade on delete cascade
alter table NhanVien add constraint fk_lg foreign key(MaNV) references login(MaNV) on update cascade on delete cascade
alter table salary add constraint fk_salary foreign key(MaNV) references login(MaNV) on update cascade on delete cascade

alter table salary
drop constraint fk_salary
select * from salary
select nv.MaNV ,nv.TenNV,sl.ngaylv,sl.luongcb,sl.thuong,sl.thuong,sl.phat,sl.thue,sl.tongluong from (salary sl right join NhanVien nv on sl.MaNV = nv.MaNV)

declare @id nvarchar(20), @tong int
declare cur_Salary cursor 
forward_only
for
select MaNV  from salary
open cur_Salary
fetch next from cur_Salary into @id
while @@FETCH_STATUS = 0
begin
	select @tong = SUM(((luongcb/26)*ngaylv)*thue+thuong-phat)
	from salary
	where MaNV = @id
	update salary
	set tongluong = @tong
	where MaNV = @id
	fetch next from cur_Salary into @id
end
close cur_Salary

open cur_Salary
close cur_Salary

select* from salary


create table QLDA 
( MaDA char(10),
   MaPB char(10),
	TenDA nvarchar(50),
	TenPB char(10),
	checkDA varchar(10)
	constraint DA_MaDA primary key (MaDA,MaPB) 
)
go
drop table QLDA
insert into QLDA 
values ()

select * from QLDA
alter table QLDA add constraint fk_da foreign key(MaPB) references QLPB(MaPB) on update cascade on delete cascade

create trigger trg_salary
on salary
after insert,
update as
	declare @id nvarchar(20)
	begin
		if not exists(select * from deleted)
			begin
				select @id = MaNV
				from inserted
				if ((select SUM(((luongcb/22)*ngaylv)+thuong-phat) from salary where MaNV = @id) >= 10000000)
						update salary set tongluong = (select SUM((((luongcb/22)*ngaylv)+thuong-phat)*thue) from salary where MaNV = @id) where MaNV = @id 
				else 
						update salary set tongluong = (select SUM(((luongcb/22)*ngaylv)+thuong-phat) from salary where MaNV = @id) where MaNV = @id 
			end
	end

	drop trigger trg_salary
	select * from NhanVien
	select * from salary
	insert into salary 
 values ('004',10,80,100,0,0.5,100)
	insert into NhanVien
values ('005','phamvanpha','cntt3',123456,'IT','Nam','Co')

update salary set tongluong = (select SUM(luongcb) from salary where MaNV = '003') where MaNV = '003'

DELIMITER $$
 
CREATE TRIGGER trg_update_salary
    AFTER UPDATE
    ON salary FOR EACH ROW
BEGIN
    
END$$    
 
DELIMITER;		


create view staff_in4 as
select 
from ((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on sl.MaNV = nv.MaNV) join login lg on lg.MaNV = sl.MaNV


select nv.TenNV, pb.TenPB, pb.DuAn, sl.luongcb, sl.ngaylv, sl.thuong, sl.phat, sl.tongluong from ((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB) join salary sl on sl.MaNV = nv.MaNV) join login lg on lg.MaNV = nv.MaNV where lg.MaNV ='001'
select nv.TenNV, pb.TenPB,pb.DuAn, sl.luongcb, sl.ngaylv,sl.thuong, sl.phat , sl.tongluong from((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on nv.MaNV = sl.MaNV) join login lg on lg.MaNV  = nv.MaNV where lg.MaNV = '002'
select * from NhanVien
select * from QLPB

select MaNV from login where usename = 'admin'

select nv.TenNV, pb.TenPB,pb.DuAn, sl.luongcb, sl.ngaylv,sl.thuong, sl.phat , sl.tongluong from((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on nv.MaNV = sl.MaNV) join login lg on lg.MaNV  = nv.MaNV where lg.MaNV = (select MaNV from login where usename = 'admin')

select nv.TenNV, pb.TenPB,pb.DuAn, sl.luongcb, sl.ngaylv,sl.thuong, sl.phat , sl.tongluong from((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on nv.MaNV = sl.MaNV) join login lg on lg.MaNV  = nv.MaNV where lg.MaNV = (select MaNV from login where usename = 'admin')
select nv.TenNV, pb.TenPB,pb.DuAn, sl.luongcb, sl.ngaylv,sl.thuong, sl.phat , sl.tongluong from((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on nv.MaNV = sl.MaNV) join login lg on lg.MaNV  = nv.MaNV where lg.MaNV = 002


select nv.TenNV, pb.TenPB,pb.DuAn, sl.luongcb, sl.ngaylv,sl.thuong, sl.phat , sl.tongluong from((NhanVien nv join QLPB pb on nv.MaPB = pb.MaPB)join salary sl on nv.MaNV = sl.MaNV) join login lg on lg.MaNV  = nv.MaNV where lg.MaNV = (select MaNV from login where usename = 'admin')

select * from QLDA
select * from QLPB