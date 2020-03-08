
var apiclient = (function(){

    var getBlueprintsByAuthor = (function (callback,author) {
        var success = false;
        $.getJSON("http://localhost:8080/blueprints/"+author,function(data){
            success=true;
            callback(data);

        },null);
        setTimeout(function() {
            if (!success) {
                alert("El autor que estas colocando no existe");
            }
        }, 300);
    })

    var getBlueprintsByNameAndAuthor = (function(name, author, callback) {
            $.getJSON("http://localhost:8080/blueprints/"+author+"/"+name,function(data){
                callback(data);
            },null);
        }

    )
    var createBlueprint = (function(blueprint) {
            var putPromise=$.ajax({
                url:"http://localhost:8080/blueprints",
                type:'POST',
                data:JSON.stringify(blueprint),
                contentType: "application/json",
            });
            return putPromise;
        }

    )

    var actualizarBP = (function(blueprint){
        var putPromise=$.ajax({
            url:"http://localhost:8080/blueprints/"+blueprint.author+"/"+blueprint.name,
            type:'PUT',
            data:JSON.stringify(blueprint),
            contentType: "application/json",
        });
        return putPromise;
    })
    var eliminarBP = (function (blueprint){
        var putPromise=$.ajax({
            url:"http://localhost:8080/blueprints/"+blueprint.author+"/"+blueprint.name,
            type:'DELETE'
        });
        return putPromise;
    })



    return {
        getBlueprintsByAuthor: getBlueprintsByAuthor ,
        getBlueprintsByNameAndAuthor: getBlueprintsByNameAndAuthor,
        createBluePrint:createBlueprint,
        actualizarBP:actualizarBP,
        eliminarBP:eliminarBP
    }
})();