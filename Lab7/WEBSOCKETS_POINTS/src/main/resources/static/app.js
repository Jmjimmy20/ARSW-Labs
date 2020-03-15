var app = (function () {

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;

    var addPointToCanvas = function (event) {
        var ptTemporal = getMousePosition(event);
        var pt=new Point(ptTemporal.x,ptTemporal.y);
        console.info("publishing point at "+pt);
        stompClient.send("/topic/newpoint",{},JSON.stringify(pt));
    };
    
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        //subscribe to /topic/TOPICXX when connections succeed

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe("/topic/newpoint", function (eventbody) {
                var canvas = document.getElementById("canvas");
                var ctx = canvas.getContext("2d");
                ctx.beginPath();
                var puntoTMP = JSON.parse(eventbody.body);
                ctx.arc(puntoTMP.x, puntoTMP.y, 3, 0, 2 * Math.PI);
                ctx.stroke();
            });

        });

    };
    
    

    return {

        init: function () {
            var can = document.getElementById("canvas");
            
            //websocket connection
            connectAndSubscribe();

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