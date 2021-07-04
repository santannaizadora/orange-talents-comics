package com.marvelcomicsapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.marvelcomicsapi.objects.ComicApi;
import com.marvelcomicsapi.objects.ref.DataWrapper;

@FeignClient(url = "https://gateway.marvel.com:443/v1/public/comics/", name = "comic")
public interface ComicClient {

	@GetMapping("{idComic}?apikey=83312fbc43d68692529bc7a251a00699&ts=thesoer&hash=d4ec265f5ef1e32bc7eb9f8ff3b9de9a")
	DataWrapper<ComicApi> getComicById(@PathVariable("idComic") Integer idComic);
}
