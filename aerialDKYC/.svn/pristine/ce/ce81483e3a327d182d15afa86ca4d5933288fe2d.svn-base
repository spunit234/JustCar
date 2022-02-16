package com.edios.cdf.util;

public class NumberToWord {

	private static final String[] SPECIALNAMES = { "", " THOUSAND", " MILLION", " BILLION", " TRILLION", " QUADRILLION",
			" QUINTILLION" };

	private static final String[] TENSNAMES = { "", " TEN", " TWENTY", " THIRTY", " FORTY", " FIFTY", " SIXTY",
			" SEVENTY", " EIGHTY", " NINETY" };

	private static final String[] NUMBERNAMES = { "", " ONE", " TWO", " THREE", " FOUR", " FIVE", " SIX", " SEVEN",
			" EIGHT", " NINE", " TEN", " ELEVEN", " TWELVE", " THIRTEEN", " FOURTEEN", " FIFTEEN", " SIXTEEN",
			" SEVENTEEN", " EIGHTEEN", " NINETEEN" };

	private String convertLessThanOneThousand(int number) {
		String current;

		if (number % 100 < 20) {
			current = NUMBERNAMES[number % 100];
			number /= 100;
		} else {
			current = NUMBERNAMES[number % 10];
			number /= 10;

			current = TENSNAMES[number % 10] + current;
			number /= 10;
		}
		if (number == 0)
			return current;
		return NUMBERNAMES[number] + " HUNDRED" + current;
	}

	public String convert(Integer number) {
		if (number == 0) {
			return "ZERO";
		}

		String prefix = "";

		if (number < 0) {
			number = -number;
			prefix = "-";
		}

		String current = "";
		int place = 0;

		do {
			int n = number % 1000;
			if (n != 0) {
				String s = convertLessThanOneThousand(n);
				current = s + SPECIALNAMES[place] + current;
			}
			place++;
			number /= 1000;
		} while (number > 0);

		return (prefix + current).trim();
	}
}