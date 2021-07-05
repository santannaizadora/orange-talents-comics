package com.marvelcomicsapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvelcomicsapi.client.ComicClient;
import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.objects.ComicApi;
import com.marvelcomicsapi.objects.CreatorSummary;
import com.marvelcomicsapi.repository.ComicRepository;

@Service
public class ComicService {

	@Autowired
	private ComicRepository comicRepository;
	
	private final ComicClient comicClient;
	
	public ComicService (ComicClient comicClient) {
		this.comicClient = comicClient;
	}
	
	public Optional<Comic> save(Integer idComic) {
		Comic comicToSave = new Comic();

		ComicApi comicApiMarvel = comicClient.getComicById(idComic).getData().getResults().get(0);

		comicToSave.setIdComic(comicApiMarvel.getId());
		comicToSave.setTitle(comicApiMarvel.getTitle());
		comicToSave.setDescription(comicApiMarvel.getDescription());
		comicToSave.setIsbn(comicApiMarvel.getIsbn());
		comicToSave.setPrice(comicApiMarvel.getPrices().get(0).getPrice());

		for(CreatorSummary author : comicApiMarvel.getCreators().getItems()) {
			if (comicToSave.getAuthor() != null && author.getName() != null) {
				comicToSave.setAuthor(author.getName() + "; " + comicToSave.getAuthor());
			} else {
				comicToSave.setAuthor(author.getName());
			}
		}

		comicRepository.save(comicToSave);
		
		return this.comicRepository.findById(idComic);
	}
	
	public Comic fromDto(Comic comic) {
		return new Comic(comic);
	}
}
