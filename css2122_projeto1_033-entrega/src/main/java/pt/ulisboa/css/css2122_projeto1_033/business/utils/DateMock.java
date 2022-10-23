package pt.ulisboa.css.css2122_projeto1_033.business.utils;

import pt.ulisboa.css.css2122_projeto1_033.facade.exceptions.ApplicationException;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;


public class DateMock {
	private String hourAndMinute;
	private String dayAndMonthAndYear;
	private DayOfWeek dayOfWeek;

	private DATE_TYPE type;

	public DateMock(String dayAndMonthAndYear, String hourAndMinute) {
		this.hourAndMinute = hourAndMinute;
		this.dayAndMonthAndYear = dayAndMonthAndYear;
	}

	public static Date increaseMinToData(int minutes, Date date){
		return new Date(date.getTime() +  ((long) minutes * 60 * 1000));
	}

	public DateMock(DATE_TYPE type, String date) throws ApplicationException {
		this.type = type;
		switch (type) {
			case HOUR_MINUTE:
				this.hourAndMinute = date;
				break;
			case YEAR_MONTH_DAY:
				this.dayAndMonthAndYear = date;
				break;
			case YEAR_MONTH_DAY_HOUR_MINUTE:
				String[] yearMonthDayHourMinute = date.split(" ");
				this.dayAndMonthAndYear = yearMonthDayHourMinute[0];
				this.hourAndMinute = yearMonthDayHourMinute[1];
				break;
			case DAY_OF_WEEK_HOUR_MINUTE:
				String[] dayOfWeekAndHourMinute = date.split(" ");
				this.dayOfWeek = convertStringToDayOfTheWeek(dayOfWeekAndHourMinute[0]);
				this.hourAndMinute = dayOfWeekAndHourMinute[1];
				break;
			case DAY_MONTH_YEAR_TRACE_HOUR_MINUTE:
				String[] dayMonthYearHourMinute = date.split("-");
				this.dayAndMonthAndYear = dayMonthYearHourMinute[0];
				this.hourAndMinute = dayMonthYearHourMinute[1];
				break;
			default:

		}
	}


	public Date convertToDate() {

		try {
			switch (type) {
				case HOUR_MINUTE:
					return new SimpleDateFormat("HH:mm").parse(hourAndMinute);
				case YEAR_MONTH_DAY:
					return new SimpleDateFormat("dd/MM/yyyy").parse(dayAndMonthAndYear);
				case YEAR_MONTH_DAY_HOUR_MINUTE:
					return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dayAndMonthAndYear + " " + hourAndMinute);
				case DAY_OF_WEEK_HOUR_MINUTE:
					String hour = hourAndMinute.split(":")[0];
					String minute = hourAndMinute.split(":")[1];
					Date date = getCurrentTime();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.set(Calendar.HOUR, Integer.parseInt(hour));
					calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
					return calendar.getTime();
				case DAY_MONTH_YEAR_TRACE_HOUR_MINUTE:
					return new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(dayAndMonthAndYear + "-" + hourAndMinute);
				default:
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getCurrentTime() {
		Date date = null;

		try {
			date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("20/03/2022 10:15:00");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return date;
	}

	public static DayOfWeek convertStringToDayOfTheWeek(String day) throws ApplicationException {
		switch (day) {
			case "Segunda":
				return DayOfWeek.MONDAY;
			case "Terça":
				return DayOfWeek.TUESDAY;
			case "Quarta":
				return DayOfWeek.WEDNESDAY;
			case "Quinta":
				return DayOfWeek.THURSDAY;
			case "Sexta":
				return DayOfWeek.FRIDAY;
			case "Sabado":
				return DayOfWeek.SATURDAY;
			case "Domingo":
				return DayOfWeek.SUNDAY;
			default:
				throw new ApplicationException("Something went wrong with the day of the week");
		}
	}

	public static String printHour(Date date){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(date);
	}

	public static String printDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}

	public String getHourAndMinute() {
		return hourAndMinute;
	}

	public String getDayAndMonthAndYear() {
		return dayAndMonthAndYear;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	@Override
	public String toString() {
		return dayAndMonthAndYear + " " + hourAndMinute;
	}
}
