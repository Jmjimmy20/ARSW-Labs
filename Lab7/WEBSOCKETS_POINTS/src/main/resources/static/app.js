var app = (function () {

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;
    var identificadorDibujo = null;
    var addPointToCanvas = function (event) {
        var ptTemporal = getMousePosition(event);
        var pt=new Point(ptTemporal.x,ptTemporal.y);
        console.info("publishing point at "+pt);
        stompClient.send("/app/newpoint."+identificadorDibujo,{},JSON.stringify(pt));
    };
    
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function (identificador) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        //subscribe to /topic/TOPICXX when connections succeed
        identificadorDibujo = identificador;
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe("/topic/newpoint."+identificador, function (eventbody) {
                var canvas = document.getElementById("canvas");
                var ctx = canvas.getContext("2d");
                ctx.beginPath();
                var puntoTMP = JSON.parse(eventbody.body);
                ctx.arc(puntoTMP.x, puntoTMP.y, 3, 0, 2 * Math.PI);
                ctx.stroke();
            });
            stompClient.subscribe("/topic/newpolygon."+identificador,function (eventbody){
                alert("Ey");
                var canvas = document.getElementById("canvas");
                var ctx = canvas.getContext("2d");
                ctx.beginPath();
                var puntosTMP = JSON.parse(eventbody.body);
                ctx.moveTo(puntosTMP[0].x,puntosTMP[0].y);
                puntosTMP.map(function(element){
                    ctx.lineTo(element.x,element.y);
                },1)
                ctx.stroke();
            });

        });

    };
    
    

    return {

        init: function (identificador) {
            var can = document.getElementById("canvas");
            
            //websocket connection
            connectAndSubscribe(identificador);

            can.addEventListener("mousedown", addPointToCanvas, false);

        },

        publishPoint: function(){
            var ptTemporal = getMousePosition(onclick);
            var pt=new Point(ptTemporal.x,ptTemporal.y);
            console.info("publishing point at "+pt);
            addPointToCanvas(pt);


        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };

})();