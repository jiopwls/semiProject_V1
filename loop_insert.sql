create procedure loop_insert(IN cnt int)
begin
	declare i int default 1;
	while (i <= cnt) do
		insert into board (title, userid, contents)
		values('aaaa', '111','가나다라');
		insert into board (title, userid, contents)
		values('ㄱㄴㄷ', '222','작성함');
		insert into board (title, userid, contents)
		values('테스트', '333','테스트');
		set i = i + 1;
	end while;
end;


delete from board;
call loop_insert(1004);