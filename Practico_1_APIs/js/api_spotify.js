(function(){
	$(document).ready(function(){
		var colores = ["#F08484","#B1B2FC","#FACF43","#43FA89"];
		$("#btnBuscarSpotify").click(function(){
			var busqueda = $("#txtBuscarSpotify").val().trim();
			var tipo = $("#slcTipoSpotify").val();
			if(busqueda.length > 0 &&
				(tipo === "all" || tipo === "artist" || tipo === "playlist" || tipo === "album" || tipo === "track" )
			){
				//datos de busqueda validos
				var resultadoTipo = "";
				if(tipo === "all"){
					resultadoTipo += "artist,playlist,album,track";
				}else{
					resultadoTipo = tipo;
				}
				
				$.get( "https://api.spotify.com/v1/search?q="+busqueda+"&type="+resultadoTipo, function(resultados) {
					$("#contenedorResultadosSpotify > div *:not(:first-child)").remove();
					console.log(resultados);
					indiceColor=0;
					if(resultados.artists!==undefined){
						for(var i = 0; i<resultados.artists.items.length; i++){
							var clone = $("#baseResultadoBusquedaSpotify").clone();
							clone.find(".name a").html(resultados.artists.items[i].name);
							clone.find(".name a").attr("href", resultados.artists.items[i].href);
							//clone.find(".image img").attr("src", resultados.artists.items[i].images[0].url);
							clone.css("background-color",colores[indiceColor]);
							$("#contenedorResultadosSpotify > div").append(clone);
						}
					}
					indiceColor=1;
					if(resultados.albums!==undefined){
						for(var i = 0; i<resultados.albums.items.length; i++){
							var clone = $("#baseResultadoBusquedaSpotify").clone();
							clone.find(".name a").html(resultados.albums.items[i].name);
							clone.find(".name a").attr("href", resultados.albums.items[i].href);
							//clone.find(".image img").attr("src", resultados.albums.items[i].images[0].url);
							clone.css("background-color",colores[indiceColor]);
							$("#contenedorResultadosSpotify > div").append(clone);
						}
					}
					indiceColor=2;
					if(resultados.tracks!==undefined){
						for(var i = 0; i<resultados.tracks.items.length; i++){
							var clone = $("#baseResultadoBusquedaSpotify").clone();
							clone.find(".name a").html(resultados.tracks.items[i].name);
							clone.find(".name a").attr("href", resultados.tracks.items[i].href);
							//clone.find(".image img").attr("src", resultados.tracks.items[i].images[0].url);
							clone.css("background-color",colores[indiceColor]);
							$("#contenedorResultadosSpotify > div").append(clone);
						}
					}
					indiceColor=3;
					if(resultados.playlists!==undefined){
						for(var i = 0; i<resultados.playlists.items.length; i++){
							var clone = $("#baseResultadoBusquedaSpotify").clone();
							clone.find(".name a").html(resultados.playlists.items[i].name);
							clone.find(".name a").attr("href", resultados.playlists.items[i].href);
							//clone.find(".image img").attr("src", resultados.playlists.items[i].images[0].url);
							clone.css("background-color",colores[indiceColor]);
							$("#contenedorResultadosSpotify > div").append(clone);
						}
					}
				});
			}
		});
	});
})();