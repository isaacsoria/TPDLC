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
					$("#contenedorResultadosSpotify > div > div:not(:first-child)").remove();
					console.log(resultados);
					indiceColor=0;
					if(resultados.artists!==undefined){
						cargarDatos(resultados.artists,colores[indiceColor]);
					}
					indiceColor=1;
					if(resultados.albums!==undefined){
						cargarDatos(resultados.albums,colores[indiceColor]);
					}
					indiceColor=2;
					if(resultados.tracks!==undefined){
						cargarDatos(resultados.tracks,colores[indiceColor]);
					}
					indiceColor=3;
					if(resultados.playlists!==undefined){
						cargarDatos(resultados.playlists,colores[indiceColor]);
					}
				});
			}
		});
		function cargarDatos(datos, color){
			var tamanioMinimo = 200;
			for(var i = 0; i<datos.items.length; i++){
				var clone = $("#baseResultadoBusquedaSpotify").clone();
				var item = datos.items[i];
				clone.find(".name a").html(item.name);
				clone.find(".name a").attr("href", item.external_urls.spotify);
				var masChica = 100000, imagenMasChica = null;
				for(var j=0; j < item.images.length; j++){
					if(item.images[j].width < masChica && item.images[j].width > tamanioMinimo){
						masChica = item.images[j].width;
						imagenMasChica = item.images[j].url;
					}
				}
				if(imagenMasChica !== null){
					clone.find(".image img").attr("src", imagenMasChica);
					clone.find(".image img").attr("width", "100px");
				}
				clone.css("background-color",color);
				$("#contenedorResultadosSpotify > div").append(clone);
			}
		}
	});
})();