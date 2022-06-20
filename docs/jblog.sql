-- scheme
show tables;
desc user;
desc blog;
desc category;
desc post;

select * from user;
delete from user;
select * from blog;
delete from blog;
update blog set logo='assets/logo/default.jpg';
select * from category;
select * from post;

(select no from category where blog_id='windba78' and name='1');

insert into post values(null, 'a', 'b', (select no from category where blog_id='windba78' and name='3'));

select category_no, count(IFNULL(category_no,0)) as count from post a, category b where a.category_no = b.no and b.blog_id='windba78' group by category_no;

select a.no, a.name, a.description, a.blog_id as blogId, IFNULL(b.count,0) as count
	from category a left outer join (select category_no, count(*) as count from post a, category b where a.category_no = b.no and b.blog_id='windba78' group by category_no) b 
    on a.no = b.category_no
    where blog_id='windba78'
    order by a.no;
(select category_no, count(*) from post a, category b where a.category_no = b.no and b.blog_id='windba78' group by category_no);
(select * from post a, category b where a.category_no = b.no and b.blog_id='windba78' group by category_no);

