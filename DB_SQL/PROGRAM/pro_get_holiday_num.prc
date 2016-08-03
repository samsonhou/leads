CREATE OR REPLACE procedure pro_get_holiday_num(g_today         in date,
                                                g_holiday_count in out number) is
  g_flag number := 0;

begin

  select count(1) into g_flag from t_holiday t where t.holiday = g_today;
  if g_flag > 0 then
    g_holiday_count := nvl(g_holiday_count, 0) + 1;
    pro_get_holiday_num(g_today - 1, g_holiday_count);
  else
    g_holiday_count := nvl(g_holiday_count, 0);
  end if;

end pro_get_holiday_num;

/
