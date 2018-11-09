-- 总收入排名视图
create view
  yxy_parking_statistics_info_view
 as
select * from yxy_parking_statistics_info where datetime = CURRENT_DATE and hour < 24;