package com.marvelcomicsapi.service.function;

import java.util.Calendar;

import com.marvelcomicsapi.entity.User;
import com.marvelcomicsapi.entity.UserComic;
import com.marvelcomicsapi.objects.response.ComicResponse;
import com.marvelcomicsapi.objects.response.UserComicResponse;
import com.marvelcomicsapi.objects.response.UserResponse;

public class DiscountDay {
	
	String[] daysOfWeek = {"Domingo", "Segunda-Feira", "Terça-feira", "Quarta-Feira", "Quinta-feira", "Sexta-Feira", "Sábado"};
	Calendar calendar = Calendar.getInstance();
	int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
	
	public UserResponse DiscountDayActive(User userToResponse) {
		UserResponse userResponse = new UserResponse(userToResponse.getIdUser(), userToResponse.getName(), 
				userToResponse.getEmail(), userToResponse.getCpf(), userToResponse.getBirthDate());
		
		for (UserComic userComic : userToResponse.getUserComics()) {
			
			UserComicResponse userComicResponse = new UserComicResponse(userComic.getIdUserComic(), userComic.getDate());
				
			userComicResponse.setComic(new ComicResponse(userComic.getComic().getIdComic(), userComic.getComic().getTitle(), 
					userComic.getComic().getPrice(), userComic.getComic().getAuthor(), userComic.getComic().getIsbn(), 
					userComic.getComic().getDescription(), null, null));

			if (!userComic.getComic().getIsbn().isEmpty()) {

				String isbn = userComic.getComic().getIsbn();
				String lastCharIsbn = isbn.substring(isbn.length() - 1); 

				switch (lastCharIsbn) {
					case "0": case "1":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[1]);
						break;
					case "2": case "3":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[2]);
						break;
					case "4": case "5":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[3]);
						break;
					case "6": case "7":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[4]);
						break;
					case "8": case "9":
						userComicResponse.getComic().setDiscountDay(daysOfWeek[5]);
						break;
				}
				
				discountActive(userComicResponse, userComic);
			}
			
			userResponse.addUserComic(userComicResponse);
		}
		
		return userResponse;
	}
	
	public void discountActive(UserComicResponse userComicResponse, UserComic userComic) {
		if (daysOfWeek[dayWeek - 1] == userComicResponse.getComic().getDiscountDay()) {
			userComicResponse.getComic().setDiscountActive(true);
			Float newPrice = (float) (userComic.getComic().getPrice() * 0.9);
			userComicResponse.getComic().setPrice(newPrice);
		} else {
		userComicResponse.getComic().setDiscountActive(false);
		}
	}
}
