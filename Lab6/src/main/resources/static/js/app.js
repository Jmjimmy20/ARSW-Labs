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
            var markup = "<tr> <td>"+ element.name +"</td> <td>"+element.points+'</td> <td><button type="button"  class="btn btn-info" onclick="hallarAutor.realizarBusquedaPorNombre(\''+author+'\',\''+element.name+'\')">Open</button></td> </tr>';
            $("#filasBlueprints").append(markup)
        })
        $("#sumaTotal").text(" Total user points: "+sumaDePuntos(blueprints));

    }
    var nombreAutor = null;
    var realizarBusqueda = function(name){
        try {
            $("#filasBlueprints").empty();
            nombreAutor = name;
            apiclient.getBlueprintsByAuthor(realizarTabla, name);
            $("#textoNombre").text( name+'\'s Blueprints:');
        } catch(nombreInvalido){
            alert("No estas colocando un nombre válido o no existe");
        }

    }
    var dibujoActual = null;
    function realizarDibujo(blueprint){
        if (blueprint.points.length == 0){
            throw "noPuntos";
        }
        dibujoActual = blueprint;
        $("#textoCanvas").text("Current blueprint: "+blueprint.name)
        var canvas = document.getElementById("myCanvas");
        var lapiz = canvas.getContext("2d");

        lapiz.clearRect(0,0,canvas.width,canvas.height);
        lapiz.beginPath();
        lapiz.moveTo(blueprint.points[0].x,blueprint.points[0].y);

        blueprint.points.map(function(element){
            lapiz.lineTo(element.x,element.y);
        },1)
        lapiz.stroke();
    }



    var pointSize = 3;

    function getPosition(event){
         var rect = myCanvas.getBoundingClientRect();
         var x = event.clientX - rect.left;
         var y = event.clientY - rect.top;
         if(dibujoActual == null){
             if (nombreAutor == null){
                 throw "autorNoIdentificado";
             } else {
                 //Ver que poner
             }
         } else {
             dibujoActual.points.push({x:x,y:y});
         }
         realizarDibujo(dibujoActual);
         //drawCoordinates(x,y);
    }

    function drawCoordinates(x,y){
      	var ctx = document.getElementById("myCanvas").getContext("2d");


      	ctx.fillStyle = "#ff2626"; // Red color

        ctx.beginPath();
        ctx.arc(x, y, pointSize, 0, Math.PI * 2, true);
        ctx.fill();
    }

    var realizarBusquedaPorNombre = function (author,name){
        try {
            apiclient.getBlueprintsByNameAndAuthor(name, author, realizarDibujo)
        } catch (noPuntos){
            alert("Escogiste un plano que no cuenta con puntos, escoge otro o agrega puntos al plano actual")
        }
    }

    var createBluePrint = function(){
        var nuevoNombreBP = prompt("Digita el nombre del blueprint que estas creando", "Blueprint_sin_nombre");
        dibujoActual =  {
            author: nombreAutor,
            name: nuevoNombreBP,
            points: [

            ]
        }

        apiclient.createBluePrint(dibujoActual).then(function(){
            $("#textoCanvas").text("Current Blueprint: "+nuevoNombreBP);
            realizarBusqueda(dibujoActual.author);
            var canvas = document.getElementById("myCanvas");
            var lapiz = canvas.getContext("2d");

            lapiz.clearRect(0,0,canvas.width,canvas.height);
            lapiz.beginPath();
        })

    }
    var updateBP = function(){
        if (dibujoActual == null) {
            alert("Debes abrir un blueprint antes de poder eliminarlo")
        } else {
            apiclient.actualizarBP(dibujoActual).then(function () {
                realizarBusqueda(dibujoActual.author);
                alert("El blueprint se ha actualizado");
            });
        }
    }

    var deleteBP = function() {
        if (dibujoActual == null) {
            alert("Debes abrir un blueprint antes de poder eliminarlo")
        } else {

            apiclient.eliminarBP(dibujoActual).then(function () {
                realizarBusqueda(dibujoActual.author);
                alert("El blueprint se ha eliminado");
                var canvas = document.getElementById("myCanvas");
                var lapiz = canvas.getContext("2d");

                lapiz.clearRect(0,0,canvas.width,canvas.height);
                lapiz.beginPath();

            });
        }
    }

    return {
        realizarBusqueda:realizarBusqueda,
        realizarBusquedaPorNombre:realizarBusquedaPorNombre,
        createBluePrint:createBluePrint,
        updateBP:updateBP,
        deleteBP:deleteBP,
        can:function(){
        $("#myCanvas").click(function(e){
                 try {
                     getPosition(e);
                 } catch(autorNoIdentificado){
                     alert("Selecciona un nombre como autor en la casilla de Author y cuando crees un blueprint crearemos el autor por ti")
                 }
            })
        }
    }
})();