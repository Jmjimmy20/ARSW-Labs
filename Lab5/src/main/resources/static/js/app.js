var hallarAutor = ( function () {
    function realizarMap(blueprints){
        return blueprints.map(function(element){
            return {
                name: element.name,
                points: element.points.length
            }
        })
    }
    function sumaDePuntos(blueprints){
        return blueprints.reduce(function(total,num){
            return total+num.points;
        },0)
    }
    function realizarTabla(blueprints){
        var author = blueprints[0].author;
        blueprints = realizarMap(blueprints);
        $("#filasBlueprints").empty();
        blueprints.map(function(element){
            var markup = "<tr> <td>"+ element.name +"</td> <td>"+element.points+'</td> <td><button type="button" onclick="hallarAutor.realizarBusquedaPorNombre(\''+author+'\',\''+element.name+'\')">Open</button></td> </tr>';
            $("#filasBlueprints").append(markup)
        })
        $("#sumaTotal").text(" Total user points: "+sumaDePuntos(blueprints));

    }

    var realizarBusqueda = function(name){

        try {
            apimock.getBlueprintsByAuthor(realizarTabla, name)
            $("#textoNombre").text( name+'\'s Blueprints:');
        } catch(exception){
            alert("No estas colocando un nombre válido");
        }

    }
    function realizarDibujo(blueprint){
        if (blueprint.points.length == 0){
            throw "noPuntos";
        }
        var canvas = document.getElementById("myCanvas");
        var lapiz = canvas.getContext("2d");
        //Ver como borrar el contexto actual de un Canvas//ASDASDASDASDASDSADSADSD
        lapiz.clearRect(0,0,canvas.width,canvas.height)
        lapiz.moveTo(blueprint.points[0].x,blueprint.points[0].y);

        blueprint.points.map(function(element){
            lapiz.lineTo(element.x,element.y);
        },1)
        lapiz.stroke();
        alert(blueprint.author);
    }

    var realizarBusquedaPorNombre = function (author,name){
        try {
            apimock.getBlueprintsByNameAndAuthor(name, author, realizarDibujo)
        } catch (noPuntos){
            alert("Escogiste un plano que no cuenta con puntos, escoge otro o agrega puntos al plano actual")
        }
    }

    return {
        realizarBusqueda:realizarBusqueda,
        realizarBusquedaPorNombre:realizarBusquedaPorNombre
    }
})();