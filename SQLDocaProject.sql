create database DoCav1_2



use DoCav1_2
create table tblUser(
	id nvarchar(50) primary key, 
	username nvarchar(50) not null,
	[password] nvarchar(50) not null,
	fullname nvarchar(50) not null,
	email nvarchar(50),
	dob date,
	gender bit not null,
	statususer nvarchar(20) not null,
	img nvarchar(MAX) null)


CREATE TABLE [dbo].[tblRole](
	[id] [int] primary key,
	[name] [nvarchar](50) NOT NULL,
	[type] [nvarchar](50) NULL,
)

CREATE TABLE [dbo].[tblUser_Role](
	id int primary key,
	[userid] [nvarchar](50) NOT NULL foreign key references tblUser(id),
	[roleid] [int] NOT NULL foreign key references tblRole(id),
	[datestart] [date] NOT NULL,
	[dateend] [date] NULL,
	[status] [bit] NULL
)

Create table tblCategory(
	id int primary key,
	[name] nvarchar(50)
)

CREATE TABLE [dbo].[tblPost](
	[id] [nvarchar](50) primary key,
	[postNumber] [nvarchar](50) NULL,
	[content] [nvarchar](500) NOT NULL,
	[status] bit NOT NULL,
	[createTime] datetime2(6) NULL,
	exchange bit not null,
	[userid] [nvarchar](50) not NULL foreign key references tblUser(id),
	id_category int not null foreign key references tblCategory(id)
)
create table tblBookmark(
	postid nvarchar(50)not NULL foreign key references tblPost(id),
	userid nvarchar(50)not null foreign key references tblUser(id),
	primary key(postid,userid),
	createtime datetime2(6) null,
	status bit
)
create table tblComment(
	id nvarchar(50) primary key,
	commentnumber nvarchar(50) not null,
	content nvarchar(500) not null,
	createtime date,
	postid nvarchar(50) not null foreign key references tblPost(id),
	userid nvarchar(50) not null foreign key references tblUser(id),
)

create table tblPet(
	id nvarchar(50) primary key,
	[type] nvarchar(20),
	[name] nvarchar(20),
	gender bit,
	age int,
	color nvarchar(20),
	characteristics nvarchar(50),
	vaccination nvarchar (50),
	dewoming nvarchar(50)
)

create table Pet_Item (
	id nvarchar(50) primary key,
	topic nvarchar(50),
	species nvarchar(50),
	[description] nvarchar(50),
	postid nvarchar(50) not null foreign key references tblPost(id),
)

create table Pet_Breed(
	id nvarchar(50) primary key,
	breedname nvarchar(50),
	[postid] [nvarchar](50) not NULL foreign key references tblPost(id),
	pet_type nvarchar(50) not null,
)

	CREATE TABLE [dbo].[tblReact](
	[id] [nvarchar](50) primary key,
	[type] [nvarchar](50) NULL,
	[creatime] [date] NULL,
	[postid] [nvarchar](50) not NULL foreign key references tblPost(id),
	[userid] [nvarchar](50) not NULL foreign key references tblUser(id),
	[commentid] [nvarchar](50) NULL foreign key references tblComment(id)
)
CREATE TABLE [dbo].[tblReport](
	[id] [nvarchar](50) primary key,
	[message] [nvarchar](200) NULL,
	[status] [bit] NULL,
	[postId] [nvarchar](50) not null foreign key references tblPost(id) ,
	[userId] [nvarchar](50)  not null foreign key references tblUser(id),
)