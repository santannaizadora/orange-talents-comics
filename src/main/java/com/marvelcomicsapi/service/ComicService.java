package com.marvelcomicsapi.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.marvelcomicsapi.entity.Comic;
import com.marvelcomicsapi.objects.ComicApi;
import com.marvelcomicsapi.objects.ref.DataWrapper;
import com.marvelcomicsapi.repository.ComicRepository;

@Service
public class ComicService {

	@Autowired
	private ComicRepository comicRepository;
	
	public void save(Integer idComic) {
		Optional<Comic> comic = this.comicRepository.findById(idComic);
		
		if(comic.isEmpty()) {
			Comic comicToSave = new Comic();
			
			RestTemplate restTemplate = new RestTemplate();
			
			DataWrapper<ComicApi> comicApiMarvel = restTemplate.getForObject("https://gateway.marvel.com:443/v1/public/comics/"+idComic+"?apikey=83312fbc43d68692529bc7a251a00699&ts=thesoer&hash=d4ec265f5ef1e32bc7eb9f8ff3b9de9a", DataWrapper.class);
			
			//ComicApi comicApi = new Gson().fromJson(comicApiMarvel.getData().getResults().toArray()[0].toString().replaceAll("=", ":"), ComicApi.class);
			
			System.out.println(comicApiMarvel.getData().getResults().toString());
			
			//System.out.println("----------"+comicApi.getDescription());

			comicToSave.setIdComic(idComic);
			comicToSave.setTitle("123");
			comicToSave.setDescription("123");
			comicToSave.setAuthor("123");
			comicToSave.setIsbn("1231");
			comicToSave.setPrice(123.00);
			
			comicRepository.save(comicToSave);		
		}
	}
	
	
	public Comic fromDto(Comic comic) {
		return new Comic(comic);
	}
}
